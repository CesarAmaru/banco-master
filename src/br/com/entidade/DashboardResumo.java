package br.com.entidade;

import java.math.BigDecimal;

/**
 * Entidade de projeção (Model) para os indicadores consolidados
 * exibidos nos cards do Dashboard principal.
 */
public class DashboardResumo {

    private int totalClientes;
    private int totalAgencias;
    private int contasAtivas;
    private BigDecimal saldoTotalCustodia = BigDecimal.ZERO;
    private int totalTransacoes;
    private BigDecimal volumeMovimentado = BigDecimal.ZERO;

    public int getTotalClientes() {
        return totalClientes;
    }

    public void setTotalClientes(int totalClientes) {
        this.totalClientes = totalClientes;
    }

    public int getTotalAgencias() {
        return totalAgencias;
    }

    public void setTotalAgencias(int totalAgencias) {
        this.totalAgencias = totalAgencias;
    }

    public int getContasAtivas() {
        return contasAtivas;
    }

    public void setContasAtivas(int contasAtivas) {
        this.contasAtivas = contasAtivas;
    }

    public BigDecimal getSaldoTotalCustodia() {
        return saldoTotalCustodia;
    }

    public void setSaldoTotalCustodia(BigDecimal saldoTotalCustodia) {
        this.saldoTotalCustodia = saldoTotalCustodia != null ? saldoTotalCustodia : BigDecimal.ZERO;
    }

    public int getTotalTransacoes() {
        return totalTransacoes;
    }

    public void setTotalTransacoes(int totalTransacoes) {
        this.totalTransacoes = totalTransacoes;
    }

    public BigDecimal getVolumeMovimentado() {
        return volumeMovimentado;
    }

    public void setVolumeMovimentado(BigDecimal volumeMovimentado) {
        this.volumeMovimentado = volumeMovimentado != null ? volumeMovimentado : BigDecimal.ZERO;
    }
}
