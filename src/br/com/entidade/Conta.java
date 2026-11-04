/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.entidade;

/**
 * Classe-base ABSTRATA de todas as contas (herança + polimorfismo).
 *
 * <b>Camada:</b> Entidade (Model)
 * <br><b>Responsável:</b> Integrante 4 depende dos métodos {@code depositar} e {@code sacar} desta classe
 * <p>
 * <b>O que esta classe deve fazer:</b>
 * <ul>
 *   <li>Declarar como {@code public abstract class Conta}.</li>
 *   <li>Atributos privados/protegidos: {@code id}, {@code numero} (String), {@code agencia}, {@code saldo} ({@code BigDecimal}, começa em {@code BigDecimal.ZERO}), {@code status} ({@code StatusConta}), {@code tipo} ({@code TipoConta}), {@code cliente} ({@code Cliente}).</li>
 *   <li>{@code depositar(BigDecimal valor)}: valida valor > 0 e conta ATIVA; soma ao saldo.</li>
 *   <li>{@code abstract void sacar(BigDecimal valor)}: cada tipo de conta implementa a sua regra (polimorfismo).</li>
 *   <li>{@code bloquear()}, {@code desbloquear()}: alteram o {@code status}.</li>
 *   <li>{@code encerrar()}: só permite se o saldo for zero; muda o status para ENCERRADA.</li>
 *   <li>{@code estaAtiva()}: retorna {@code status == StatusConta.ATIVA}.</li>
 *   <li>Getters e setters; construtores.</li>
 * </ul>
 * <b>Atenção:</b>
 * <ul>
 *   <li>Dinheiro sempre em {@code BigDecimal}, nunca {@code double}. Use {@code compareTo}, não {@code equals}, para comparar valores.</li>
 *   <li>Conta BLOQUEADA ou ENCERRADA não faz nenhuma operação: lance exceção.</li>
 *   <li>Valor nulo, zero ou negativo: lance {@code IllegalArgumentException} (ou uma {@code ValorInvalidoException} própria).</li>
 *   <li>Saldo insuficiente: lance {@code SaldoInsuficienteException}.</li>
 * </ul>
 *
 * 
 */
public class Conta {
    // TODO: implementar conforme descrito acima.
}
