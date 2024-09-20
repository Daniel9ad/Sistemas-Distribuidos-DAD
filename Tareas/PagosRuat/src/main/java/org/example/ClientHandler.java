package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.rmi.Naming;

class ClientHandler extends Thread {
    private Socket client;
    public ClientHandler(Socket client) {
        this.client = client;
    }
    @Override
    public void run() {
        try (
                BufferedReader from_client = new BufferedReader(new InputStreamReader(client.getInputStream()));
                PrintStream to_client = new PrintStream(client.getOutputStream())
        ) {
            String message;
            while ((message = from_client.readLine()) != null) {
                if (message.startsWith("Deuda:")) {
                    String ci = message.split(":")[1];
                    handleDebtQuery(ci, to_client);
                } else if (message.startsWith("Pagar:")) {
                    String[] params = message.split(":")[1].split(",");
                    String ci = params[0];
                    int year = Integer.parseInt(params[1]);
                    String tax_type = params[2];
                    double amount = Double.parseDouble(params[3]);

                    handlePayment(ci, year, tax_type, amount, to_client);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al manejar cliente: " + e.getMessage());
        }
    }
    private void handleDebtQuery(String ci, PrintStream to_client) {
        try {
            RuatInterface ruat = (RuatInterface) Naming.lookup("//localhost/RuatService");
            Deuda[] debts = ruat.buscar(ci);
            StringBuilder response = new StringBuilder();
            for (Deuda debt : debts) {
                response.append(debt.getYear()).append(",")
                        .append(debt.getTaxType()).append(",")
                        .append(debt.getAmount()).append(";");
            }

            to_client.println("deudas:" + response.toString());

        } catch (Exception e) {
            to_client.println("Error al consultar deudas: " + e.getMessage());
        }
    }
    private void handlePayment(String ci, int year, String tax_type, double amount, PrintStream to_client) {
        try {
            RuatInterface ruat = (RuatInterface) Naming.lookup("//localhost/RuatService");
            Deuda debt = new Deuda(ci, year, tax_type, amount);
            boolean result = ruat.pagar(debt);

            to_client.println("transacción:" + result);

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            to_client.println("Error al procesar pago: " + ex.getMessage());
        }
    }
}