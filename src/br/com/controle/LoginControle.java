/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.controle;

/**
 * Autenticação do usuário (usado pela {@code TelaLogin}).
 *
 * <b>Camada:</b> Controle (Controller)
 * <p>
 * <b>O que esta classe deve fazer:</b>
 * <ul>
 *   <li>{@code autenticar(String cpf, String senha)}: limpa o CPF, busca com {@code ClienteDAO.buscarPorCpf}, rejeita se não existir ou estiver bloqueado, confere a senha com {@code SenhaUtil.verificar}.</li>
 *   <li>Senha errada: {@code cliente.registrarFalhaLogin()} e {@code ClienteDAO.atualizar}; na 3ª tentativa o cliente é bloqueado.</li>
 *   <li>Senha certa: {@code cliente.zerarTentativas()}, atualiza no banco e chama {@code Sessao.iniciar(cliente)}.</li>
 *   <li>{@code logout()}: chama {@code Sessao.encerrar()}.</li>
 * </ul>
 * <b>Atenção:</b>
 * <ul>
 *   <li>Não diga à tela se o erro foi no CPF ou na senha: use a mesma mensagem ("CPF ou senha inválidos").</li>
 * </ul>
 *
 * 
 */
public class LoginControle {
    // TODO: implementar conforme descrito acima.
}
