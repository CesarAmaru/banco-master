package br.com.dao;

import br.com.entidade.Funcionario;
import br.com.exception.PersistenciaException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAO {

    // CADASTRAR FUNCIONARIO
    public void inserir(Funcionario funcionario) {

        String sql = "INSERT INTO funcionario "
                + "(nome, cpf, cargo, data_admissao, agencia_id) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = ConexaoFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, funcionario.getNome());
            stmt.setString(2, funcionario.getCpf());
            stmt.setString(3, funcionario.getCargo());
            stmt.setDate(4, Date.valueOf(funcionario.getDataAdmissao()));
            stmt.setInt(5, funcionario.getAgenciaId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new PersistenciaException(
                    "Erro ao cadastrar funcionário.", e);
        }
    }

    // LISTAR TODOS OS FUNCIONARIOS
    public List<Funcionario> listarTodos() {

        String sql = "SELECT * FROM funcionario ORDER BY id";

        List<Funcionario> funcionarios = new ArrayList<>();

        try (Connection con = ConexaoFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Funcionario funcionario = new Funcionario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getString("cargo"),
                        rs.getDate("data_admissao").toLocalDate(),
                        rs.getInt("agencia_id")
                );

                funcionarios.add(funcionario);
            }

        } catch (SQLException e) {
            throw new PersistenciaException(
                    "Erro ao listar funcionários.", e);
        }

        return funcionarios;
    }

    // BUSCAR FUNCIONARIO PELO ID
    public Funcionario buscarPorId(int id) {

        String sql = "SELECT * FROM funcionario WHERE id = ?";

        try (Connection con = ConexaoFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {

                    return new Funcionario(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getString("cpf"),
                            rs.getString("cargo"),
                            rs.getDate("data_admissao").toLocalDate(),
                            rs.getInt("agencia_id")
                    );
                }
            }

        } catch (SQLException e) {
            throw new PersistenciaException(
                    "Erro ao buscar funcionário.", e);
        }

        return null;
    }

    // PESQUISAR POR NOME OU CPF
    public List<Funcionario> pesquisar(String termo) {

        String sql = "SELECT * FROM funcionario "
                + "WHERE nome LIKE ? OR cpf LIKE ? "
                + "ORDER BY nome";

        List<Funcionario> funcionarios = new ArrayList<>();

        try (Connection con = ConexaoFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, "%" + termo + "%");
            stmt.setString(2, "%" + termo + "%");

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {

                    Funcionario funcionario = new Funcionario(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getString("cpf"),
                            rs.getString("cargo"),
                            rs.getDate("data_admissao").toLocalDate(),
                            rs.getInt("agencia_id")
                    );

                    funcionarios.add(funcionario);
                }
            }

        } catch (SQLException e) {
            throw new PersistenciaException(
                    "Erro ao pesquisar funcionários.", e);
        }

        return funcionarios;
    }

    // ATUALIZAR FUNCIONARIO
    public void atualizar(Funcionario funcionario) {

        String sql = "UPDATE funcionario SET "
                + "nome = ?, cpf = ?, cargo = ?, "
                + "data_admissao = ?, agencia_id = ? "
                + "WHERE id = ?";

        try (Connection con = ConexaoFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, funcionario.getNome());
            stmt.setString(2, funcionario.getCpf());
            stmt.setString(3, funcionario.getCargo());
            stmt.setDate(4, Date.valueOf(funcionario.getDataAdmissao()));
            stmt.setInt(5, funcionario.getAgenciaId());
            stmt.setInt(6, funcionario.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new PersistenciaException(
                    "Erro ao atualizar funcionário.", e);
        }
    }

    // EXCLUIR FUNCIONARIO
    public void excluir(int id) {

        String sql = "DELETE FROM funcionario WHERE id = ?";

        try (Connection con = ConexaoFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new PersistenciaException(
                    "Erro ao excluir funcionário. Verifique se ele está vinculado a alguma conta.", e);
        }
    }
}
