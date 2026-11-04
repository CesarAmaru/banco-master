/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.controle;

/**
 * Ponte entre {@code TelaAbrirConta}/{@code TelaPrincipal} e o {@code ContaService}.
 *
 * <b>Camada:</b> Controle (Controller)
 * <p>
 * <b>O que esta classe deve fazer:</b>
 * <ul>
 *   <li>{@code abrirConta(TipoConta tipo)}: pega o cliente de {@code Sessao.getClienteLogado()} e delega ao {@code ContaService}.</li>
 *   <li>{@code listarContasDoCliente()}: contas do cliente logado (para a tela principal).</li>
 *   <li>{@code encerrarConta(String numero)}, {@code bloquearConta(String numero)}.</li>
 *   <li>{@code consultarSaldo(String numero)}.</li>
 * </ul>
 * <b>Atenção:</b>
 * <ul>
 *   <li>Só aceita operar em contas do cliente logado.</li>
 * </ul>
 *
 * 
 */
public class ContaControle {
    // TODO: implementar conforme descrito acima.
}
