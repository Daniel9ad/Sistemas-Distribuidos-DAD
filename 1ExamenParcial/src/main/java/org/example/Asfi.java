package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Asfi extends UnicastRemoteObject implements IAsfi {

    public Asfi() throws RemoteException {
        super();
    }

    @Override
    public ArrayList<String> ConsultarCuentas(String ci, String nombres, String apellidos) throws RemoteException {
        ArrayList<String> auxiliar = new ArrayList<String>();
        try {
            // Banco Mercantil
            int puertoMercantil = 6789;
            String dato = "Buscar:"+ci+"-"+nombres+"-"+apellidos;
            String ip = "localhost";
            DatagramSocket socketUDP = new DatagramSocket();
            byte[] mensaje = dato.getBytes();
            InetAddress hostServidor = InetAddress.getByName(ip);
            // Construimos un datagrama para enviar el mensaje al servidor
            DatagramPacket peticion = new DatagramPacket(
                    mensaje,
                    dato.length(),
                    hostServidor,
                    puertoMercantil
            );
            // Enviamos el datagrama
            socketUDP.send(peticion);
            // Construimos el DatagramPacket que contendrá la respuesta
            byte[] bufer = new byte[1000];
            DatagramPacket respuesta = new DatagramPacket(bufer, bufer.length);
            socketUDP.receive(respuesta);
            // Enviamos la respuesta del servidor a la salida estandar
            String response = new String(respuesta.getData(), 0, respuesta.getLength());
            // String[] comandos = respuesta.toString().split(":");
            System.out.println("Response");
            System.out.println(response);
            auxiliar.add(response);
            // Cerramos el socket
            socketUDP.close();

            // Banco BCP
            Socket client = new Socket("localhost", 5003);
            System.out.println(client);
            PrintStream toServer = new PrintStream(client.getOutputStream());
            BufferedReader fromServer = new BufferedReader(
                    new InputStreamReader(client.getInputStream())
            );
            toServer.println(dato);
            String result = fromServer.readLine();
            auxiliar.add(result);

        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        }
        return auxiliar;
    }


    @Override
    public Boolean RetenerMonto(String cuenta, double montoBs, String glosa) throws RemoteException {
        try {
            // Banco Mercantil
            int puertoMercantil = 6789;
            String dato = "Congelar:"+cuenta+"-"+montoBs;
            String ip = "localhost";
            DatagramSocket socketUDP = new DatagramSocket();
            byte[] mensaje = dato.getBytes();
            InetAddress hostServidor = InetAddress.getByName(ip);
            // Construimos un datagrama para enviar el mensaje al servidor
            DatagramPacket peticion = new DatagramPacket(
                    mensaje,
                    dato.length(),
                    hostServidor,
                    puertoMercantil
            );
            // Enviamos el datagrama
            socketUDP.send(peticion);
            // Construimos el DatagramPacket que contendrá la respuesta
            byte[] bufer = new byte[1000];
            DatagramPacket respuesta = new DatagramPacket(bufer, bufer.length);
            socketUDP.receive(respuesta);
            // Enviamos la respuesta del servidor a la salida estandar
            String response = new String(respuesta.getData(), 0, respuesta.getLength());
            // String[] comandos = respuesta.toString().split(":");
            System.out.println("Response");
            System.out.println(response);
//            auxiliar.add(response);
            // Cerramos el socket
            socketUDP.close();

            // Banco BCP
            Socket client = new Socket("localhost", 5003);
            System.out.println(client);
            PrintStream toServer = new PrintStream(client.getOutputStream());
            BufferedReader fromServer = new BufferedReader(
                    new InputStreamReader(client.getInputStream())
            );
            toServer.println(dato);
            String result = fromServer.readLine();
//            auxiliar.add(result);

        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        }
        return false;
    }

    public static void main(String[] args) {
        try {
//            System.setProperty("java.rmi.server.hostname", "localhost");
            java.rmi.registry.LocateRegistry.createRegistry(1099) ;
            Asfi asfiService = new Asfi();
            java.rmi.Naming.bind("Asfi", asfiService);
            System.out.println("Servidor Asfi listo.");
        } catch (Exception e) {
            System.out.println("Error en el servidor Asfi: " + e.getMessage());
        }
    }

}

