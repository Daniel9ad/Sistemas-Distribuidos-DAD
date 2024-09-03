package org.example;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.ArrayList;
public class BancoTCPRMI {
    public static void main(String[] args) {
        int port = 5000;
        List<Thread> clients = new ArrayList<>();
       try (ServerSocket server = new ServerSocket(port)) {
            System.out.println("Banco TCP esperando conexiones...");
            while (true) {
                Socket client = server.accept();
                System.out.println("Cliente conectado.");
                Thread thread = new ClientHandler(client);
                clients.add(thread);
                thread.start();
            }
        } catch (IOException ex) {
            System.out.println("Error en el servidor: " + ex.getMessage());
        }
    }
}