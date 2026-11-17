package br.com.visao;

import br.com.controle.RelatorioControle;
import br.com.entidade.ContaResumo;
import br.com.entidade.DashboardResumo;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

/**
 * Tela principal (Dashboard Executivo e Hub de Integração do Sistema Banco Master).
 * Conecta diretamente às telas já existentes em br.com.visao e exibe os indicadores
 * consolidados e a posição das contas via INNER JOIN.
 */
public class TelaDashboard extends JFrame {

    private static final Color COR_PRIMARIA = new Color(15, 41, 66);
    private static final Color COR_SECUNDARIA = new Color(30, 58, 95);
    private static final Color COR_FUNDO = new Color(244, 246, 249);
    private static final Color COR_VERDE = new Color(21, 128, 61);

    private final RelatorioControle controle;

    private JLabel lblStatusBanco;
    private JLabel lblValClientes;
    private JLabel lblValAgencias;
    private JLabel lblValContasAtivas;
    private JLabel lblValSaldoCustodia;
    private JLabel lblValTransacoes;
    private JLabel lblValVolumeMovimentado;

    private JTable tabelaContas;
    private DefaultTableModel modeloTabelaContas;

    public TelaDashboard() {
        this.controle = new RelatorioControle();
        inicializarComponentes();
        carregarDadosDashboard();
    }

    private void inicializarComponentes() {
        setTitle("Banco Master — Dashboard Executivo & Integração (POO / MVC)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1160, 700);
        setMinimumSize(new Dimension(980, 620));
        setLocationRelativeTo(null);
        getContentPane().setBackground(COR_FUNDO);
        setLayout(new BorderLayout(12, 12));

        // 1. Cabeçalho
        JPanel painelTopo = new JPanel(new BorderLayout());
        painelTopo.setBackground(COR_PRIMARIA);
        painelTopo.setBorder(BorderFactory.createEmptyBorder(16, 20, 16, 20));

        JLabel lblTitulo = new JLabel("BANCO MASTER — PAINEL DE GESTÃO, RELATÓRIOS E INTEGRAÇÃO");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 18));

        lblStatusBanco = new JLabel("Verificando MySQL...");
        lblStatusBanco.setForeground(Color.WHITE);
        lblStatusBanco.setFont(new Font("SansSerif", Font.BOLD, 12));

        painelTopo.add(lblTitulo, BorderLayout.WEST);
        painelTopo.add(lblStatusBanco, BorderLayout.EAST);
        add(painelTopo, BorderLayout.NORTH);

        // 2. Painel Central (Navegação Integrada + Cards KPI + Tabela INNER JOIN)
        JPanel painelCentro = new JPanel(new BorderLayout(12, 12));
        painelCentro.setBackground(COR_FUNDO);
        painelCentro.setBorder(BorderFactory.createEmptyBorder(0, 16, 12, 16));

