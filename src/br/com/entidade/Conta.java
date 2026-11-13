
package br.com.entidade;

import java.math.BigDecimal;
import java.time.LocalDate;

public abstract class Conta {

    private int id;
    private String numero;
    private int agenciaId;
    private int clienteId;
    private Integer funcionarioId;
    private BigDecimal saldo;
    private BigDecimal limite;
    private BigDecimal taxaRendimento;
    private StatusConta status;
    private TipoConta tipo;
    private LocalDate dataAbertura;

    public Conta() {
        this.saldo = BigDecimal.ZERO;
        this.limite = BigDecimal.ZERO;
        this.taxaRendimento = BigDecimal.ZERO;
        this.status = StatusConta.ATIVA;
        this.dataAbertura = LocalDate.now();
    }

    public void depositar(BigDecimal valor) {

        validarOperacao(valor);

        saldo = saldo.add(valor);
    }

    public abstract void sacar(BigDecimal valor);

    public void bloquear() {
        if (status == StatusConta.ENCERRADA) {
            throw new IllegalStateException(
                    "Não é possível bloquear uma conta encerrada."
            );
        }

        status = StatusConta.BLOQUEADA;
    }

    public void desbloquear() {
        if (status == StatusConta.ENCERRADA) {
            throw new IllegalStateException(
                    "Não é possível desbloquear uma conta encerrada."
            );
        }

        status = StatusConta.ATIVA;
    }

    public void encerrar() {
        if (saldo.compareTo(BigDecimal.ZERO) != 0) {
            throw new IllegalStateException(
                    "A conta precisa estar com saldo zero para ser encerrada."
            );
        }

        status = StatusConta.ENCERRADA;
    }

    public boolean estaAtiva() {
        return status == StatusConta.ATIVA;
    }

    protected void validarOperacao(BigDecimal valor) {

        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "O valor deve ser maior que zero."
            );
        }

        if (!estaAtiva()) {
            throw new IllegalStateException(
                    "A conta não está ativa."
            );
        }
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

    public int getAgenciaId() {
        return agenciaId;
    }

    public void setAgenciaId(int agenciaId) {
        this.agenciaId = agenciaId;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public Integer getFuncionarioId() {
        return funcionarioId;
    }

    public void setFuncionarioId(Integer funcionarioId) {
        this.funcionarioId = funcionarioId;
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

    public BigDecimal getTaxaRendimento() {
        return taxaRendimento;
    }

    public void setTaxaRendimento(BigDecimal taxaRendimento) {
        this.taxaRendimento = taxaRendimento;
    }

    public StatusConta getStatus() {
        return status;
    }

    public void setStatus(StatusConta status) {
        this.status = status;
    }

    public TipoConta getTipo() {
        return tipo;
    }

    public void setTipo(TipoConta tipo) {
        this.tipo = tipo;
    }

    public LocalDate getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(LocalDate dataAbertura) {
        this.dataAbertura = dataAbertura;
    }
}
