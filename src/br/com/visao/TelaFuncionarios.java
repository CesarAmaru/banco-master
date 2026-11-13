
package br.com.visao;

import br.com.entidade.Funcionario;
import br.com.controle.FuncionarioControle;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class TelaFuncionarios extends JFrame {

    private final FuncionarioControle controle = new FuncionarioControle();

    private JTextField txtId;
    private JTextField txtNome;
    private JTextField txtCpf;
    private JTextField txtCargo;
    private JTextField txtData;
    private JTextField txtAgencia;
    private JTextField txtPesquisa;

    private JTable tabela;
    private DefaultTableModel modelo;

    public TelaFuncionarios() {
        setTitle("Banco Master - Gerenciamento de Funcionários");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        criarTela();
        listarFuncionarios();
    }

    private void criarTela() {
        
        JLabel titulo = new JLabel(
            "GERENCIAMENTO DE FUNCIONÁRIOS",
            SwingConstants.CENTER
    );
    titulo.setFont(new Font("Arial", Font.BOLD, 20));

    // Formulário
    JPanel formulario = new JPanel(new GridLayout(6, 2, 8, 8));
    formulario.setBorder(
            BorderFactory.createTitledBorder("Dados do funcionário")
    );

    txtId = new JTextField();
    txtId.setEditable(false);

    txtNome = new JTextField();
    txtCpf = new JTextField();
    txtCargo = new JTextField();
    txtData = new JTextField();
    txtAgencia = new JTextField();

    formulario.add(new JLabel("ID:"));
    formulario.add(txtId);

    formulario.add(new JLabel("Nome:"));
    formulario.add(txtNome);

    formulario.add(new JLabel("CPF:"));
    formulario.add(txtCpf);

    formulario.add(new JLabel("Cargo:"));
    formulario.add(txtCargo);

    formulario.add(new JLabel("Data de admissão (AAAA-MM-DD):"));
    formulario.add(txtData);

    formulario.add(new JLabel("ID da agência:"));
    formulario.add(txtAgencia);

    // Botões
    JButton btnCadastrar = new JButton("Cadastrar");
    JButton btnAtualizar = new JButton("Atualizar");
    JButton btnExcluir = new JButton("Excluir");
    JButton btnLimpar = new JButton("Limpar");

    JPanel botoes = new JPanel(new FlowLayout());
    botoes.add(btnCadastrar);
    botoes.add(btnAtualizar);
    botoes.add(btnExcluir);
    botoes.add(btnLimpar);

    // Pesquisa
    JPanel painelPesquisa = new JPanel(
            new FlowLayout(FlowLayout.LEFT)
    );

    txtPesquisa = new JTextField(25);

    JButton btnPesquisar = new JButton("Pesquisar");
    JButton btnListar = new JButton("Listar todos");

    painelPesquisa.add(new JLabel("Pesquisar por nome ou CPF:"));
    painelPesquisa.add(txtPesquisa);
    painelPesquisa.add(btnPesquisar);
    painelPesquisa.add(btnListar);

    // Tabela
    modelo = new DefaultTableModel(
            new Object[]{"ID", "Nome", "CPF", "Cargo", "Admissão", "Agência"}, 0
    ) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    tabela = new JTable(modelo);
    JScrollPane rolagem = new JScrollPane(tabela);

    // Organização da tela
    JPanel painelSuperior = new JPanel();
    painelSuperior.setLayout(new BoxLayout(
            painelSuperior, BoxLayout.Y_AXIS
    ));

    painelSuperior.add(formulario);
    painelSuperior.add(botoes);
    painelSuperior.add(painelPesquisa);

    JPanel topo = new JPanel(new BorderLayout(5, 5));
    topo.add(titulo, BorderLayout.NORTH);
    topo.add(painelSuperior, BorderLayout.CENTER);

    setLayout(new BorderLayout(10, 10));

    add(topo, BorderLayout.NORTH);
    add(rolagem, BorderLayout.CENTER);

    // Ações dos botões
    btnCadastrar.addActionListener(e -> cadastrar());
    btnAtualizar.addActionListener(e -> atualizar());
    btnExcluir.addActionListener(e -> excluir());
    btnLimpar.addActionListener(e -> limparCampos());
    btnPesquisar.addActionListener(e -> pesquisar());
    btnListar.addActionListener(e -> listarFuncionarios());

    // Seleção de funcionário na tabela
    tabela.getSelectionModel().addListSelectionListener(e -> {
        if (!e.getValueIsAdjusting() && tabela.getSelectedRow() != -1) {
            preencherCampos();
        }
    });
}
    

    private Funcionario obterDadosFormulario() {

        String nome = txtNome.getText().trim();
        String cpf = txtCpf.getText().trim();
        String cargo = txtCargo.getText().trim();
        LocalDate data = LocalDate.parse(txtData.getText().trim());
        int agenciaId = Integer.parseInt(txtAgencia.getText().trim());

        int id = txtId.getText().isEmpty()
                ? 0 : Integer.parseInt(txtId.getText());

        return new Funcionario(id, nome, cpf, cargo, data, agenciaId);
    }

    private void cadastrar() {
        try {
            Funcionario funcionario = obterDadosFormulario();
            controle.cadastrar(funcionario);

            JOptionPane.showMessageDialog(this,
                    "Funcionário cadastrado com sucesso!");

            limparCampos();
            listarFuncionarios();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao cadastrar: " + ex.getMessage());
        }
    }

    private void atualizar() {
        try {
            if (txtId.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Selecione um funcionário na tabela.");
                return;
            }

            Funcionario funcionario = obterDadosFormulario();
            controle.atualizar(funcionario);

            JOptionPane.showMessageDialog(this,
                    "Funcionário atualizado com sucesso!");

            limparCampos();
            listarFuncionarios();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao atualizar: " + ex.getMessage());
        }
    }

    private void excluir() {
        try {
            if (txtId.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Selecione um funcionário na tabela.");
                return;
            }

            int resposta = JOptionPane.showConfirmDialog(
                    this,
                    "Deseja realmente excluir este funcionário?",
                    "Confirmar exclusão",
                    JOptionPane.YES_NO_OPTION
            );

            if (resposta == JOptionPane.YES_OPTION) {
                controle.excluir(Integer.parseInt(txtId.getText()));

                JOptionPane.showMessageDialog(this,
                        "Funcionário excluído com sucesso!");

                limparCampos();
                listarFuncionarios();
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao excluir: " + ex.getMessage());
        }
    }

    private void listarFuncionarios() {
        try {
            modelo.setRowCount(0);

            List<Funcionario> funcionarios = controle.listarTodos();

            for (Funcionario f : funcionarios) {
                modelo.addRow(new Object[]{
                    f.getId(),
                    f.getNome(),
                    f.getCpf(),
                    f.getCargo(),
                    f.getDataAdmissao(),
                    f.getAgenciaId()
                });
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao listar funcionários: " + ex.getMessage());
        }
    }

    private void pesquisar() {
        try {
            modelo.setRowCount(0);

            List<Funcionario> funcionarios =
                    controle.pesquisar(txtPesquisa.getText().trim());

            for (Funcionario f : funcionarios) {
                modelo.addRow(new Object[]{
                    f.getId(),
                    f.getNome(),
                    f.getCpf(),
                    f.getCargo(),
                    f.getDataAdmissao(),
                    f.getAgenciaId()
                });
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Erro na pesquisa: " + ex.getMessage());
        }
    }

    private void preencherCampos() {
        int linha = tabela.getSelectedRow();

        txtId.setText(modelo.getValueAt(linha, 0).toString());
        txtNome.setText(modelo.getValueAt(linha, 1).toString());
        txtCpf.setText(modelo.getValueAt(linha, 2).toString());
        txtCargo.setText(modelo.getValueAt(linha, 3).toString());
        txtData.setText(modelo.getValueAt(linha, 4).toString());
        txtAgencia.setText(modelo.getValueAt(linha, 5).toString());
    }

    private void limparCampos() {
        txtId.setText("");
        txtNome.setText("");
        txtCpf.setText("");
        txtCargo.setText("");
        txtData.setText("");
        txtAgencia.setText("");
        tabela.clearSelection();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
                new TelaFuncionarios().setVisible(true)
        );
    }
}
