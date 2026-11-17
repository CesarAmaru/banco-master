package br.com.entidade;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entidade de projeção (Model) que representa uma movimentação bancária (transacao)
 * enriquecida com os dados legíveis das contas e titulares de origem e destino.
 */
public class MovimentacaoRelatorio {

    private int id;
    private String tipo;
    private BigDecimal valor;
    private LocalDateTime dataHora;
    private Integer contaOrigemId;
    private String contaOrigemNumero;
    private String titularOrigemNome;
    private Integer contaDestinoId;
    private String contaDestinoNumero;
    private String titularDestinoNome;

    public MovimentacaoRelatorio() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
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

    public String getContaOrigemNumero() {
        return contaOrigemNumero;
    }

    public void setContaOrigemNumero(String contaOrigemNumero) {
        this.contaOrigemNumero = contaOrigemNumero;
    }

    public String getTitularOrigemNome() {
        return titularOrigemNome;
    }

    public void setTitularOrigemNome(String titularOrigemNome) {
        this.titularOrigemNome = titularOrigemNome;
    }

    public Integer getContaDestinoId() {
        return contaDestinoId;
    }

    public void setContaDestinoId(Integer contaDestinoId) {
        this.contaDestinoId = contaDestinoId;
    }

    public String getContaDestinoNumero() {
        return contaDestinoNumero;
    }

    public void setContaDestinoNumero(String contaDestinoNumero) {
        this.contaDestinoNumero = contaDestinoNumero;
    }

    public String getTitularDestinoNome() {
        return titularDestinoNome;
    }

    public void setTitularDestinoNome(String titularDestinoNome) {
        this.titularDestinoNome = titularDestinoNome;
    }

    public String getOrigemFormatada() {
        if (contaOrigemNumero == null) {
            return "—";
        }
        return contaOrigemNumero + " (" + titularOrigemNome + ")";
    }

    public String getDestinoFormatada() {
        if (contaDestinoNumero == null) {
            return "—";
        }
        return contaDestinoNumero + " (" + titularDestinoNome + ")";
    }
}
