package projeto_barbearia;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class barbearia {

    static class Corte {
        private String horario;
        private String tipoCorte;
        private double valor;
        private String nomeCliente;
        private String telefoneCliente;

        public Corte(String horario, String tipoCorte, double valor, String nomeCliente, String telefoneCliente) {
            this.horario = horario;
            this.tipoCorte = tipoCorte;
            this.valor = valor;
            this.nomeCliente = nomeCliente;
            this.telefoneCliente = telefoneCliente;
        }

        @Override
        public String toString() {
            return "Nome do Cliente: " + nomeCliente + ", Telefone: " + telefoneCliente + ", Horário: " + horario + ", Tipo de Corte: " + tipoCorte + ", Valor: R$ " + valor;
        }
    }

    private static ArrayList<Corte> registros = new ArrayList<>();

    public static void main(String[] args) {
        JFrame frame = new JFrame("Registro de Barbearia");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(540, 960); // Ajustar o tamanho da janela
        frame.setResizable(false); // Desativa a opção de redimensionar a janela

        frame.setLayout(new BorderLayout());

        // Criação do painel com imagem de fundo
        JPanel backgroundPanel = new BackgroundPanel("projeto_barbearia/telalogin.jpg");
        backgroundPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Margens

        JLabel nomeLabel = new JLabel("Nome do Cliente:");
        nomeLabel.setForeground(Color.WHITE);
        JTextField nomeField = new JTextField(20);

        JLabel telefoneLabel = new JLabel("Telefone:");
        telefoneLabel.setForeground(Color.WHITE);
        JTextField telefoneField = new JTextField(20);

        JLabel horarioLabel = new JLabel("Horário:");
        horarioLabel.setForeground(Color.WHITE);
        JTextField horarioField = new JTextField(20);

        JLabel tipoCorteLabel = new JLabel("Tipo de Corte:");
        tipoCorteLabel.setForeground(Color.WHITE);
        JTextField tipoCorteField = new JTextField(20);

        JLabel valorLabel = new JLabel("Valor:");
        valorLabel.setForeground(Color.WHITE);
        JTextField valorField = new JTextField(20);

        JButton adicionarButton = new JButton("Adicionar Registro");
        JButton salvarButton = new JButton("Salvar Registros");
        JButton resetButton = new JButton("Resetar Registros");

        adicionarButton.setBackground(new Color(0, 150, 136)); // Cor de fundo verde
        adicionarButton.setForeground(Color.WHITE); // Cor do texto
        salvarButton.setBackground(new Color(0, 150, 136)); // Cor de fundo verde
        salvarButton.setForeground(Color.WHITE); // Cor do texto
        resetButton.setBackground(new Color(255, 82, 82)); // Cor de fundo vermelha
        resetButton.setForeground(Color.WHITE); // Cor do texto

        gbc.gridx = 0;
        gbc.gridy = 0;
        backgroundPanel.add(nomeLabel, gbc);

        gbc.gridx = 1;
        backgroundPanel.add(nomeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        backgroundPanel.add(telefoneLabel, gbc);

        gbc.gridx = 1;
        backgroundPanel.add(telefoneField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        backgroundPanel.add(horarioLabel, gbc);

        gbc.gridx = 1;
        backgroundPanel.add(horarioField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        backgroundPanel.add(tipoCorteLabel, gbc);

        gbc.gridx = 1;
        backgroundPanel.add(tipoCorteField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        backgroundPanel.add(valorLabel, gbc);

        gbc.gridx = 1;
        backgroundPanel.add(valorField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        backgroundPanel.add(adicionarButton, gbc);

        gbc.gridy = 6;
        backgroundPanel.add(salvarButton, gbc);

        gbc.gridy = 7;
        backgroundPanel.add(resetButton, gbc);

        frame.add(backgroundPanel, BorderLayout.CENTER);

        adicionarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nomeCliente = nomeField.getText();
                String telefoneCliente = telefoneField.getText();
                String horario = horarioField.getText();
                String tipoCorte = tipoCorteField.getText();
                double valor;

                try {
                    valor = Double.parseDouble(valorField.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Valor inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                registros.add(new Corte(horario, tipoCorte, valor, nomeCliente, telefoneCliente));
                JOptionPane.showMessageDialog(frame, "Registro adicionado com sucesso!");

                // Limpar os campos
                nomeField.setText("");
                telefoneField.setText("");
                horarioField.setText("");
                tipoCorteField.setText("");
                valorField.setText("");
            }
        });

        salvarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Gerar nome do arquivo baseado na data e hora atuais
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
                String timestamp = LocalDateTime.now().format(formatter);
                String fileName = "registros_cortes_" + timestamp + ".txt";

                try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                    writer.write("Registros de Cortes:\n");
                    for (Corte corte : registros) {
                        writer.write(corte.toString() + "\n");
                    }
                    JOptionPane.showMessageDialog(frame, "Arquivo de texto gerado com sucesso: " + fileName);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame, "Erro ao salvar o arquivo!", "Erro", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Limpar a lista de registros
                registros.clear();
                JOptionPane.showMessageDialog(frame, "Registros do dia foram resetados com sucesso!");
            }
        });

        frame.setVisible(true);
    }
}

class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    public BackgroundPanel(String imagePath) {
        try {
            // Carregar a imagem do caminho relativo
            backgroundImage = new ImageIcon(getClass().getResource("/" + imagePath)).getImage();
            if (backgroundImage == null) {
                throw new IOException("Imagem não encontrada: " + imagePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao carregar imagem: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
