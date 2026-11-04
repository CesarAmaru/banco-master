/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.dao;

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
public class ConexaoFactory {
    // TODO: implementar conforme descrito acima.
}
