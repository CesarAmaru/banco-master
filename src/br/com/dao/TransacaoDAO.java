/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.dao;

/**
 * Acesso ao banco para a tabela {@code transacao} (movimentações).
 *
 * <b>Camada:</b> DAO (persistência)
 * <br><b>Responsável:</b> Integrante 4
 * <p>
 * <b>O que esta classe deve fazer:</b>
 * <ul>
 *   <li>{@code void registrar(Connection con, Transacao t)}: {@code INSERT} usando a conexão recebida; grava {@code NULL} em origem/destino quando forem nulos.</li>
 *   <li>{@code List<Transacao> listarPorConta(String numeroConta)}: movimentações em que a conta é origem ou destino, em ordem de data.</li>
 *   <li>{@code List<Transacao> listarPorPeriodo(String numeroConta, LocalDate inicio, LocalDate fim)}: para o filtro do extrato.</li>
 *   <li>Método privado {@code montarTransacao(ResultSet rs)}: converte uma linha do banco em objeto (evita repetir código).</li>
 *   <li>Para a tela de extrato (Integrante 5): uma consulta com {@code JOIN} entre {@code transacao}, {@code conta} e {@code cliente} para trazer também o nome do titular.</li>
 * </ul>
 * <b>Atenção:</b>
 * <ul>
 *   <li>{@code registrar} NÃO abre conexão própria: usa a recebida, para participar do {@code commit}/{@code rollback} do service.</li>
 *   <li>Não existe método de {@code UPDATE} nem {@code DELETE}: transação registrada não se edita nem se apaga.</li>
 *   <li>Coluna {@code tipo} guarda o texto do enum; {@code conta_origem_id} e {@code conta_destino_id} aceitam {@code NULL}.</li>
 * </ul>
 *
 * 
 */
public class TransacaoDAO {
    // TODO: implementar conforme descrito acima.
}
