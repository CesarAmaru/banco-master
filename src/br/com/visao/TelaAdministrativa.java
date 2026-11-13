
package br.com.visao;

import javax.swing.*;
import java.awt.*;

public class TelaAdministrativa extends JFrame {

    public TelaAdministrativa() {

        setTitle("Banco Master - Área Administrativa");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel titulo = new JLabel(
                "PAINEL ADMINISTRATIVO",
                SwingConstants.CENTER
        );

        titulo.setFont(new Font("Arial", Font.BOLD, 22));

        JButton btnFuncionarios =
                new JButton("Gerenciar Funcionários");

        JButton btnContas =
                new JButton("Gerenciar Contas");

        JButton btnSair =
                new JButton("Sair");

        btnFuncionarios.addActionListener(e -> {
            new TelaFuncionarios().setVisible(true);
        });

        // Abre a tela de gerenciamento de contas
        btnContas.addActionListener(e -> {
            new TelaContas().setVisible(true);
        });

        btnSair.addActionListener(e -> dispose());

        JPanel painel = new JPanel();
        painel.setLayout(new GridLayout(3, 1, 15, 15));
        painel.setBorder(
                BorderFactory.createEmptyBorder(35, 60, 35, 60)
        );

        painel.add(btnFuncionarios);
        painel.add(btnContas);
        painel.add(btnSair);

        setLayout(new BorderLayout());
        add(titulo, BorderLayout.NORTH);
        add(painel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TelaAdministrativa().setVisible(true);
        });
    }
}
