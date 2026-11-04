/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.entidade;

/**
 * Conta poupança: herda de {@code Conta} e acrescenta o rendimento.
 *
 * <b>Camada:</b> Entidade (Model)
 * <p>
 * <b>O que esta classe deve fazer:</b>
 * <ul>
 *   <li>Declarar como {@code public class ContaPoupanca extends Conta}.</li>
 *   <li>Atributo: {@code taxaRendimento} ({@code BigDecimal}, ex.: 0.005 = 0,5% ao mês).</li>
 *   <li>Implementar {@code sacar(BigDecimal valor)} com {@code @Override}: só permite se {@code valor <= saldo}.</li>
 *   <li>{@code aplicarRendimento()}: soma ao saldo {@code saldo * taxaRendimento} (use {@code setScale(2, RoundingMode.HALF_UP)}).</li>
 * </ul>
 * <b>Atenção:</b>
 * <ul>
 *   <li>A poupança nunca pode ficar com saldo negativo.</li>
 * </ul>
 *
 * 
 */
public class ContaPoupanca {
    // TODO: implementar conforme descrito acima.
}