        // Barra de Navegação conectada às telas reais de br.com.visao
        JPanel painelNavegacao = new JPanel(new GridLayout(2, 1, 6, 6));
        painelNavegacao.setBackground(Color.WHITE);
        painelNavegacao.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 225, 232)),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));

        // Linha 1: Relatórios (Integrante 6) + Telas Administrativas/Cadastros já existentes
        JPanel linhaCadastros = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 2));
        linhaCadastros.setOpaque(false);
        JLabel lblCadastros = new JLabel("Gestão & Relatórios: ");
        lblCadastros.setFont(new Font("SansSerif", Font.BOLD, 12));

        JButton btnRelatorio = criarBotao("Relatório de Movimentações (Período)", COR_PRIMARIA, Color.WHITE);
        btnRelatorio.addActionListener(e -> new TelaRelatorioMovimentacoes().setVisible(true));

        JButton btnAdmin = criarBotao("Painel Administrativo", COR_SECUNDARIA, Color.WHITE);
        btnAdmin.addActionListener(e -> new TelaAdministrativa().setVisible(true));

        JButton btnAgencias = criarBotao("Agências", new Color(243, 244, 246), Color.DARK_GRAY);
        btnAgencias.addActionListener(e -> new TelaAgencia().setVisible(true));

        JButton btnFuncionarios = criarBotao("Funcionários", new Color(243, 244, 246), Color.DARK_GRAY);
        btnFuncionarios.addActionListener(e -> new TelaFuncionarios().setVisible(true));

        JButton btnContas = criarBotao("Gerenciar Contas", new Color(243, 244, 246), Color.DARK_GRAY);
        btnContas.addActionListener(e -> new TelaContas().setVisible(true));

        JButton btnAtualizar = criarBotao("Atualizar Dashboard", new Color(229, 231, 235), Color.BLACK);
        btnAtualizar.addActionListener(e -> carregarDadosDashboard());

        linhaCadastros.add(lblCadastros);
        linhaCadastros.add(btnRelatorio);
        linhaCadastros.add(btnAdmin);
        linhaCadastros.add(btnAgencias);
        linhaCadastros.add(btnFuncionarios);
        linhaCadastros.add(btnContas);
        linhaCadastros.add(btnAtualizar);

        // Linha 2: Operações do Cliente (já existentes em br.com.visao)
        JPanel linhaOperacoes = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 2));
        linhaOperacoes.setOpaque(false);
        JLabel lblOperacoes = new JLabel("Operações Bancárias: ");
        lblOperacoes.setFont(new Font("SansSerif", Font.BOLD, 12));

        JButton btnAbrirConta = criarBotao("Abrir Conta", new Color(243, 244, 246), Color.DARK_GRAY);
        btnAbrirConta.addActionListener(e -> new TelaAbrirConta().setVisible(true));

        JButton btnSaque = criarBotao("Saque", new Color(22, 101, 52), Color.WHITE);
        btnSaque.addActionListener(e -> new TelaSaque().setVisible(true));

        JButton btnExtrato = criarBotao("Extrato da Conta", new Color(243, 244, 246), Color.DARK_GRAY);
        btnExtrato.addActionListener(e -> new TelaExtrato().setVisible(true));

        JButton btnMovimentacoes = criarBotao("Movimentações", new Color(22, 101, 52), Color.WHITE);
        btnMovimentacoes.addActionListener(e -> new TelaMovimentacoes().setVisible(true));

        // Descomente os botões abaixo quando TelaCadastroCliente, TelaDeposito e TelaTransferencia forem adicionadas:
        // JButton btnClientes = criarBotao("Cadastro de Clientes", new Color(243, 244, 246), Color.DARK_GRAY);
        // btnClientes.addActionListener(e -> new TelaCadastroCliente().setVisible(true));
        // JButton btnDeposito = criarBotao("Depósito", new Color(22, 101, 52), Color.WHITE);
        // btnDeposito.addActionListener(e -> new TelaDeposito().setVisible(true));
        // JButton btnTransferencia = criarBotao("Transferência", new Color(22, 101, 52), Color.WHITE);
        // btnTransferencia.addActionListener(e -> new TelaTransferencia().setVisible(true));

        linhaOperacoes.add(lblOperacoes);
        linhaOperacoes.add(btnAbrirConta);
        linhaOperacoes.add(btnSaque);
        linhaOperacoes.add(btnExtrato);
        linhaOperacoes.add(btnMovimentacoes);

        painelNavegacao.add(linhaCadastros);
        painelNavegacao.add(linhaOperacoes);

        // Cards KPI (6 indicadores)
        JPanel painelCards = new JPanel(new GridLayout(1, 6, 10, 10));
        painelCards.setOpaque(false);
        painelCards.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        lblValClientes = new JLabel("0");
        lblValAgencias = new JLabel("0");
        lblValContasAtivas = new JLabel("0");
        lblValSaldoCustodia = new JLabel("R$ 0,00");
        lblValTransacoes = new JLabel("0");
        lblValVolumeMovimentado = new JLabel("R$ 0,00");

        painelCards.add(criarCardKPI("Clientes", lblValClientes, COR_PRIMARIA));
        painelCards.add(criarCardKPI("Agências", lblValAgencias, COR_PRIMARIA));
        painelCards.add(criarCardKPI("Contas Ativas", lblValContasAtivas, COR_SECUNDARIA));
        painelCards.add(criarCardKPI("Saldo em Custódia", lblValSaldoCustodia, COR_VERDE));
        painelCards.add(criarCardKPI("Movimentações", lblValTransacoes, COR_SECUNDARIA));
        painelCards.add(criarCardKPI("Volume Movimentado", lblValVolumeMovimentado, COR_PRIMARIA));

        JPanel painelSuperiorCentro = new JPanel(new BorderLayout());
        painelSuperiorCentro.setOpaque(false);
        painelSuperiorCentro.add(painelNavegacao, BorderLayout.NORTH);
        painelSuperiorCentro.add(painelCards, BorderLayout.CENTER);

        painelCentro.add(painelSuperiorCentro, BorderLayout.NORTH);

        // Tabela com INNER JOIN (conta + cliente + agencia)
        JPanel painelTabela = new JPanel(new BorderLayout(6, 6));
        painelTabela.setBackground(Color.WHITE);
        painelTabela.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 225, 232)),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));

        JLabel lblTituloTabela = new JLabel(
                "Posição Consolidada das Contas Bancárias (INNER JOIN: Conta + Cliente + Agência)");
        lblTituloTabela.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblTituloTabela.setForeground(COR_PRIMARIA);

        String[] colunas = {"ID", "Conta", "Titular", "CPF", "Agência", "Tipo", "Status", "Saldo Atual", "Limite"};
        modeloTabelaContas = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaContas = new JTable(modeloTabelaContas);
        tabelaContas.setRowHeight(26);
        tabelaContas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaContas.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        tabelaContas.getTableHeader().setBackground(COR_PRIMARIA);
        tabelaContas.getTableHeader().setForeground(Color.WHITE);

        DefaultTableCellRenderer alinhadoDireita = new DefaultTableCellRenderer();
        alinhadoDireita.setHorizontalAlignment(SwingConstants.RIGHT);
        tabelaContas.getColumnModel().getColumn(7).setCellRenderer(alinhadoDireita);
        tabelaContas.getColumnModel().getColumn(8).setCellRenderer(alinhadoDireita);
        tabelaContas.getColumnModel().getColumn(0).setMaxWidth(45);

        painelTabela.add(lblTituloTabela, BorderLayout.NORTH);
        painelTabela.add(new JScrollPane(tabelaContas), BorderLayout.CENTER);

        painelCentro.add(painelTabela, BorderLayout.CENTER);
        add(painelCentro, BorderLayout.CENTER);
    }

    private JPanel criarCardKPI(String titulo, JLabel lblValor, Color corDestaque) {
        JPanel card = new JPanel(new BorderLayout(4, 6));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(3, 1, 1, 1, corDestaque),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblTitulo.setForeground(new Color(90, 100, 115));

        lblValor.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblValor.setForeground(corDestaque);

        card.add(lblTitulo, BorderLayout.NORTH);
        card.add(lblValor, BorderLayout.CENTER);
        return card;
    }

    private JButton criarBotao(String texto, Color fundo, Color textoCor) {
        JButton btn = new JButton(texto);
        btn.setBackground(fundo);
        btn.setForeground(textoCor);
        btn.setFocusPainted(false);
        btn.setFont(new Font("SansSerif", Font.BOLD, 12));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    public final void carregarDadosDashboard() {
        boolean conectado = controle.verificarConexaoBanco();
        if (conectado) {
            lblStatusBanco.setText("● MySQL Conectado (banco_master)");
            lblStatusBanco.setForeground(new Color(134, 239, 172));
        } else {
            lblStatusBanco.setText("● Falha na Conexão MySQL");
            lblStatusBanco.setForeground(new Color(252, 165, 165));
        }

        try {
            DashboardResumo resumo = controle.obterIndicadoresDashboard();
            lblValClientes.setText(String.valueOf(resumo.getTotalClientes()));
            lblValAgencias.setText(String.valueOf(resumo.getTotalAgencias()));
            lblValContasAtivas.setText(String.valueOf(resumo.getContasAtivas()));
            lblValSaldoCustodia.setText(controle.formatarMoeda(resumo.getSaldoTotalCustodia()));
            lblValTransacoes.setText(String.valueOf(resumo.getTotalTransacoes()));
            lblValVolumeMovimentado.setText(controle.formatarMoeda(resumo.getVolumeMovimentado()));

            List<ContaResumo> contasCarregadas = controle.listarContasComTitular();
            modeloTabelaContas.setRowCount(0);
            for (ContaResumo c : contasCarregadas) {
                modeloTabelaContas.addRow(new Object[]{
                        c.getId(),
                        c.getNumero(),
                        c.getTitularNome(),
                        c.getTitularCpf(),
                        c.getAgenciaFormatada(),
                        c.getTipo(),
                        c.getStatus(),
                        controle.formatarMoeda(c.getSaldo()),
                        controle.formatarMoeda(c.getLimite())
                });
            }
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Erro ao carregar dados do banco MySQL:\n" + ex.getMessage(),
                    "Erro de Banco de Dados",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }
        SwingUtilities.invokeLater(() -> new TelaDashboard().setVisible(true));
    }
}
