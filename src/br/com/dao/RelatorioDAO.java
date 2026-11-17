package br.com.dao;

import br.com.entidade.ContaResumo;
import br.com.entidade.DashboardResumo;
import br.com.entidade.MovimentacaoRelatorio;
import br.com.exception.PersistenciaException;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Camada de Acesso a Dados (DAO) responsável pelas consultas de relatório
 * por período e indicadores do dashboard utilizando ConexaoFactory, JOINs
 * e PersistenciaException (alinhado ao padrão de ContaDAO, FuncionarioDAO e TransacaoDAO).
 */
public class RelatorioDAO {

    /**
     * Verifica se a conexão com o MySQL via ConexaoFactory (config.properties) está ativa.
     */
    public boolean testarConexao() {
        try (Connection conn = ConexaoFactory.getConnection()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Consulta com INNER JOIN entre conta, cliente e agencia para apresentar
     * informações legíveis de todas as contas cadastradas no Dashboard e no filtro.
     */
    public List<ContaResumo> listarContasComTitular() {
        String sql = """
                SELECT c.id, c.numero, c.tipo, c.status, c.saldo, c.limite,
                       cl.nome AS titular_nome, cl.cpf AS titular_cpf,
                       a.numero AS agencia_numero, a.nome AS agencia_nome
                  FROM conta c
                 INNER JOIN cliente cl ON cl.id = c.cliente_id
                 INNER JOIN agencia a  ON a.id  = c.agencia_id
                 ORDER BY a.numero ASC, c.numero ASC
                """;

        List<ContaResumo> lista = new ArrayList<>();

        try (Connection conn = ConexaoFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ContaResumo conta = new ContaResumo();
                conta.setId(rs.getInt("id"));
                conta.setNumero(rs.getString("numero"));
                conta.setTipo(rs.getString("tipo"));
                conta.setStatus(rs.getString("status"));
                conta.setSaldo(rs.getBigDecimal("saldo"));
                conta.setLimite(rs.getBigDecimal("limite"));
                conta.setTitularNome(rs.getString("titular_nome"));
                conta.setTitularCpf(rs.getString("titular_cpf"));
                conta.setAgenciaNumero(rs.getString("agencia_numero"));
                conta.setAgenciaNome(rs.getString("agencia_nome"));
                lista.add(conta);
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao listar contas consolidadas: " + e.getMessage(), e);
        }
        return lista;
    }

    /**
     * Consulta parametrizada de movimentações bancárias (rotina principal)
     * com filtros opcionais de período, tipo de operação e conta.
     * Utiliza LEFT JOIN para resolver os nomes dos titulares de origem e destino
     * mesmo quando uma das chaves estrangeiras é NULL (depósito ou saque).
     */
    public List<MovimentacaoRelatorio> filtrarMovimentacoes(
            LocalDate dataInicio,
            LocalDate dataFim,
            String tipoOperacao,
            Integer contaId) {

        StringBuilder sql = new StringBuilder("""
                SELECT t.id, t.tipo, t.valor, t.data_hora,
                       t.conta_origem_id,  co.numero AS conta_origem,  clo.nome AS titular_origem,
                       t.conta_destino_id, cd.numero AS conta_destino, cld.nome AS titular_destino
                  FROM transacao t
                  LEFT JOIN conta co    ON co.id  = t.conta_origem_id
                  LEFT JOIN cliente clo ON clo.id = co.cliente_id
                  LEFT JOIN conta cd    ON cd.id  = t.conta_destino_id
                  LEFT JOIN cliente cld ON cld.id = cd.cliente_id
                 WHERE 1 = 1
                """);

        List<Object> parametros = new ArrayList<>();

        if (dataInicio != null) {
            sql.append(" AND DATE(t.data_hora) >= ? ");
            parametros.add(Date.valueOf(dataInicio));
        }
        if (dataFim != null) {
            sql.append(" AND DATE(t.data_hora) <= ? ");
            parametros.add(Date.valueOf(dataFim));
        }
        if (tipoOperacao != null && !tipoOperacao.isBlank() && !"TODOS".equalsIgnoreCase(tipoOperacao)) {
            sql.append(" AND t.tipo = ? ");
            parametros.add(tipoOperacao.toUpperCase());
        }
        if (contaId != null && contaId > 0) {
            sql.append(" AND (t.conta_origem_id = ? OR t.conta_destino_id = ?) ");
            parametros.add(contaId);
            parametros.add(contaId);
        }

        sql.append(" ORDER BY t.data_hora DESC, t.id DESC ");

        List<MovimentacaoRelatorio> resultados = new ArrayList<>();

        try (Connection conn = ConexaoFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < parametros.size(); i++) {
                ps.setObject(i + 1, parametros.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    MovimentacaoRelatorio mov = new MovimentacaoRelatorio();
                    mov.setId(rs.getInt("id"));
                    mov.setTipo(rs.getString("tipo"));
                    mov.setValor(rs.getBigDecimal("valor"));

                    Timestamp ts = rs.getTimestamp("data_hora");
                    if (ts != null) {
                        mov.setDataHora(ts.toLocalDateTime());
                    }

                    int origemId = rs.getInt("conta_origem_id");
                    if (!rs.wasNull()) {
                        mov.setContaOrigemId(origemId);
                        mov.setContaOrigemNumero(rs.getString("conta_origem"));
                        mov.setTitularOrigemNome(rs.getString("titular_origem"));
                    }

                    int destinoId = rs.getInt("conta_destino_id");
                    if (!rs.wasNull()) {
                        mov.setContaDestinoId(destinoId);
                        mov.setContaDestinoNumero(rs.getString("conta_destino"));
                        mov.setTitularDestinoNome(rs.getString("titular_destino"));
                    }

                    resultados.add(mov);
                }
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao filtrar movimentações bancárias: " + e.getMessage(), e);
        }
        return resultados;
    }

    /**
     * Carrega os indicadores consolidados para os cards do Dashboard principal.
     */
    public DashboardResumo carregarIndicadoresDashboard() {
        DashboardResumo resumo = new DashboardResumo();

        String sql = """
                SELECT
                    (SELECT COUNT(*) FROM cliente) AS total_clientes,
                    (SELECT COUNT(*) FROM agencia) AS total_agencias,
                    (SELECT COUNT(*) FROM conta WHERE status = 'ATIVA') AS contas_ativas,
                    (SELECT COALESCE(SUM(saldo), 0) FROM conta WHERE status <> 'ENCERRADA') AS saldo_custodia,
                    (SELECT COUNT(*) FROM transacao) AS total_transacoes,
                    (SELECT COALESCE(SUM(valor), 0) FROM transacao) AS volume_movimentado
                """;

        try (Connection conn = ConexaoFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                resumo.setTotalClientes(rs.getInt("total_clientes"));
                resumo.setTotalAgencias(rs.getInt("total_agencias"));
                resumo.setContasAtivas(rs.getInt("contas_ativas"));
                resumo.setSaldoTotalCustodia(rs.getBigDecimal("saldo_custodia"));
                resumo.setTotalTransacoes(rs.getInt("total_transacoes"));
                resumo.setVolumeMovimentado(rs.getBigDecimal("volume_movimentado"));
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao carregar indicadores do dashboard: " + e.getMessage(), e);
        }
        return resumo;
    }
}
