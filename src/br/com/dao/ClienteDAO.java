/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.dao;

/**
 * Acesso ao banco para a tabela {@code cliente}. Só SQL/JDBC, nenhuma regra de negócio.
 *
 * <b>Camada:</b> DAO (persistência)
 * <p>
 * <b>O que esta classe deve fazer:</b>
 * <ul>
 *   <li>{@code void inserir(Cliente c)}</li>
 *   <li>{@code Cliente buscarPorId(int id)}</li>
 *   <li>{@code Cliente buscarPorCpf(String cpf)}: devolve {@code null} se não existir (usado no login).</li>
 *   <li>{@code boolean existeCpf(String cpf)}: evita CPF duplicado.</li>
 *   <li>{@code void atualizar(Cliente c)}: grava, por exemplo, {@code tentativas_login} e {@code bloqueado}.</li>
 *   <li>{@code List<Cliente> listar()}</li>
 * </ul>
 * <b>Atenção:</b>
 * <ul>
 *   <li>Use sempre {@code PreparedStatement} (evita SQL injection).</li>
 *   <li>Converta {@code SQLException} em {@code PersistenciaException}; a tela não deve depender de JDBC.</li>
 * </ul>
 *
 * 
 */
public class ClienteDAO {
    // TODO: implementar conforme descrito acima.
}
