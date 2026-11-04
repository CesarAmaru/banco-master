/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.entidade;

/**
 * Conta corrente: herda de {@code Conta} e acrescenta o limite de cheque especial.
 *
 * <b>Camada:</b> Entidade (Model)
 * <p>
 * <b>O que esta classe deve fazer:</b>
 * <ul>
 *   <li>Declarar como {@code public class ContaCorrente extends Conta}.</li>
 *   <li>Atributo: {@code limite} ({@code BigDecimal}, ex.: R$ 500,00).</li>
 *   <li>Implementar {@code sacar(BigDecimal valor)} com {@code @Override}: permite sacar se {@code valor <= saldo + limite}; senão lança {@code SaldoInsuficienteException}.</li>
 *   <li>Getter e setter do limite.</li>
 * </ul>
 * <b>Atenção:</b>
 * <ul>
 *   <li>O saldo pode ficar negativo, mas nunca abaixo de {@code -limite}.</li>
 * </ul>
 *
 * 
 */
public class ContaCorrente {
    // TODO: implementar conforme descrito acima.
}
