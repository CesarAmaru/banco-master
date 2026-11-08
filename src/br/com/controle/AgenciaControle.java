/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.controle;
import br.com.dao.AgenciaDAO;
import br.com.entidade.Agencia;
import java.util.List;

public class AgenciaControle {
    private final AgenciaDAO agenciaDao;
    public AgenciaControle(){
        agenciaDao = new AgenciaDAO();
    }
    
    private void validar(String nome, String numero, String cidade, String uf){
        if(nome == null || nome.isBlank()){
            throw new IllegalArgumentException("Nome é obrigatório.");
        }
        if(numero == null || numero.isBlank()){
            throw new IllegalArgumentException("Número é obrigatório.");
        }
        if(cidade == null || cidade.isBlank()){
            throw new IllegalArgumentException("Cidade é obrigatório.");
        }
        if(uf == null || uf.isBlank()){
            throw new IllegalArgumentException("UF é obrigatório.");
        }
    }
    
    public void cadastrar(String nome, String numero, String cidade, String uf){
        validar(nome, numero, cidade, uf);
        Agencia aux = new Agencia(null, nome, numero, cidade, uf);
        agenciaDao.inserir(aux);
    }
    public void atualizar(Integer id, String nome, String numero, String cidade, String uf){
        if(id == null){
            throw new IllegalArgumentException("Id é obrigatório.");
        }
        validar(nome, numero, cidade, uf);
        Agencia aux = new Agencia(id, nome, numero, cidade, uf);
        agenciaDao.atualizar(aux);
    }
    
    public void excluir(Agencia a){
        if(a == null || a.getId() == null){
            throw new IllegalArgumentException("Nome é obrigatório.");
        }
        agenciaDao.excluir(a);
    }
    
    public List<Agencia> listarTodos(){
        return agenciaDao.listar();
    }
}
