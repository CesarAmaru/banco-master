
package br.com.entidade;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ContaPoupanca extends Conta {

    public ContaPoupanca() {
        super();
        setTipo(TipoConta.POUPANCA);
    }

    @Override
    public void sacar(BigDecimal valor) {

        validarOperacao(valor);

        if (getSaldo().compareTo(valor) < 0) {
            throw new IllegalStateException(
                    "Saldo insuficiente para realizar o saque."
            );
        }

        setSaldo(getSaldo().subtract(valor));
    }

    public void aplicarRendimento() {

        if (!estaAtiva()) {
            throw new IllegalStateException(
                    "A conta precisa estar ativa para receber rendimento."
            );
        }

        BigDecimal rendimento = getSaldo()
                .multiply(getTaxaRendimento())
                .setScale(2, RoundingMode.HALF_UP);

        setSaldo(getSaldo().add(rendimento));
    }
}
