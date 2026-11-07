/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.dao;

import java.sql.Connection;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.sql.DriverManager;

/**
 * Fábrica de conexões JDBC: único lugar que conhece URL, usuário e senha do banco.
 *
 * <b>Camada:</b> DAO (persistência)
 * <p>
 * <b>O que esta classe deve fazer:</b>
 * <ul>
 *   <li>Constantes {@code URL}, {@code USUARIO}, {@code SENHA}.</li>
 *   <li>{@code static Connection getConexao()}: devolve {@code DriverManager.getConnection(...)}; converte {@code SQLException} em {@code PersistenciaException}.</li>
 *   <li>Adicione o driver JDBC (MySQL, PostgreSQL ou SQLite) em Libraries no NetBeans.</li>
 * </ul>
 * <b>Atenção:</b>
 * <ul>
 *   <li>Quem abre a conexão deve fechá-la (use try-with-resources).</li>
 *   <li>Confirme com o grupo o nome do método ({@code getConexao} ou {@code getConnection}) para todos usarem o mesmo.</li>
 * </ul>
 *
 * 
 */
import java.sql.SQLException;/**
 * Fábrica de conexões JDBC: único lugar que conhece URL, usuário e senha do banco.
 *
 * <b>Camada:</b> DAO (persistência)
 * <p>
 * <b>O que esta classe deve fazer:</b>
 * <ul>
 *   <li>Constantes {@code URL}, {@code USUARIO}, {@code SENHA}.</li>
 *   <li>{@code static Connection getConexao()}: devolve {@code DriverManager.getConnection(...)}; converte {@code SQLException} em {@code PersistenciaException}.</li>
 *   <li>Adicione o driver JDBC (MySQL, PostgreSQL ou SQLite) em Libraries no NetBeans.</li>
 * </ul>
 * <b>Atenção:</b>
 * <ul>
 *   <li>Quem abre a conexão deve fechá-la (use try-with-resources).</li>
 *   <li>Confirme com o grupo o nome do método ({@code getConexao} ou {@code getConnection}) para todos usarem o mesmo.</li>
 * </ul>
 *
 * 
 */
public class ConexaoFactory {
    private static Properties carregarPropriedades(){
        Properties props = new Properties();
        try(FileInputStream fs = new FileInputStream("config.properties")){
            props.load(fs);
        }catch(IOException e){
            System.err.println("Erro ao carregar arquivo config.properties." + e.getMessage());
        }
        return props;
    }
    
    public static Connection getConnection() throws SQLException{
        Properties props = carregarPropriedades();
        String url = props.getProperty("db.url");
        String usuario = props.getProperty("db.user");
        String senha = props.getProperty("db.password");
        
        return DriverManager.getConnection(url, usuario, senha);
    }
}
