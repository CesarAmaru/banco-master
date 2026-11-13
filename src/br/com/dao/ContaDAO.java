
package br.com.dao;

import br.com.entidade.Conta;
import br.com.entidade.ContaCorrente;
import br.com.entidade.ContaPoupanca;
import br.com.entidade.TipoConta;
import br.com.entidade.StatusConta;
import br.com.exception.PersistenciaException;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class ContaDAO {

    // CADASTRAR CONTA
    public void inserir(Conta conta) {

        String sql = "INSERT INTO conta "
                + "(numero, agencia_id, cliente_id, funcionario_id, "
                + "tipo, status, saldo, limite, taxa_rendimento) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = ConexaoFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, conta.getNumero());
            stmt.setInt(2, conta.getAgenciaId());
            stmt.setInt(3, conta.getClienteId());

            if (conta.getFuncionarioId() == null) {
                stmt.setNull(4, Types.INTEGER);
            } else {
                stmt.setInt(4, conta.getFuncionarioId());
            }

            stmt.setString(5, conta.getTipo().name());
            stmt.setString(6, conta.getStatus().name());
            stmt.setBigDecimal(7, conta.getSaldo());
            stmt.setBigDecimal(8, conta.getLimite());
            stmt.setBigDecimal(9, conta.getTaxaRendimento());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new PersistenciaException(
        "Erro ao cadastrar conta: " + e.getMessage(), e
    );
}
    }

    // LISTAR CONTAS DE UM CLIENTE
    public List<Conta> listarPorCliente(int clienteId) {

        String sql = "SELECT * FROM conta "
                + "WHERE cliente_id = ? ORDER BY id";

        List<Conta> contas = new ArrayList<>();

        try (Connection con = ConexaoFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, clienteId);

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    contas.add(mapearConta(rs));
                }
            }

        } catch (SQLException e) {
            throw new PersistenciaException(
                    "Erro ao listar contas do cliente.", e);
        }

        return contas;
    }

    public List<Conta> listarTodas() {

    String sql = "SELECT * FROM conta ORDER BY id";
    List<Conta> contas = new ArrayList<>();

    try (Connection con = ConexaoFactory.getConnection();
         PreparedStatement stmt = con.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            contas.add(mapearConta(rs));
        }

    } catch (SQLException e) {
        throw new PersistenciaException(
                "Erro ao listar todas as contas.", e);
    }

    return contas;
}
    // BUSCAR CONTA PELO NUMERO
    // Recebe uma conexão externa para permitir transações.
    public Conta buscarPorNumero(Connection con, String numero) {

        String sql = "SELECT * FROM conta WHERE numero = ?";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, numero);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return mapearConta(rs);
                }
            }

        } catch (SQLException e) {
            throw new PersistenciaException(
                    "Erro ao buscar conta pelo número.", e);
        }

        return null;
    }

    // ATUALIZAR SALDO
    // Não abre nem fecha a conexão recebida.
    public void atualizarSaldo(Connection con, Conta conta) {

        String sql = "UPDATE conta SET saldo = ? WHERE id = ?";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setBigDecimal(1, conta.getSaldo());
            stmt.setInt(2, conta.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new PersistenciaException(
                    "Erro ao atualizar saldo da conta.", e);
        }
    }

    // ATUALIZAR STATUS DA CONTA
    public void atualizarStatus(Conta conta) {

        String sql = "UPDATE conta SET status = ? WHERE id = ?";

        try (Connection con = ConexaoFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, conta.getStatus().name());
            stmt.setInt(2, conta.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new PersistenciaException(
                    "Erro ao atualizar status da conta.", e);
        }
    }

    // ATUALIZAR DADOS DA CONTA
    public void atualizar(Conta conta) {

        String sql = "UPDATE conta SET "
                + "agencia_id = ?, funcionario_id = ?, "
                + "limite = ?, taxa_rendimento = ? "
                + "WHERE id = ?";

        try (Connection con = ConexaoFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, conta.getAgenciaId());

            if (conta.getFuncionarioId() == null) {
                stmt.setNull(2, Types.INTEGER);
            } else {
                stmt.setInt(2, conta.getFuncionarioId());
            }

            stmt.setBigDecimal(3, conta.getLimite());
            stmt.setBigDecimal(4, conta.getTaxaRendimento());
            stmt.setInt(5, conta.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new PersistenciaException(
                    "Erro ao atualizar conta.", e);
        }
    }

    // VERIFICAR SE O NUMERO JA EXISTE
    public boolean existeNumero(String numero) {

        String sql = "SELECT COUNT(*) FROM conta WHERE numero = ?";

        try (Connection con = ConexaoFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, numero);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }

        } catch (SQLException e) {
            throw new PersistenciaException(
                    "Erro ao verificar número da conta.", e);
        }

        return false;
    }

    // CONVERTER REGISTRO DO BANCO EM OBJETO CONTA
    private Conta mapearConta(ResultSet rs) throws SQLException {

        TipoConta tipo = TipoConta.valueOf(rs.getString("tipo"));

        Conta conta;

        if (tipo == TipoConta.CORRENTE) {
            conta = new ContaCorrente();
        } else {
            conta = new ContaPoupanca();
        }

        conta.setId(rs.getInt("id"));
        conta.setNumero(rs.getString("numero"));
        conta.setAgenciaId(rs.getInt("agencia_id"));
        conta.setClienteId(rs.getInt("cliente_id"));

        int funcionarioId = rs.getInt("funcionario_id");

        if (rs.wasNull()) {
            conta.setFuncionarioId(null);
        } else {
            conta.setFuncionarioId(funcionarioId);
        }

        conta.setTipo(tipo);
        conta.setStatus(
                StatusConta.valueOf(rs.getString("status"))
        );

        conta.setSaldo(rs.getBigDecimal("saldo"));
        conta.setLimite(rs.getBigDecimal("limite"));
        conta.setTaxaRendimento(rs.getBigDecimal("taxa_rendimento"));

        if (rs.getDate("data_abertura") != null) {
            conta.setDataAbertura(
                    rs.getDate("data_abertura").toLocalDate()
            );
        }

        return conta;
    }
}
