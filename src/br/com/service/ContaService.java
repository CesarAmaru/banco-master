/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.service;

/**
 * Fluxo de gerenciamento de contas.
 *
 * <b>Camada:</b> Service (regras de fluxo)
 * <p>
 * <b>O que esta classe deve fazer:</b>
 * <ul>
 *   <li>{@code abrirConta(Cliente cliente, TipoConta tipo)}: confere se o cliente já não tem conta desse tipo, gera número único, saldo inicial zero, status ATIVA, e chama {@code ContaDAO.inserir}.</li>
 *   <li>{@code encerrarConta(String numero)}: só se o saldo for zero.</li>
 *   <li>{@code bloquearConta(String numero)} / {@code desbloquearConta(String numero)}.</li>
 *   <li>{@code listarContasDoCliente(int clienteId)}.</li>
 *   <li>{@code consultarSaldo(String numero)}.</li>
 * </ul>
 * <b>Atenção:</b>
 * <ul>
 *   <li>Cliente com no máximo uma conta de cada tipo.</li>
 *   <li>Um cliente só acessa as próprias contas.</li>
 *   <li>Sem {@code JOptionPane}: erros sobem como exceção e a tela mostra a mensagem.</li>
 * </ul>
 *
 * 
 */
public class ContaService {
    // TODO: implementar conforme descrito acima.
}
