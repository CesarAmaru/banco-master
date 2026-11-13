
package br.com.controle;

import br.com.dao.ContaDAO;
import br.com.dao.ConexaoFactory;
import br.com.entidade.Conta;
import br.com.entidade.StatusConta;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ContaControle {

    private final ContaDAO contaDAO;

    public ContaControle() {
        this.contaDAO = new ContaDAO();
    }

    public void cadastrar(Conta conta) {
        validarConta(conta);

        if (contaDAO.existeNumero(conta.getNumero())) {
            throw new IllegalArgumentException(
                "Já existe uma conta com esse número."
            );
        }

        contaDAO.inserir(conta);
    }

    public List<Conta> listarPorCliente(int clienteId) {
        if (clienteId <= 0) {
            throw new IllegalArgumentException(
                "Informe um cliente válido."
            );
        }

        return contaDAO.listarPorCliente(clienteId);
    }

    public List<Conta> listarTodas() {
    return contaDAO.listarTodas();
}
    public Conta buscarPorNumero(String numero) {
        if (numero == null || numero.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "Informe o número da conta."
            );
        }

        try (Connection con = ConexaoFactory.getConnection()) {
            return contaDAO.buscarPorNumero(con, numero.trim());
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar conta.", e);
        }
    }

    public void atualizar(Conta conta) {
        validarContaExistente(conta);
        validarConta(conta);

        contaDAO.atualizar(conta);
    }

    public void bloquear(Conta conta) {
        validarContaExistente(conta);
        conta.bloquear();
        contaDAO.atualizarStatus(conta);
    }

    public void desbloquear(Conta conta) {
        validarContaExistente(conta);
        conta.desbloquear();
        contaDAO.atualizarStatus(conta);
    }

    public void encerrar(Conta conta) {
        validarContaExistente(conta);
        conta.encerrar();
        contaDAO.atualizarStatus(conta);
    }

    private void validarConta(Conta conta) {
        if (conta == null) {
            throw new IllegalArgumentException(
                "A conta não pode ser nula."
            );
        }

        if (conta.getNumero() == null ||
            conta.getNumero().trim().isEmpty()) {
            throw new IllegalArgumentException(
                "Informe o número da conta."
            );
        }

        if (conta.getNumero().trim().length() > 20) {
            throw new IllegalArgumentException(
                "O número da conta deve ter no máximo 20 caracteres."
            );
        }

        if (conta.getClienteId() <= 0) {
            throw new IllegalArgumentException(
                "Informe um cliente válido."
            );
        }

        if (conta.getAgenciaId() <= 0) {
            throw new IllegalArgumentException(
                "Informe uma agência válida."
            );
        }

        if (conta.getTipo() == null) {
            throw new IllegalArgumentException(
                "Informe o tipo da conta."
            );
        }

        if (conta.getSaldo() == null ||
            conta.getLimite() == null ||
            conta.getTaxaRendimento() == null) {
            throw new IllegalArgumentException(
                "Os valores financeiros não podem ser nulos."
            );
        }

        if (conta.getSaldo().signum() < 0) {
            throw new IllegalArgumentException(
                "O saldo inicial não pode ser negativo."
            );
        }

        if (conta.getLimite().signum() < 0 ||
            conta.getTaxaRendimento().signum() < 0) {
            throw new IllegalArgumentException(
                "Limite e taxa de rendimento não podem ser negativos."
            );
        }

        if (conta.getStatus() == null) {
            conta.setStatus(StatusConta.ATIVA);
        }
    }

    private void validarContaExistente(Conta conta) {
        if (conta == null || conta.getId() <= 0) {
            throw new IllegalArgumentException(
                "Informe uma conta válida."
            );
        }
    }
}
