/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.dao;

/**
 * Acesso ao banco para a tabela {@code conta}.
 *
 * <b>Camada:</b> DAO (persistência)
 * <br><b>Responsável:</b> COMPARTILHADO - o Integrante 4 mexe só em {@code buscarPorNumero(Connection, ...)} e {@code atualizarSaldo(Connection, ...)}
 * <p>
 * <b>O que esta classe deve fazer:</b>
 * <ul>
 *   <li>{@code void inserir(Conta c)}</li>
 *   <li>{@code Conta buscarPorNumero(Connection con, String numero)}: devolve {@code null} se não existir; cria {@code ContaCorrente} ou {@code ContaPoupanca} conforme a coluna {@code tipo}.</li>
 *   <li>{@code void atualizarSaldo(Connection con, Conta c)}: {@code UPDATE conta SET saldo = ? WHERE id = ?}.</li>
 *   <li>{@code List<Conta> listarPorCliente(int clienteId)}</li>
 *   <li>{@code void atualizarStatus(Conta c)}: bloquear/encerrar.</li>
 *   <li>{@code boolean existeNumero(String numero)} / geração do número único da conta.</li>
 * </ul>
 * <b>Atenção:</b>
 * <ul>
 *   <li>Os métodos que participam de depósito, saque ou transferência RECEBEM a {@code Connection} por parâmetro: assim todos usam a mesma conexão e o {@code rollback} funciona. Não abra conexão nova dentro deles.</li>
 *   <li>Valor monetário: {@code setBigDecimal} / {@code getBigDecimal} (coluna {@code DECIMAL(15,2)}).</li>
 * </ul>
 *
 * 
 */
public class ContaDAO {
    // TODO: implementar conforme descrito acima.
}
