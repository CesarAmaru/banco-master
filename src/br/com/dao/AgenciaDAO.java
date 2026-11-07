/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.dao;

import br.com.entidade.Agencia;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AgenciaDAO {
    public void inserir(Agencia a){
        String sql = "insert into agencia(numero, nome, cidade, uf) "
                + "values (?, ?, ?, ?)";
        try(Connection cn = ConexaoFactory.getConnection();
            PreparedStatement ps = cn.prepareStatement(sql);){
            ps.setString(1, a.getNumero());
            ps.setString(2, a.getNome());
            ps.setString(3, a.getCidade());
            ps.setString(4, a.getUf());
            ps.executeUpdate();
            
        }catch (SQLException e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }
    
    public Agencia buscaPorId(Integer id){
        String sql = "select id, nome, numero, cidade, uf from agencia"
                + "where id = ?";
        Agencia aux = new Agencia();
        try(Connection cn = ConexaoFactory.getConnection();
            PreparedStatement ps = cn.prepareStatement(sql);){
            ps.setInt(1, id);
            try(ResultSet rs = ps.executeQuery();){
                if(rs.next()){
                    aux.setId(rs.getInt("id"));
                    aux.setNome(rs.getString("nome"));
                    aux.setNumero(rs.getString("numero"));
                    aux.setCidade(rs.getString("cidade"));
                    aux.setUf(rs.getString("uf"));
                }
            }
            
        }catch (SQLException e) {
            System.err.println("Erro: " + e.getMessage());
        }
        return aux;
    }
    
    public List<Agencia> listar(){
        List<Agencia> list = new ArrayList<>();
        String sql = "select * from agencia";
        try(Connection cn = ConexaoFactory.getConnection();
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();){
            while(rs.next()){
                Agencia aux = new Agencia();
                aux.setId(rs.getInt("id"));
                    aux.setNome(rs.getString("nome"));
                    aux.setNumero(rs.getString("numero"));
                    aux.setCidade(rs.getString("cidade"));
                    aux.setUf(rs.getString("uf"));
                    list.add(aux);
            }
        }catch (SQLException e) {
            System.err.println("Erro: " + e.getMessage());
        }
        return list;
    }
    
    public void atualizar(Agencia a){
        String sql = "update agencia set nome = ?, cidade = ?, uf = ? "
                + "where id = ?";
        try(Connection cn = ConexaoFactory.getConnection();
            PreparedStatement ps = cn.prepareStatement(sql);){
            ps.setString(1, a.getNome());
            ps.setString(2, a.getCidade());
            ps.setString(3, a.getUf());
            ps.setInt(4, a.getId());
            ps.executeUpdate();
        }catch (SQLException e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }
    public void excluir(Agencia a){
        String sql = "delete from agencia where id = ?";
        try (Connection cn = ConexaoFactory.getConnection(); 
            PreparedStatement ps = cn.prepareCall(sql);) {
            ps.setInt(1, a.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }
}
