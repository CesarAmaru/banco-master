/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.entidade;

import java.time.LocalDateTime;
import java.math.BigDecimal;

/**
 * Representa UMA movimentação registrada (depósito, saque ou transferência).
 *
 * <b>Camada:</b> Entidade (Model)
 * <br><b>Responsável:</b> Integrante 4
 * <p>
 * <b>O que esta classe deve fazer:</b>
 * <ul>
 *   <li>Atributos privados: {@code id}, {@code tipo} ({@code TipoTransacao}), {@code valor} ({@code BigDecimal}), {@code dataHora} ({@code LocalDateTime}), {@code contaOrigem} ({@code Conta}), {@code contaDestino} ({@code Conta}).</li>
 *   <li>Construtor vazio (o DAO usa para montar o objeto vindo do banco).</li>
 *   <li>Construtor completo {@code (tipo, valor, origem, destino)} que preenche {@code dataHora} com {@code LocalDateTime.now()}.</li>
 *   <li>Getters e setters.</li>
 * </ul>
 * <b>Atenção:</b>
 * <ul>
 *   <li>DEPÓSITO: {@code contaOrigem} fica {@code null}. SAQUE: {@code contaDestino} fica {@code null}.</li>
 *   <li>Sem regra de negócio aqui, só dados. Uma transação registrada nunca é editada nem apagada.</li>
 * </ul>
 *
 * 
 */
public class Transacao {
    // TODO: implementar conforme descrito acima.
    
    private Integer id;
    private TipoTransacao tipo;
    private BigDecimal valor;
    private LocalDateTime dataHora;
    private Integer contaOrigemId;
    private Integer contaDestinoId;
    
    //Consulta de extrato (JOIN)
    private String numeroContaOrigem;
    private String nomeClienteOrigem;
    private String numeroContaDestino;
    private String nomeClienteDestino;
    
    //Utilizado no DAO.
    public Transacao(){
    }
    
    public Transacao(TipoTransacao tipo, BigDecimal valor, Integer contaOrigemId, Integer contaDestinoId){
        this.tipo = tipo;
        this.valor = valor;
        this.contaOrigemId = contaOrigemId;
        this.contaDestinoId = contaDestinoId;
        this.dataHora = LocalDateTime.now();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TipoTransacao getTipo() {
        return tipo;
    }

    public void setTipo(TipoTransacao tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public Integer getContaOrigemId() {
        return contaOrigemId;
    }

    public void setContaOrigemId(Integer contaOrigemId) {
        this.contaOrigemId = contaOrigemId;
    }

    public Integer getContaDestinoId() {
        return contaDestinoId;
    }

    public void setContaDestinoId(Integer contaDestinoId) {
        this.contaDestinoId = contaDestinoId;
    }

    public String getNumeroContaOrigem() {
        return numeroContaOrigem;
    }

    public void setNumeroContaOrigem(String numeroContaOrigem) {
        this.numeroContaOrigem = numeroContaOrigem;
    }

    public String getNomeClienteOrigem() {
        return nomeClienteOrigem;
    }

    public void setNomeClienteOrigem(String nomeClienteOrigem) {
        this.nomeClienteOrigem = nomeClienteOrigem;
    }

    public String getNumeroContaDestino() {
        return numeroContaDestino;
    }

    public void setNumeroContaDestino(String numeroContaDestino) {
        this.numeroContaDestino = numeroContaDestino;
    }

    public String getNomeClienteDestino() {
        return nomeClienteDestino;
    }

    public void setNomeClienteDestino(String nomeClienteDestino) {
        this.nomeClienteDestino = nomeClienteDestino;
    }
    
}
