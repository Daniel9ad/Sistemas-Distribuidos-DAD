package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerBCP {
    public static void main(String[] args) {
        List<Cuenta> cuentasBanco = new ArrayList<>();
        cuentasBanco.add(new Cuenta(Banco.BCP, "237284", "1234567", "Daniel", "Duran", 1000));
        cuentasBanco.add(new Cuenta(Banco.BCP, "5464543", "2323232", "Daniel", "Duran", 788));
        cuentasBanco.add(new Cuenta(Banco.BCP, "557563", "123232", "Daniel", "Duran", 3434));
        cuentasBanco.add(new Cuenta(Banco.BCP, "4102164", "11021654", "Juan Perez", "Segovia", 3000));
        int port = 5003;
        try (ServerSocket server = new ServerSocket(port)) {
            System.out.println("Servidor Banco BCP TCP listo.");
            while (true) {
//                Socket client;
                PrintStream toClient;
                Socket client = server.accept(); //conexion entre cliente y servidor para comunicacion bidireccional
                BufferedReader fromClient = new BufferedReader(new InputStreamReader(client.getInputStream())); // el lector
                System.out.println("Cliente se conecto");
                String recibido = fromClient.readLine();
                String[] comandos = recibido.split(":");
                if (comandos[0].equals("Buscar")){
                    String[] data = comandos[1].split("-");
                    String cuentaUsuario = "";
                    for (Cuenta c: cuentasBanco){
                        if (data[0].equals(c.getCi()) && data[1].equals(c.getNombres()) && data[2].equals(c.getApellidos())){
                            cuentaUsuario = c.toString();
                        }
                    }
                    System.out.println(cuentaUsuario);
                    toClient = new PrintStream(client.getOutputStream());
                    toClient.println(cuentaUsuario);
                }else{
//                    String[] data = comandos[1].split("-");
//                    String cuentaUsuario = "";
//                    for (Cuenta c: cuentasBanco){
//                        if (data[0].equals(c.getNrocuenta())){
//                            c.setSaldo(c.getSaldo()-Double(data[));
//                            cuentaUsuario = c.toString();
//                        }
//                    }
//                    System.out.println(cuentaUsuario);
//                    toClient = new PrintStream(client.getOutputStream());
//                    toClient.println(cuentaUsuario);
                }
            }
        } catch (IOException ex) {
            System.out.println("Error en el servidor: " + ex.getMessage());
        }
    }
}
