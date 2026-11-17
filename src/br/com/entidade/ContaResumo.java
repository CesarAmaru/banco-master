package br.com.entidade;

import java.math.BigDecimal;

/**
 * Entidade de projeção (Model) que representa os dados consolidados de uma Conta
 * obtidos via INNER JOIN entre as tabelas conta, cliente e agencia.
 */
public class ContaResumo {

    private int id;
    private String numero;
    private String tipo;
    private String status;
    private BigDecimal saldo;
    private BigDecimal limite;
    private String titularNome;
    private String titularCpf;
    private String agenciaNumero;
    private String agenciaNome;

    public ContaResumo() {
    }

    public ContaResumo(int id, String numero, String titularNome, String tipo) {
        this.id = id;
        this.numero = numero;
        this.titularNome = titularNome;
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public BigDecimal getLimite() {
        return limite;
    }

    public void setLimite(BigDecimal limite) {
        this.limite = limite;
    }

    public String getTitularNome() {
        return titularNome;
    }

    public void setTitularNome(String titularNome) {
        this.titularNome = titularNome;
    }

    public String getTitularCpf() {
        return titularCpf;
    }

    public void setTitularCpf(String titularCpf) {
        this.titularCpf = titularCpf;
    }

    public String getAgenciaNumero() {
        return agenciaNumero;
    }

    public void setAgenciaNumero(String agenciaNumero) {
        this.agenciaNumero = agenciaNumero;
    }

    public String getAgenciaNome() {
        return agenciaNome;
    }

    public void setAgenciaNome(String agenciaNome) {
        this.agenciaNome = agenciaNome;
    }

    public String getAgenciaFormatada() {
        if (agenciaNumero == null) {
            return "—";
        }
        return agenciaNumero + " - " + (agenciaNome != null ? agenciaNome : "");
    }

    @Override
    public String toString() {
        if (id == 0) {
            return "Todas as contas";
        }
        return numero + " - " + titularNome + " (" + tipo + ")";
    }
}
