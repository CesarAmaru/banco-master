/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.controle;

import br.com.dao.ClienteDAO;
import br.com.entidade.Cliente;
import java.time.LocalDate;
import java.util.List;

/**
 * Ponte entre {@code TelaCadastroCliente} e o cadastro de clientes.
 *
 * <b>Camada:</b> Controle (Controller)
 * <p>
 * <b>O que esta classe deve fazer:</b>
 * <ul>
 *   <li>{@code cadastrar(String nome, String cpf, String dataNascimento, String senha)}: converte os textos da tela, valida (campos obrigatórios, CPF via {@code CpfValidar}, maior de 18, senha >= 6 e igual à confirmação), gera o hash com {@code SenhaUtil}, confere CPF duplicado e chama {@code ClienteDAO.inserir}.</li>
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
    private final ClienteDAO clienteDAO;
    public ClienteControle(){
        this.clienteDAO = new ClienteDAO();
    }
    
    private void validar(String nome, String cpf, String senha_hash, LocalDate data_nascimento){
            if(nome == null || nome.trim().isEmpty()){
                throw new IllegalArgumentException("Nome é obrigatório.");
            }
            if(cpf == null || cpf.trim().isEmpty()){
                throw new IllegalArgumentException("CPF é obrigatório.");
            }
            if(senha_hash == null || senha_hash.trim().isEmpty()){
                throw new IllegalArgumentException("A senha  é obrigatória.");
            }
            if(data_nascimento == null){
                throw new IllegalArgumentException("A data de nascimento  é obrigatória.");
            }
        }
    
    public void cadastrar(String nome, String cpf, String senha_hash, LocalDate data_nascimento){
        validar(nome, cpf, senha_hash, data_nascimento);
        
        Cliente aux = new Cliente(null, nome, cpf, senha_hash, data_nascimento, null, null);
        clienteDAO.inserir(aux);
    }
    
    public void atualizar(Integer id,String nome, String cpf, String senha_hash, LocalDate data_nascimento, Boolean bloqueado){
        if(id == null){
            throw new IllegalArgumentException("Id é obrigatório.");
        }
        if(bloqueado == null){
            throw new IllegalArgumentException("status bloqueado é obrigatório.");
        }
        
        Cliente aux = new Cliente(id, nome, cpf, senha_hash, data_nascimento, null, bloqueado);
        clienteDAO.atualizar(aux);
        
    }
    
    public void excluir(Cliente c){
        if(c == null || c.getId() == null){
            throw new IllegalArgumentException("Cliente inválido para exclusão");
        }
        clienteDAO.excluir(c);
    }
    public List<Cliente> listarTodos(){
        return clienteDAO.listar();
    }
    
}
