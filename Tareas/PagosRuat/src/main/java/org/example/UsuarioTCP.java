package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class UsuarioTCP extends JFrame {
    private JTextField ciField;
    private JTextArea resultArea;
    private JTextField yearField;
    private JTextField taxTypeField;
    private JTextField amountField;
    private PrintStream to_server;
    private BufferedReader from_server;

    public UsuarioTCP() {
        setTitle("Consulta y Pago de Deudas");
        setSize(500, 600);  // Tamaño aumentado para mejor visibilidad
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  // Centrar ventana
        setLayout(new BorderLayout(10, 10));  // Añadir márgenes

        // Crear panel de entrada
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Datos de Usuario"));
        inputPanel.setBackground(new Color(245, 245, 245));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Campo para CI
        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(new JLabel("CI:"), gbc);
        gbc.gridx = 1;
        ciField = new JTextField(15);
        inputPanel.add(ciField, gbc);

        // Campo para Año
        gbc.gridx = 0; gbc.gridy = 1;
        inputPanel.add(new JLabel("Año:"), gbc);
        gbc.gridx = 1;
        yearField = new JTextField(15);
        inputPanel.add(yearField, gbc);

        // Campo para Tipo de Impuesto
        gbc.gridx = 0; gbc.gridy = 2;
        inputPanel.add(new JLabel("Tipo de Impuesto:"), gbc);
        gbc.gridx = 1;
        taxTypeField = new JTextField(15);
        inputPanel.add(taxTypeField, gbc);

        // Campo para Monto
        gbc.gridx = 0; gbc.gridy = 3;
        inputPanel.add(new JLabel("Monto a Pagar:"), gbc);
        gbc.gridx = 1;
        amountField = new JTextField(15);
        inputPanel.add(amountField, gbc);

        // Botones
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton queryButton = new JButton("Consultar Deudas");
        JButton payButton = new JButton("Pagar Deuda");

        queryButton.setBackground(new Color(59, 89, 182));
        queryButton.setForeground(Color.WHITE);
        queryButton.setFocusPainted(false);
        queryButton.setFont(new Font("Tahoma", Font.BOLD, 12));

        payButton.setBackground(new Color(76, 175, 80));
        payButton.setForeground(Color.WHITE);
        payButton.setFocusPainted(false);
        payButton.setFont(new Font("Tahoma", Font.BOLD, 12));

        buttonPanel.add(queryButton);
        buttonPanel.add(payButton);
        buttonPanel.setBackground(new Color(245, 245, 245));

        // Añadir ActionListeners a los botones
        queryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                consultarDeudas();
            }
        });

        payButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pagarDeuda();
            }
        });

        // Área de resultados
        resultArea = new JTextArea(10, 30);
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        resultArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        JScrollPane scrollPane = new JScrollPane(resultArea);

        // Añadir componentes a la ventana
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        connectToServer();
    }

    private void connectToServer() {
        int port = 5000;
        String ip = "localhost";
        try {
            Socket client = new Socket(ip, port);
            to_server = new PrintStream(client.getOutputStream());
            from_server = new BufferedReader(new InputStreamReader(client.getInputStream()));
        } catch (IOException ex) {
            resultArea.setText("Error de conexión: " + ex.getMessage());
        }
    }

    private void consultarDeudas() {
        String ci = ciField.getText();
        System.out.println(ci);
        to_server.println("Deuda:" + ci);
        try {
            String response = from_server.readLine();
            System.out.println(response);
            resultArea.setText("Deudas: \n" + response);
        } catch (IOException e) {
            resultArea.setText("Error al consultar deudas: " + e.getMessage());
        }
    }

    private void pagarDeuda() {
        String ci = ciField.getText();
        int year = Integer.parseInt(yearField.getText());
        String taxType = taxTypeField.getText();
        double amount = Double.parseDouble(amountField.getText());
        to_server.println("Pagar:" + ci + "," + year + "," + taxType + "," + amount);
        try {
            String response = from_server.readLine();
            resultArea.setText("Resultado del pago: \n" + response);
        } catch (IOException e) {
            resultArea.setText("Error al pagar deuda: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UsuarioTCP frame = new UsuarioTCP();
            frame.setVisible(true);
        });
    }
}