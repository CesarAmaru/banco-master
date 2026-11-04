/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.entidade;

/**
 * Representa o titular das contas.
 *
 * <b>Camada:</b> Entidade (Model)
 * <p>
 * <b>O que esta classe deve fazer:</b>
 * <ul>
 *   <li>Atributos privados: {@code dataNascimento} ({@code LocalDate}), {@code tentativasLogin} ({@code int}), {@code bloqueado} ({@code boolean}) (e os de {@code Usuario}, se herdar dela).</li>
 *   <li>Construtores, getters e setters.</li>
 *   <li>{@code isMaiorDeIdade()}: retorna {@code true} se tiver 18 anos ou mais (use {@code Period.between}).</li>
 *   <li>{@code registrarFalhaLogin()}: soma 1 em {@code tentativasLogin} e, ao chegar em 3, marca {@code bloqueado = true}.</li>
 *   <li>{@code zerarTentativas()}: volta {@code tentativasLogin} para 0 após um login correto.</li>
 * </ul>
 * <b>Atenção:</b>
 * <ul>
 *   <li>CPF único e válido (a validação fica em {@code CpfValidar}; a unicidade, no banco e no {@code ClienteDAO}).</li>
 *   <li>Só maiores de 18 anos podem ser cadastrados.</li>
 *   <li>Esta classe só guarda dados e regras do próprio cliente: sem SQL e sem {@code JOptionPane}.</li>
 * </ul>
 *
 * 
 */
public class Cliente {
    // TODO: implementar conforme descrito acima.
}
