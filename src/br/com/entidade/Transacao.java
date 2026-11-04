/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.entidade;

/**
 * Representa UMA movimentação registrada (depósito, saque ou transferência).
 *
 * <b>Camada:</b> Entidade (Model)
 * <br><b>Responsável:</b> Integrante 4
 * <p>
 * <b>O que esta classe deve fazer:</b>
 * <ul>
 *   <li>Atributos privados: {@code id}, {@code tipo} ({@code TipoTransacao}), {@code valor} ({@code BigDecimal}), {@code dataHora} ({@code LocalDateTime}), {@code contaOrigem} ({@code Conta}), {@code contaDestino} ({@code Conta}).</li>
 *   <li>Construtor vazio (o DAO usa para montar o objeto vindo do banco).</li>
 *   <li>Construtor completo {@code (tipo, valor, origem, destino)} que preenche {@code dataHora} com {@code LocalDateTime.now()}.</li>
 *   <li>Getters e setters.</li>
 * </ul>
 * <b>Atenção:</b>
 * <ul>
 *   <li>DEPÓSITO: {@code contaOrigem} fica {@code null}. SAQUE: {@code contaDestino} fica {@code null}.</li>
 *   <li>Sem regra de negócio aqui, só dados. Uma transação registrada nunca é editada nem apagada.</li>
 * </ul>
 *
 * 
 */
public class Transacao {
    // TODO: implementar conforme descrito acima.
}
