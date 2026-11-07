/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.entidade;

import java.time.LocalDate;

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
    private Integer id;
    private String nome;
    private String cpf;
    private String senha_hash;
    private LocalDate data_nascimento;
    private Integer tentativas_login;
    private Boolean bloaqueado;
    
    public Cliente(){};

    public Cliente(Integer id, String nome, String cpf, String senha_hash, LocalDate data_nascimento, Integer tentativas_login, Boolean bloaqueado) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.senha_hash = senha_hash;
        this.data_nascimento = data_nascimento;
        this.tentativas_login = tentativas_login;
        this.bloaqueado = bloaqueado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getSenha_hash() {
        return senha_hash;
    }

    public void setSenha_hash(String senha_hash) {
        this.senha_hash = senha_hash;
    }

    public LocalDate getData_nascimento() {
        return data_nascimento;
    }

    public void setData_nascimento(LocalDate data_nascimento) {
        this.data_nascimento = data_nascimento;
    }

    public Integer getTentativas_login() {
        return tentativas_login;
    }

    public void setTentativas_login(Integer tentativas_login) {
        this.tentativas_login = tentativas_login;
    }

    public boolean getBloaqueado() {
        return bloaqueado;
    }

    public void setBloaqueado(boolean bloaqueado) {
        this.bloaqueado = bloaqueado;
    }
    
    
}
