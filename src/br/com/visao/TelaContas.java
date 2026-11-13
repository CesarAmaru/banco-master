
package br.com.visao;

import br.com.controle.ContaControle;
import br.com.entidade.Conta;
import br.com.entidade.ContaCorrente;
import br.com.entidade.ContaPoupanca;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.util.List;

public class TelaContas extends JFrame {

    private final ContaControle controle = new ContaControle();

    private JTextField txtId;
    private JTextField txtNumero;
    private JTextField txtCliente;
    private JTextField txtAgencia;
    private JTextField txtFuncionario;
    private JTextField txtLimite;
    private JTextField txtTaxa;

    private JComboBox<String> cbTipo;
    private JTable tabela;
    private DefaultTableModel modelo;

    public TelaContas() {

        setTitle("Banco Master - Gerenciamento de Contas");
        setSize(950, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        );

        JLabel titulo = new JLabel(
            "GERENCIAMENTO DE CONTAS",
            SwingConstants.CENTER
        );

        titulo.setFont(new Font("Arial", Font.BOLD, 22));

        painelPrincipal.add(titulo, BorderLayout.NORTH);

        JPanel painelCampos = new JPanel(new GridLayout(4, 4, 8, 8));

        txtId = new JTextField();
        txtId.setEditable(false);

        txtNumero = new JTextField();
        txtCliente = new JTextField();
        txtAgencia = new JTextField();
        txtFuncionario = new JTextField();
        txtLimite = new JTextField("0.00");
        txtTaxa = new JTextField("0.0000");

        cbTipo = new JComboBox<>(
            new String[]{"CORRENTE", "POUPANCA"}
        );

        painelCampos.add(new JLabel("ID:"));
        painelCampos.add(txtId);

        painelCampos.add(new JLabel("Número:"));
        painelCampos.add(txtNumero);

        painelCampos.add(new JLabel("ID do Cliente:"));
        painelCampos.add(txtCliente);

        painelCampos.add(new JLabel("ID da Agência:"));
        painelCampos.add(txtAgencia);

        painelCampos.add(new JLabel("ID do Funcionário (opcional):"));
        painelCampos.add(txtFuncionario);

        painelCampos.add(new JLabel("Tipo da Conta:"));
        painelCampos.add(cbTipo);

        painelCampos.add(new JLabel("Limite:"));
        painelCampos.add(txtLimite);

        painelCampos.add(new JLabel("Taxa de Rendimento:"));
        painelCampos.add(txtTaxa);

        JPanel painelSuperior = new JPanel(new BorderLayout(10, 10));
        painelSuperior.add(titulo, BorderLayout.NORTH);
        painelSuperior.add(painelCampos, BorderLayout.CENTER);

        modelo = new DefaultTableModel(
            new String[]{
                "ID", "Número", "Cliente", "Agência",
                "Funcionário", "Tipo", "Saldo", "Status"
            }, 0
        ) {
            @Override
            public boolean isCellEditable(int linha, int coluna) {
                return false;
            }
        };

        tabela = new JTable(modelo);
        tabela.setSelectionMode(
            ListSelectionModel.SINGLE_SELECTION
        );

        tabela.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                preencherCampos();
            }
        });

        JScrollPane rolagem = new JScrollPane(tabela);

        JPanel painelBotoes = new JPanel(
            new FlowLayout(FlowLayout.CENTER, 8, 8)
        );

        JButton btnCadastrar = new JButton("Cadastrar");
        JButton btnAtualizar = new JButton("Atualizar");
        JButton btnPesquisar = new JButton("Pesquisar");
        JButton btnListar = new JButton("Listar Todas");
        JButton btnBloquear = new JButton("Bloquear");
        JButton btnDesbloquear = new JButton("Desbloquear");
        JButton btnEncerrar = new JButton("Encerrar");
        JButton btnLimpar = new JButton("Limpar");

        painelBotoes.add(btnCadastrar);
        painelBotoes.add(btnAtualizar);
        painelBotoes.add(btnPesquisar);
        painelBotoes.add(btnListar);
        painelBotoes.add(btnBloquear);
        painelBotoes.add(btnDesbloquear);
        painelBotoes.add(btnEncerrar);
        painelBotoes.add(btnLimpar);

        painelPrincipal.add(painelSuperior, BorderLayout.NORTH);
        painelPrincipal.add(rolagem, BorderLayout.CENTER);
        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);

        add(painelPrincipal);

        btnCadastrar.addActionListener(e -> cadastrar());
        btnAtualizar.addActionListener(e -> atualizar());
        btnPesquisar.addActionListener(e -> pesquisar());
        btnListar.addActionListener(e -> listar());
        btnBloquear.addActionListener(e -> alterarStatus("BLOQUEAR"));
        btnDesbloquear.addActionListener(e -> alterarStatus("DESBLOQUEAR"));
        btnEncerrar.addActionListener(e -> alterarStatus("ENCERRAR"));
        btnLimpar.addActionListener(e -> limpar());

        listar();
    }

    private void cadastrar() {
        try {
            Conta conta;

            if (cbTipo.getSelectedItem().equals("CORRENTE")) {
                conta = new ContaCorrente();
            } else {
                conta = new ContaPoupanca();
            }

            conta.setNumero(txtNumero.getText().trim());
            conta.setClienteId(Integer.parseInt(txtCliente.getText().trim()));
            conta.setAgenciaId(Integer.parseInt(txtAgencia.getText().trim()));

            String funcionario = txtFuncionario.getText().trim();

            if (funcionario.isEmpty()) {
                conta.setFuncionarioId(null);
            } else {
                conta.setFuncionarioId(Integer.parseInt(funcionario));
            }

            conta.setLimite(new BigDecimal(
                txtLimite.getText().trim().replace(",", ".")
            ));

            conta.setTaxaRendimento(new BigDecimal(
                txtTaxa.getText().trim().replace(",", ".")
            ));

            controle.cadastrar(conta);

            JOptionPane.showMessageDialog(
                this, "Conta cadastrada com sucesso!"
            );

            limpar();
            listar();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                this, e.getMessage(),
                "Erro ao cadastrar", JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void atualizar() {
        try {
            Conta conta = obterContaSelecionada();

            if (conta == null) {
                return;
            }

            conta.setAgenciaId(
                Integer.parseInt(txtAgencia.getText().trim())
            );

            String funcionario = txtFuncionario.getText().trim();

            if (funcionario.isEmpty()) {
                conta.setFuncionarioId(null);
            } else {
                conta.setFuncionarioId(Integer.parseInt(funcionario));
            }

            conta.setLimite(new BigDecimal(
                txtLimite.getText().trim().replace(",", ".")
            ));

            conta.setTaxaRendimento(new BigDecimal(
                txtTaxa.getText().trim().replace(",", ".")
            ));

            controle.atualizar(conta);

            JOptionPane.showMessageDialog(
                this, "Conta atualizada com sucesso!"
            );

            listar();
            limpar();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                this, e.getMessage(),
                "Erro ao atualizar", JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void pesquisar() {
        try {
            String numero = txtNumero.getText().trim();

            Conta conta = controle.buscarPorNumero(numero);

            if (conta == null) {
                JOptionPane.showMessageDialog(
                    this, "Conta não encontrada."
                );
                return;
            }

            preencherCampos(conta);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                this, e.getMessage(),
                "Erro na pesquisa", JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void listar() {
        try {
            modelo.setRowCount(0);

            List<Conta> contas = controle.listarTodas();

            for (Conta conta : contas) {
                modelo.addRow(new Object[]{
                    conta.getId(),
                    conta.getNumero(),
                    conta.getClienteId(),
                    conta.getAgenciaId(),
                    conta.getFuncionarioId(),
                    conta.getTipo(),
                    conta.getSaldo(),
                    conta.getStatus()
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                this, e.getMessage(),
                "Erro ao listar contas", JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void preencherCampos() {
        int linha = tabela.getSelectedRow();

        if (linha < 0) {
            return;
        }

        String numero = modelo.getValueAt(linha, 1).toString();

        Conta conta = controle.buscarPorNumero(numero);

        if (conta != null) {
            preencherCampos(conta);
        }
    }

    private void preencherCampos(Conta conta) {
        txtId.setText(String.valueOf(conta.getId()));
        txtNumero.setText(conta.getNumero());
        txtCliente.setText(String.valueOf(conta.getClienteId()));
        txtAgencia.setText(String.valueOf(conta.getAgenciaId()));

        txtFuncionario.setText(
            conta.getFuncionarioId() == null
                ? ""
                : String.valueOf(conta.getFuncionarioId())
        );

        txtLimite.setText(conta.getLimite().toString());
        txtTaxa.setText(conta.getTaxaRendimento().toString());

        cbTipo.setSelectedItem(conta.getTipo().name());
    }

    private Conta obterContaSelecionada() {
        String numero = txtNumero.getText().trim();

        if (numero.isEmpty()) {
            JOptionPane.showMessageDialog(
                this, "Selecione uma conta ou informe o número."
            );
            return null;
        }

        return controle.buscarPorNumero(numero);
    }

    private void alterarStatus(String operacao) {
        try {
            Conta conta = obterContaSelecionada();

            if (conta == null) {
                return;
            }

            if (operacao.equals("BLOQUEAR")) {
                controle.bloquear(conta);
            } else if (operacao.equals("DESBLOQUEAR")) {
                controle.desbloquear(conta);
            } else {
                int resposta = JOptionPane.showConfirmDialog(
                    this,
                    "Deseja realmente encerrar esta conta?",
                    "Confirmar encerramento",
                    JOptionPane.YES_NO_OPTION
                );

                if (resposta != JOptionPane.YES_OPTION) {
                    return;
                }

                controle.encerrar(conta);
            }

            JOptionPane.showMessageDialog(
                this, "Status da conta atualizado!"
            );

            listar();
            limpar();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                this, e.getMessage(),
                "Erro ao alterar status", JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void limpar() {
        txtId.setText("");
        txtNumero.setText("");
        txtCliente.setText("");
        txtAgencia.setText("");
        txtFuncionario.setText("");
        txtLimite.setText("0.00");
        txtTaxa.setText("0.0000");
        cbTipo.setSelectedIndex(0);
        tabela.clearSelection();
    }
}
