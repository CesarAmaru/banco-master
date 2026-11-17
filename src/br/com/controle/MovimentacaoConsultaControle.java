package br.com.controle;

import br.com.dao.MovimentacaoConsultaDAO;
import java.util.List;

/**
 * Ponte entre as telas de movimentação e o MovimentacaoConsultaDAO.
 * Responsável: Integrante 5
 */
public class MovimentacaoConsultaControle {

    private final MovimentacaoConsultaDAO dao = new MovimentacaoConsultaDAO();

    /** Contas para preencher os combos. */
    public List<String> listarContas() throws Exception {
        return dao.listarContas();
    }

    /** numeroConta null = todas as contas. */
    public List<Object[]> listarMovimentacoes(String numeroConta) throws Exception {
        return dao.listarMovimentacoes(numeroConta);
    }

    /** Pega só o número da conta de um item do combo ("100001-0 - Ana ..." vira "100001-0"). */
    public String extrairNumero(Object itemCombo) {
        if (itemCombo == null) {
            return null;
        }
        return itemCombo.toString().split(" - ")[0];
    }
}
