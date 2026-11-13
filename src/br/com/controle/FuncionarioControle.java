package br.com.controle;

import br.com.dao.FuncionarioDAO;
import br.com.entidade.Funcionario;

import java.util.List;

public class FuncionarioControle {

    private final FuncionarioDAO funcionarioDAO;

    public FuncionarioControle() {
        this.funcionarioDAO = new FuncionarioDAO();
    }

    // CADASTRAR FUNCIONARIO
    public void cadastrar(Funcionario funcionario) {

        validarFuncionario(funcionario);

        funcionarioDAO.inserir(funcionario);
    }

    // LISTAR FUNCIONARIOS
    public List<Funcionario> listarTodos() {

        return funcionarioDAO.listarTodos();
    }

    // BUSCAR FUNCIONARIO PELO ID
    public Funcionario buscarPorId(int id) {

        if (id <= 0) {
            throw new IllegalArgumentException(
                    "Informe um ID válido.");
        }

        return funcionarioDAO.buscarPorId(id);
    }

    // PESQUISAR FUNCIONARIO
    public List<Funcionario> pesquisar(String termo) {

        if (termo == null || termo.trim().isEmpty()) {
            return listarTodos();
        }

        return funcionarioDAO.pesquisar(termo.trim());
    }

    // ATUALIZAR FUNCIONARIO
    public void atualizar(Funcionario funcionario) {

        if (funcionario == null || funcionario.getId() <= 0) {
            throw new IllegalArgumentException(
                    "Funcionário inválido.");
        }

        validarFuncionario(funcionario);

        funcionarioDAO.atualizar(funcionario);
    }

    // EXCLUIR FUNCIONARIO
    public void excluir(int id) {

        if (id <= 0) {
            throw new IllegalArgumentException(
                    "Informe um ID válido.");
        }

        funcionarioDAO.excluir(id);
    }

    // VALIDAR DADOS
    private void validarFuncionario(Funcionario funcionario) {

        if (funcionario == null) {
            throw new IllegalArgumentException(
                    "Funcionário não informado.");
        }

        if (funcionario.getNome() == null
                || funcionario.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Informe o nome do funcionário.");
        }

        if (funcionario.getCpf() == null
                || funcionario.getCpf().trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Informe o CPF do funcionário.");
        }

        if (funcionario.getCargo() == null
                || funcionario.getCargo().trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Informe o cargo do funcionário.");
        }

        if (funcionario.getDataAdmissao() == null) {
            throw new IllegalArgumentException(
                    "Informe a data de admissão.");
        }

        if (funcionario.getAgenciaId() <= 0) {
            throw new IllegalArgumentException(
                    "Informe uma agência válida.");
        }
    }
}
