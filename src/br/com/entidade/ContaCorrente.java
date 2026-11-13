
package br.com.entidade;

import java.math.BigDecimal;

public class ContaCorrente extends Conta {

    public ContaCorrente() {
        super();
        setTipo(TipoConta.CORRENTE);
    }

    @Override
    public void sacar(BigDecimal valor) {

        validarOperacao(valor);

        BigDecimal saldoDisponivel = getSaldo().add(getLimite());

        if (saldoDisponivel.compareTo(valor) < 0) {
            throw new IllegalStateException(
                    "Saldo insuficiente para realizar o saque."
            );
        }

        setSaldo(getSaldo().subtract(valor));
    }
}
