package br.com.visao;

import br.com.controle.RelatorioControle;
import br.com.entidade.ContaResumo;
import br.com.entidade.MovimentacaoRelatorio;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingUtilities;

/**
 * Tela de Relatório da Rotina Principal (Movimentações Bancárias por Período).
 * Permite filtrar por data inicial, data final, tipo de operação e conta,
 * apresentando dados legíveis obtidos via JOIN em JTable e permitindo exportação CSV.
 */
public class TelaRelatorioMovimentacoes extends JFrame {

    private static final Color COR_PRIMARIA = new Color(15, 41, 66);
    private static final Color COR_FUNDO = new Color(244, 246, 249);

    private final RelatorioControle controle;
    private List<MovimentacaoRelatorio> listaAtual = new ArrayList<>();

    private JTextField txtDataInicio;
    private JTextField txtDataFim;
    private JComboBox<String> cmbTipoOperacao;
    private JComboBox<ContaResumo> cmbConta;

    private JTable tabelaRelatorio;
    private DefaultTableModel modeloTabela;

    private JLabel lblTotalRegistros;
    private JLabel lblVolumeTotal;

    public TelaRelatorioMovimentacoes() {
        this.controle = new RelatorioControle();
        inicializarComponentes();
        carregarComboContas();
        aplicarFiltros();
    }

    private void inicializarComponentes() {
        setTitle("Banco Master — Relatório de Movimentações Bancárias por Período");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1220, 620);
        setMinimumSize(new Dimension(900, 520));
        setLocationRelativeTo(null);
        getContentPane().setBackground(COR_FUNDO);
        setLayout(new BorderLayout(10, 10));

        // Cabeçalho
        JPanel painelTopo = new JPanel(new BorderLayout());
        painelTopo.setBackground(COR_PRIMARIA);
        painelTopo.setBorder(BorderFactory.createEmptyBorder(14, 18, 14, 18));

