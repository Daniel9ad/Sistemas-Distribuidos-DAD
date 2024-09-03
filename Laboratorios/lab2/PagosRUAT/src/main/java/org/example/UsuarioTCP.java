package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class UsuarioTCP {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int port = 5000;
        String ip = "";
        try (Socket client = new Socket(ip, port)) {
            PrintStream to_server = new PrintStream(client.getOutputStream());
            BufferedReader from_server = new BufferedReader(new InputStreamReader(client.getInputStream()));
            while (true) {
                System.out.println("Menú de opciones:");
                System.out.println("1. Consultar deudas");
                System.out.println("2. Pagar deuda");
                System.out.println("3. Salir");
                System.out.print("Seleccione una opción: ");
                int option = scanner.nextInt();
                scanner.nextLine();
                if (option == 1) {
                    System.out.print("Ingrese su CI: ");
                    String ci = scanner.nextLine();
                    to_server.println("Deuda:" + ci);
                    String response = from_server.readLine();
                    System.out.println("Deudas: " + response);
                } else if (option == 2) {
                    System.out.print("Ingrese su CI: ");
                    String ci = scanner.nextLine();
                    System.out.print("Ingrese el año de la deuda: ");
                    int year = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Ingrese el tipo de impuesto (Vehículo o Casa): ");
                    String tax_type = scanner.nextLine();
                    System.out.print("Ingrese el monto a pagar: ");
                    double amount = scanner.nextDouble();
                    scanner.nextLine();
                    to_server.println("Pagar:" + ci + "," + year + "," + tax_type + "," + amount);
                    String payment_response = from_server.readLine();
                    System.out.println("Resultado de la transacción: " + payment_response);
                } else if (option == 3) {
                    System.out.println("Saliendo del sistema...");
                    break;
                } else {
                    System.out.println("Opción no válida.");
                }
            }
        } catch (IOException ex) {
            System.out.println("Error de conexión: " + ex.getMessage());
        }
    }
}
