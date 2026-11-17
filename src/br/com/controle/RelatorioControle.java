package br.com.controle;

import br.com.dao.RelatorioDAO;
import br.com.entidade.ContaResumo;
import br.com.entidade.DashboardResumo;
import br.com.entidade.MovimentacaoRelatorio;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.List;
import java.util.Locale;

/**
 * Camada Controle (Controller) que concentra as regras de validação,
 * formatação e orquestração entre as telas Swing e o RelatorioDAO,
 * seguindo o mesmo padrão de exceções de negócio do projeto.
 */
public class RelatorioControle {

    private final RelatorioDAO relatorioDAO;
    private static final DateTimeFormatter FORMATO_DATA =
            DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);
    private static final DateTimeFormatter FORMATO_DATA_HORA =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private static final NumberFormat FORMATO_MOEDA =
            NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    public RelatorioControle() {
        this.relatorioDAO = new RelatorioDAO();
    }

    public boolean verificarConexaoBanco() {
        return relatorioDAO.testarConexao();
    }

    public DashboardResumo obterIndicadoresDashboard() {
        return relatorioDAO.carregarIndicadoresDashboard();
    }

    public List<ContaResumo> listarContasComTitular() {
        return relatorioDAO.listarContasComTitular();
    }

    public List<MovimentacaoRelatorio> gerarRelatorioMovimentacoes(
            String dataInicioTexto,
            String dataFimTexto,
            String tipoOperacao,
            ContaResumo contaSelecionada) {

        LocalDate dataInicio = parseDataOpcional(dataInicioTexto, "Data Inicial");
        LocalDate dataFim = parseDataOpcional(dataFimTexto, "Data Final");

        if (dataInicio != null && dataFim != null && dataInicio.isAfter(dataFim)) {
            throw new IllegalArgumentException("A Data Inicial não pode ser maior que a Data Final.");
        }

        Integer contaId = (contaSelecionada != null && contaSelecionada.getId() > 0)
                ? contaSelecionada.getId()
                : null;

        return relatorioDAO.filtrarMovimentacoes(dataInicio, dataFim, tipoOperacao, contaId);
    }

    public BigDecimal calcularVolumeTotal(List<MovimentacaoRelatorio> movimentacoes) {
        BigDecimal total = BigDecimal.ZERO;
        if (movimentacoes != null) {
            for (MovimentacaoRelatorio m : movimentacoes) {
                if (m.getValor() != null) {
                    total = total.add(m.getValor());
                }
            }
        }
        return total;
    }

    public void exportarRelatorioCSV(File arquivo, List<MovimentacaoRelatorio> movimentacoes) {
        if (movimentacoes == null || movimentacoes.isEmpty()) {
            throw new IllegalArgumentException("Não há registros na tabela para exportar.");
        }

        try (FileOutputStream fos = new FileOutputStream(arquivo);
             OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
             BufferedWriter writer = new BufferedWriter(osw)) {

            writer.write('\uFEFF');
            writer.write("ID;Data/Hora;Tipo de Operacao;Conta Origem;Conta Destino;Valor (R$)");
            writer.newLine();

            for (MovimentacaoRelatorio m : movimentacoes) {
                String linha = String.format("%d;%s;%s;%s;%s;%.2f",
                        m.getId(),
                        formatarDataHora(m.getDataHora()),
                        m.getTipo(),
                        m.getOrigemFormatada(),
                        m.getDestinoFormatada(),
                        m.getValor());
                writer.write(linha);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao gravar arquivo CSV: " + e.getMessage(), e);
        }
    }

    public String formatarMoeda(BigDecimal valor) {
        return FORMATO_MOEDA.format(valor != null ? valor : BigDecimal.ZERO);
    }

    public String formatarDataHora(LocalDateTime dataHora) {
        return dataHora != null ? dataHora.format(FORMATO_DATA_HORA) : "—";
    }

    public String formatarDataHoje() {
        return LocalDate.now().format(FORMATO_DATA);
    }

    private LocalDate parseDataOpcional(String texto, String nomeCampo) {
        if (texto == null || texto.isBlank() || texto.replace("/", "").trim().isEmpty()) {
            return null;
        }
        try {
            return LocalDate.parse(texto.trim(), FORMATO_DATA);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(
                    nomeCampo + " inválida. Utilize o formato dd/MM/aaaa (ex: 10/01/2024).");
        }
    }
}
