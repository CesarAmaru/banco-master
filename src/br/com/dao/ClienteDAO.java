/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.dao;

import br.com.entidade.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Acesso ao banco para a tabela {@code cliente}. Só SQL/JDBC, nenhuma regra de
 * negócio.
 *
 * <b>Camada:</b> DAO (persistência)
 * <p>
 * <b>O que esta classe deve fazer:</b>
 * <ul>
 * <li>{@code void inserir(Cliente c)}</li>
 * <li>{@code Cliente buscarPorId(int id)}</li>
 * <li>{@code Cliente buscarPorCpf(String cpf)}: devolve {@code null} se não
 * existir (usado no login).</li>
 * <li>{@code void atualizar(Cliente c)}: grava, por exemplo,
 * {@code tentativas_login} e {@code bloqueado}.</li>
 * <li>{@code List<Cliente> listar()}</li>
 * </ul>
 * <b>Atenção:</b>
 * <ul>
 * <li>Use sempre {@code PreparedStatement} (evita SQL injection).</li>
 * <li>Converta {@code SQLException} em {@code PersistenciaException}; a tela
 * não deve depender de JDBC.</li>
 * </ul>
 *
 *
 */
public class ClienteDAO {

    public void inserir(Cliente c) {
        String sql = "insert into cliente (nome, cpf, senha_hash, data_nascimento, tentativas_login) "
                + "values (?, ? , ?, ?, ?)";
        try (Connection cn = ConexaoFactory.getConnection(); PreparedStatement ps = cn.prepareStatement(sql);) {
            ps.setString(1, c.getNome());
            ps.setString(2, c.getCpf());
            ps.setString(3, c.getSenha_hash());
            ps.setObject(4, c.getData_nascimento());
            ps.setInt(5, 1);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }

    public Cliente buscarPorId(Integer id) {
        String sql = "select nome, cpf, senha_hash, data_nascimento, tentativas_login, bloqueado "
                + "from cliente where id = ?";
        Cliente aux = new Cliente();
        try (Connection cn = ConexaoFactory.getConnection(); PreparedStatement ps = cn.prepareStatement(sql);) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery();) {
                if (rs.next()) {
                    aux.setId(rs.getInt("id"));
                    aux.setNome(rs.getString("nome"));
                    aux.setCpf(rs.getString("cpf"));
                    aux.setSenha_hash(rs.getString("senha_hash"));
                    aux.setData_nascimento(rs.getObject("data_nascimento", LocalDate.class));
                    aux.setTentativas_login(rs.getInt("tentativas_login"));
                    aux.setBloaqueado(rs.getBoolean("bloqueado"));
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro: " + e.getMessage());
        }
        return aux;
    }

    public Cliente buscarPorCPF(String cpf) {
        String sql = "select nome, cpf, senha_hash, data_nascimento, tentativas_login, bloqueado "
                + "from cliente where id = ?";
        Cliente aux = new Cliente();
        try (Connection cn = ConexaoFactory.getConnection(); PreparedStatement ps = cn.prepareStatement(sql);) {
            ps.setString(1, cpf);
            try (ResultSet rs = ps.executeQuery();) {
                if (rs.next()) {
                    aux.setId(rs.getInt("id"));
                    aux.setNome(rs.getString("nome"));
                    aux.setCpf(rs.getString("cpf"));
                    aux.setSenha_hash(rs.getString("senha_hash"));
                    aux.setData_nascimento(rs.getObject("data_nascimento", LocalDate.class));
                    aux.setTentativas_login(rs.getInt("tentativas_login"));
                    aux.setBloaqueado(rs.getBoolean("bloqueado"));
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro: " + e.getMessage());
        }
        return aux;
    }

    public List<Cliente> listar() {
        String sql = "select * from cliente";
        List<Cliente> clienteList = new ArrayList<>();
        try (Connection cn = ConexaoFactory.getConnection(); PreparedStatement ps = cn.prepareStatement(sql); ResultSet rs = ps.executeQuery();) {
            while (rs.next()) {
                Cliente aux = new Cliente();
                aux.setId(rs.getInt("id"));
                aux.setNome(rs.getString("nome"));
                aux.setCpf(rs.getString("cpf"));
                aux.setSenha_hash(rs.getString("senha_hash"));
                aux.setData_nascimento(rs.getObject("data_nascimento", LocalDate.class));
                aux.setTentativas_login(rs.getInt("tentativas_login"));
                aux.setBloaqueado(rs.getBoolean("bloqueado"));
                clienteList.add(aux);
            }

        } catch (SQLException e) {
            System.err.println("Erro: " + e.getMessage());
        }

        return clienteList;
    }

    public void atualizar(Cliente c) {
        String sql = "update cliente set nome = ?, senha_hash = ?, bloqueado = ? "
                + "where id = ?";
        try (Connection cn = ConexaoFactory.getConnection(); PreparedStatement ps = cn.prepareStatement(sql);) {
            ps.setString(1, c.getNome());
            ps.setString(2, c.getSenha_hash());
            ps.setBoolean(3, c.getBloaqueado());
            ps.setInt(4, c.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }

    public void excluir(Cliente c) {
        String sql = "delete from cliente where id = ?";
        try (Connection cn = ConexaoFactory.getConnection(); PreparedStatement ps = cn.prepareCall(sql);) {
            ps.setInt(1, c.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }
}
