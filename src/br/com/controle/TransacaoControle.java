/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.controle;

/**
 * Ponte entre {@code TelaDeposito}, {@code TelaSaque}, {@code TelaTransferencia}, {@code TelaExtrato} e o {@code TransacaoService}.
 *
 * <b>Camada:</b> Controle (Controller)
 * <br><b>Responsável:</b> Integrante 4 (expõe os métodos para a tela do Integrante 5)
 * <p>
 * <b>O que esta classe deve fazer:</b>
 * <ul>
 *   <li>Atributo {@code TransacaoService service}.</li>
 *   <li>{@code depositar(String numeroConta, String valorTexto)}, {@code sacar(...)} e {@code transferir(String origem, String destino, String valorTexto)}: convertem {@code valorTexto} em {@code BigDecimal} (trocando vírgula por ponto, tratando {@code NumberFormatException}) e chamam o service.</li>
 *   <li>{@code listarExtrato(String numeroConta)} e {@code listarExtrato(String numeroConta, LocalDate inicio, LocalDate fim)}.</li>
 * </ul>
 * <b>Atenção:</b>
 * <ul>
 *   <li>Combine com o Integrante 5 os nomes e assinaturas destes métodos para ele montar as telas sem esperar.</li>
 *   <li>Sem regra de negócio: ela fica nas entidades e no service.</li>
 * </ul>
 *
 * 
 */
public class TransacaoControle {
    // TODO: implementar conforme descrito acima.
}
