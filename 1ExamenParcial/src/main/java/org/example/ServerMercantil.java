package org.example;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class ServerMercantil {
    public static void main(String args[]) {
        List<Cuenta> cuentasBanco = new ArrayList<>();
        cuentasBanco.add(new Cuenta(Banco.Mercantil, "8743847", "1234567", "Daniel", "Duran", 1000));
        cuentasBanco.add(new Cuenta(Banco.Mercantil, "2132434", "2323232", "Daniel", "Duran", 788));
        cuentasBanco.add(new Cuenta(Banco.Mercantil, "4102164", "11021654", "Juan Perez", "Segovia", 3000));

        int port = 6789;
        try {
            DatagramSocket socketUDP = new DatagramSocket(port);
            byte[] bufer = new byte[1000];
            System.out.println("Servidor Banco Mercantil UDP listo.");
            while (true) {
                // Construimos el DatagramPacket para recibir peticiones
                DatagramPacket peticion = new DatagramPacket(bufer, bufer.length);
                // Leemos una petición del DatagramSocket
                socketUDP.receive(peticion);
                // lo recibido
                String cadena = new String(peticion.getData());
                System.out.println("Recivido");
                System.out.println(cadena);
//                String cuentas = buscarCuentas(cadena);
                String[] comandos = cadena.split(":");
                if (comandos[0].equals("Buscar")){
                    String[] data = comandos[1].split("-");
                    String cuentaUsuario = "";
                    for (Cuenta c: cuentasBanco){
                        if (data[0].equals(c.getCi()) && data[1].equals(c.getNombres())){
                            cuentaUsuario = c.toString();
                        }
                    }
                    System.out.println(cuentaUsuario);
                    String response = cuentaUsuario;
                    byte[] mensaje = response.getBytes();
                    DatagramPacket respuesta = new DatagramPacket(
                            mensaje,
                            response.length(),
                            peticion.getAddress(),
                            peticion.getPort()
                    );
                    // Enviamos la respuesta, que es un eco
                    socketUDP.send(respuesta);
                }
            }
        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        }
    }

//    public static String buscarCuentas(String solicitud) {
//        System.out.println("Bucando datos");
//        System.out.println(solicitud);
////        String cuentas = "";
//        String[] comandos = solicitud.split(":");
//        String[] ci = comandos[1].split("-");
//        for (int i = 0; i < cu; i++) {
//
//        }
//
//
////        if (datos[0].equals("7687682") && datos[1].equals("Juan") && datos[2].equals("Segobia")) {
////            cuentas = "1112450-5000";
////            return cuentas;
////        }
////        if (datos[0].equals("54654") && datos[1].equals("Maria") && datos[2].equals("Parra")) {
////            cuentas = "1121454-300";
////            return cuentas;
////        }
//
//        return "no:encontrado";
//    }

}

