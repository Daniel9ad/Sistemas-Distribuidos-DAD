package org.example;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class AlcaldiaUDP {
    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket(6000)) {
            System.out.println("Servidor Alcaldía UDP listo.");
            while (true) {
                byte[] buffer = new byte[256];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                String received = new String(packet.getData(), 0, packet.getLength());
                System.out.println("Consulta recibida: " + received);

                String ci = received.split(":")[1];
                String response;

                if (ci.equals("1234567")) {
                    response = "respuesta:false";
                } else {
                    response = "respuesta:true";
                }
                byte[] responseBytes = response.getBytes();
                DatagramPacket responsePacket = new DatagramPacket(responseBytes, responseBytes.length, packet.getAddress(), packet.getPort());
                socket.send(responsePacket);
            }
        } catch (Exception e) {
            System.out.println("Error en el servidor Alcaldía UDP: " + e.getMessage());
        }
    }
}