        JLabel lblTitulo = new JLabel("RELATÓRIO DE MOVIMENTAÇÕES BANCÁRIAS (ROTINA PRINCIPAL)");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 16));
        painelTopo.add(lblTitulo, BorderLayout.WEST);

        add(painelTopo, BorderLayout.NORTH);

        // Painel de Filtros
        JPanel painelCentral = new JPanel(new BorderLayout(10, 10));
        painelCentral.setBackground(COR_FUNDO);
        painelCentral.setBorder(BorderFactory.createEmptyBorder(0, 14, 12, 14));

        JPanel painelFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        painelFiltros.setBackground(Color.WHITE);
        painelFiltros.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 225, 232)),
                BorderFactory.createEmptyBorder(6, 8, 6, 8)
        ));

        txtDataInicio = new JTextField("01/01/2024", 9);
        txtDataFim = new JTextField(controle.formatarDataHoje(), 9);

        cmbTipoOperacao = new JComboBox<>(new String[]{
                "TODOS", "DEPOSITO", "SAQUE", "TRANSFERENCIA"
        });

        cmbConta = new JComboBox<>();
        cmbConta.setPreferredSize(new Dimension(196, 26));

        JButton btnFiltrar = new JButton("Filtrar Relatório");
        btnFiltrar.setBackground(COR_PRIMARIA);
        btnFiltrar.setForeground(Color.WHITE);
        btnFiltrar.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnFiltrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnFiltrar.addActionListener(e -> aplicarFiltros());

        JButton btnLimpar = new JButton("Limpar");
        btnLimpar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLimpar.addActionListener(e -> limparFiltros());

        JButton btnExportar = new JButton("Exportar CSV");
        btnExportar.setBackground(new Color(21, 128, 61));
        btnExportar.setForeground(Color.WHITE);
        btnExportar.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnExportar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnExportar.addActionListener(e -> exportarCSV());

        painelFiltros.add(new JLabel("Data Inicial:"));
        painelFiltros.add(txtDataInicio);
        painelFiltros.add(new JLabel("Data Final:"));
        painelFiltros.add(txtDataFim);
        painelFiltros.add(new JLabel("Operação:"));
        painelFiltros.add(cmbTipoOperacao);
        painelFiltros.add(new JLabel("Conta:"));
        painelFiltros.add(cmbConta);
        painelFiltros.add(btnFiltrar);
        painelFiltros.add(btnLimpar);
        painelFiltros.add(btnExportar);

        painelCentral.add(painelFiltros, BorderLayout.NORTH);

        // Tabela de Resultados (JOIN legível)
        String[] colunas = {
                "ID", "Data / Hora", "Tipo de Operação",
                "Conta Origem (Titular)", "Conta Destino (Titular)", "Valor"
        };

        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaRelatorio = new JTable(modeloTabela);
        tabelaRelatorio.setRowHeight(26);
        tabelaRelatorio.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        tabelaRelatorio.getTableHeader().setBackground(COR_PRIMARIA);
        tabelaRelatorio.getTableHeader().setForeground(Color.WHITE);

        DefaultTableCellRenderer alinhadoDireita = new DefaultTableCellRenderer();
        alinhadoDireita.setHorizontalAlignment(SwingConstants.RIGHT);
        tabelaRelatorio.getColumnModel().getColumn(5).setCellRenderer(alinhadoDireita);
        tabelaRelatorio.getColumnModel().getColumn(0).setMaxWidth(50);
        tabelaRelatorio.getColumnModel().getColumn(1).setPreferredWidth(120);
        tabelaRelatorio.getColumnModel().getColumn(2).setPreferredWidth(120);
        tabelaRelatorio.getColumnModel().getColumn(3).setPreferredWidth(230);
        tabelaRelatorio.getColumnModel().getColumn(4).setPreferredWidth(230);

        painelCentral.add(new JScrollPane(tabelaRelatorio), BorderLayout.CENTER);

        // Rodapé Totalizador
        JPanel painelRodape = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 8));
        painelRodape.setBackground(Color.WHITE);
        painelRodape.setBorder(BorderFactory.createLineBorder(new Color(220, 225, 232)));

        lblTotalRegistros = new JLabel("Registros encontrados: 0");
        lblTotalRegistros.setFont(new Font("SansSerif", Font.BOLD, 13));

        lblVolumeTotal = new JLabel("Volume Total Filtrado: R$ 0,00");
        lblVolumeTotal.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblVolumeTotal.setForeground(COR_PRIMARIA);

        painelRodape.add(lblTotalRegistros);
        painelRodape.add(lblVolumeTotal);

        painelCentral.add(painelRodape, BorderLayout.SOUTH);
        add(painelCentral, BorderLayout.CENTER);
    }

    private void carregarComboContas() {
        try {
            cmbConta.removeAllItems();
            cmbConta.addItem(new ContaResumo(0, "", "Todas as contas", ""));
            for (ContaResumo conta : controle.listarContasComTitular()) {
                cmbConta.addItem(conta);
            }
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Erro ao carregar lista de contas:\n" + ex.getMessage(),
                    "Erro de Banco",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void aplicarFiltros() {
        try {
            String dataInicio = txtDataInicio.getText();
            String dataFim = txtDataFim.getText();
            String tipo = (String) cmbTipoOperacao.getSelectedItem();
            ContaResumo conta = (ContaResumo) cmbConta.getSelectedItem();

            listaAtual = controle.gerarRelatorioMovimentacoes(dataInicio, dataFim, tipo, conta);
            modeloTabela.setRowCount(0);

            for (MovimentacaoRelatorio m : listaAtual) {
                modeloTabela.addRow(new Object[]{
                        m.getId(),
                        controle.formatarDataHora(m.getDataHora()),
                        m.getTipo(),
                        m.getOrigemFormatada(),
                        m.getDestinoFormatada(),
                        controle.formatarMoeda(m.getValor())
                });
            }

            BigDecimal volume = controle.calcularVolumeTotal(listaAtual);
            lblTotalRegistros.setText("Registros encontrados: " + listaAtual.size());
            lblVolumeTotal.setText("Volume Total Filtrado: " + controle.formatarMoeda(volume));

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Filtro Inválido", JOptionPane.WARNING_MESSAGE);
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao consultar movimentações:\n" + ex.getMessage(), "Erro SQL", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparFiltros() {
        txtDataInicio.setText("01/01/2024");
        txtDataFim.setText(controle.formatarDataHoje());
        cmbTipoOperacao.setSelectedIndex(0);
        if (cmbConta.getItemCount() > 0) {
            cmbConta.setSelectedIndex(0);
        }
        aplicarFiltros();
    }

    private void exportarCSV() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Salvar Relatório de Movimentações (CSV)");
        chooser.setSelectedFile(new File("relatorio_movimentacoes_banco_master.csv"));
        chooser.setFileFilter(new FileNameExtensionFilter("Arquivo CSV (*.csv)", "csv"));

        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File arquivo = chooser.getSelectedFile();
            if (!arquivo.getName().toLowerCase().endsWith(".csv")) {
                arquivo = new File(arquivo.getAbsolutePath() + ".csv");
            }
            try {
                controle.exportarRelatorioCSV(arquivo, listaAtual);
                JOptionPane.showMessageDialog(
                        this,
                        "Relatório exportado com sucesso para:\n" + arquivo.getAbsolutePath(),
                        "Exportação Concluída",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Aviso", JOptionPane.WARNING_MESSAGE);
            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro ao Exportar", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TelaRelatorioMovimentacoes tela = new TelaRelatorioMovimentacoes();
            tela.setVisible(true);
        });
    }
}
