/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.util;

/**
 * Guarda o cliente autenticado enquanto o programa está aberto.
 *
 * Camada: Util (apoio)
 * 
 * O que esta classe deve fazer:
 * 
 *   Atributos {@code private static}: {@code clienteLogado} ({@code Cliente}) e, opcional, {@code contaAtual} ({@code Conta}).
 *   Construtor privado (impede instanciar).
 *   {@code static void iniciar(Cliente c)}: chamado pelo {@code LoginControle} após autenticar.
 *   {@code static void encerrar()}: logout; coloca os atributos em {@code null}.
 *   {@code static Cliente getClienteLogado()}, {@code static boolean estaLogado()}.
 *   {@code static Conta getContaAtual()} / {@code static void setContaAtual(Conta c)} (opcional).
 * 
 * Atenção:
 * 
 *   As telas leem a Sessão para saber quem está logado. Os services NÃO usam a Sessão: recebem o número da conta por parâmetro.
 * 
 */
public class Sessao {
    // TODO: implementar conforme descrito acima.
}
