/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.service;

/**
 * Fluxo de depósito, saque, transferência e extrato, com controle de transação SQL. (O comentário antigo desta classe estava trocado com o da {@code ContaService}.)
 *
 * <b>Camada:</b> Service (regras de fluxo)
 * <br><b>Responsável:</b> Integrante 4
 * <p>
 * <b>O que esta classe deve fazer:</b>
 * <ul>
 *   <li>Atributos: {@code ContaDAO contaDAO} e {@code TransacaoDAO transacaoDAO}; construtor que os recebe.</li>
 *   <li>{@code depositar(String numeroConta, BigDecimal valor)}: valida valor, abre conexão, {@code setAutoCommit(false)}, busca a conta, {@code conta.depositar(valor)}, {@code atualizarSaldo}, registra DEPOSITO, {@code commit}. Em erro: {@code rollback} e relança.</li>
 *   <li>{@code sacar(String numeroConta, BigDecimal valor)}: mesmo fluxo com {@code conta.sacar(valor)} e registro SAQUE.</li>
 *   <li>{@code transferir(String origem, String destino, BigDecimal valor)}: valida valor e origem != destino; saca da origem, deposita no destino, atualiza as DUAS contas, registra TRANSFERENCIA, {@code commit}. Qualquer erro: {@code rollback} (ou tudo acontece, ou nada).</li>
 *   <li>{@code listarTransacoes(String numeroConta)} e {@code listarPorPeriodo(...)}: repassam ao {@code TransacaoDAO} (usados pela {@code TelaExtrato}).</li>
 *   <li>Privados: {@code validarValor(BigDecimal)} (lança exceção se nulo ou {@code <= 0}) e {@code buscarContaOuFalhar(Connection, String)}.</li>
 * </ul>
 * <b>Atenção:</b>
 * <ul>
 *   <li>Todos os DAOs de uma mesma operação usam a MESMA {@code Connection}; senão o rollback não funciona.</li>
 *   <li>A validação de saldo fica na entidade ({@code Conta.sacar}); o service só chama.</li>
 *   <li>Para testar o rollback: force um erro no meio da transferência e confirme no banco que nenhum saldo mudou.</li>
 * </ul>
 *
 * 
 */
public class TransacaoService {
    // TODO: implementar conforme descrito acima.
}
