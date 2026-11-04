/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.controle;

/**
 * Ponte entre {@code TelaCadastroCliente} e o cadastro de clientes.
 *
 * <b>Camada:</b> Controle (Controller)
 * <p>
 * <b>O que esta classe deve fazer:</b>
 * <ul>
 *   <li>{@code cadastrar(String nome, String cpf, String dataNascimento, String senha, String confirmarSenha)}: converte os textos da tela, valida (campos obrigatórios, CPF via {@code CpfValidar}, maior de 18, senha >= 6 e igual à confirmação), gera o hash com {@code SenhaUtil}, confere CPF duplicado e chama {@code ClienteDAO.inserir}.</li>
 *   <li>{@code buscarPorCpf(String cpf)} (se alguma tela precisar).</li>
 * </ul>
 * <b>Atenção:</b>
 * <ul>
 *   <li>Não desenha nada e não tem SQL: só converte, valida o formato e delega.</li>
 *   <li>Erros sobem como exceção; a tela mostra a mensagem.</li>
 * </ul>
 *
 * 
 */
public class ClienteControle {
    // TODO: implementar conforme descrito acima.
}
