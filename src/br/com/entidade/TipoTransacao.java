/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.entidade;

/**
 * Tipos de movimentação financeira.
 *
 * <b>Camada:</b> Entidade (Model)
 * <br><b>Responsável:</b> Integrante 4
 * <p>
 * <b>O que esta classe deve fazer:</b>
 * <ul>
 *   <li>TROCAR {@code public class TipoTransacao} por {@code public enum TipoTransacao}.</li>
 *   <li>Valores: {@code DEPOSITO}, {@code SAQUE}, {@code TRANSFERENCIA}.</li>
 *   <li>No banco, grave o texto ({@code tipo.name()}) e leia com {@code TipoTransacao.valueOf(texto)}.</li>
 *   <li>Opcional: {@code getDescricao()} para exibir no extrato.</li>
 * </ul>
 *
 * 
 */
public enum TipoTransacao {
    // TODO: implementar conforme descrito acima.
    
    DEPOSITO("Depósito"),
    SAQUE("Saque"),
    TRANSFERENCIA("Transferência");
    
    private String descricao;
    
    TipoTransacao(String descricao) {
        this.descricao = descricao;
    }
    
    public String getDescricao() {
        return descricao;
    }
}
