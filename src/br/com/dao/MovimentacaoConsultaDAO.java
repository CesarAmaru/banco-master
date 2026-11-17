package br.com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Consultas com INNER JOIN usadas pelas telas de movimentação.
 * Responsável: Integrante 5
 */
public class MovimentacaoConsultaDAO {

    /**
     * Lista as contas para os combos, já com o nome do titular.
     * Cada item fica assim: "100001-0 - Ana Beatriz Souza (CORRENTE)"
     */
    public List<String> listarContas() throws SQLException {
        String sql = "SELECT c.numero, cl.nome, c.tipo "
                   + "FROM conta c "
                   + "INNER JOIN cliente cl ON cl.id = c.cliente_id "
                   + "WHERE c.status <> 'ENCERRADA' "
                   + "ORDER BY c.numero";

        List<String> contas = new ArrayList<>();
        try (Connection con = ConexaoFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                contas.add(rs.getString("numero") + " - " + rs.getString("nome")
                        + " (" + rs.getString("tipo") + ")");
            }
        }
        return contas;
    }

    /**
     * Lista as movimentações com: data, tipo, valor, número da conta e nome do cliente.
     * Se numeroConta for null, traz as movimentações de todas as contas.
     *
     * O INNER JOIN liga a transação à conta (como origem OU destino)
     * e a conta ao cliente dono dela.
     * Obs.: sem filtro, uma transferência aparece 2 vezes (conta que enviou e conta que recebeu).
     */
    public List<Object[]> listarMovimentacoes(String numeroConta) throws SQLException {
        String sql = "SELECT t.data_hora, t.tipo, t.valor, c.numero, cl.nome, "
                   + "       CASE WHEN c.id = t.conta_origem_id THEN 'Saída' ELSE 'Entrada' END AS sentido "
                   + "FROM transacao t "
                   + "INNER JOIN conta c ON c.id = t.conta_origem_id OR c.id = t.conta_destino_id "
                   + "INNER JOIN cliente cl ON cl.id = c.cliente_id ";
        if (numeroConta != null) {
            sql += "WHERE c.numero = ? ";
        }
        sql += "ORDER BY t.data_hora";

        List<Object[]> linhas = new ArrayList<>();
        try (Connection con = ConexaoFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            if (numeroConta != null) {
                ps.setString(1, numeroConta);
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    linhas.add(new Object[]{
                        rs.getTimestamp("data_hora"),
                        rs.getString("tipo"),
                        rs.getBigDecimal("valor"),
                        rs.getString("sentido"),
                        rs.getString("numero"),
                        rs.getString("nome")
                    });
                }
            }
        }
        return linhas;
    }
}
