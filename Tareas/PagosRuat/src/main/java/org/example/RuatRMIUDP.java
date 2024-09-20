package org.example;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RuatRMIUDP extends UnicastRemoteObject implements RuatInterface {
    private Connection connection;

    protected RuatRMIUDP() throws RemoteException {
        super();
        try {
            // Conectar a la base de datos
//            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pagos_ruat", "root", "");
//            connection = DriverManager.getConnection("jdbc:sqlite:pagos_ruat.db");
//            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/pagos_ruat";
            String user = "postgres";
            String password = "daniel31415";
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Conexión a PostgreSQL establecida.");
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
        }
    }

    @Override
    public Deuda[] buscar(String ci) throws RemoteException {
        try {
            String query = "SELECT * FROM deudas WHERE ci = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, ci);
            ResultSet resultSet = statement.executeQuery();

            // Crear lista dinámica para almacenar las deudas
            List<Deuda> deudas = new ArrayList<>();
            while (resultSet.next()) {
                Deuda deuda = new Deuda(
                        resultSet.getString("ci"),
                        resultSet.getInt("year"),
                        resultSet.getString("tax_type"),
                        resultSet.getDouble("amount")
                );
                deudas.add(deuda);
            }
            return deudas.toArray(new Deuda[0]);

        } catch (SQLException e) {
            System.out.println("Error al buscar deudas: " + e.getMessage());
            return new Deuda[0];
        }
    }

    @Override
    public boolean pagar(Deuda deuda) throws RemoteException {
        // Verificar con la Alcaldía si el CI tiene observaciones
        boolean noObservations = consultarAlcaldia(deuda.getCi());

        if (noObservations) {
            try {
                // Eliminar la deuda de la base de datos
                String query = "DELETE FROM deudas WHERE ci = ? AND year = ? AND tax_type = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, deuda.getCi());
                statement.setInt(2, deuda.getYear());
                statement.setString(3, deuda.getTaxType());
                int rowsAffected = statement.executeUpdate();
                return rowsAffected > 0; // Si se elimina la deuda, retorna true
            } catch (SQLException e) {
                System.out.println("Error al eliminar deuda: " + e.getMessage());
                return false;
            }
        } else {
            return false;
        }
    }

    private boolean consultarAlcaldia(String ci) {
        try {
            DatagramSocket socket = new DatagramSocket();
            InetAddress address = InetAddress.getByName("localhost");
            String query = "consulta:" + ci;
            byte[] buffer = query.getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, 6000);
            socket.send(packet);

            byte[] responseBuffer = new byte[256];
            DatagramPacket responsePacket = new DatagramPacket(responseBuffer, responseBuffer.length);
            socket.receive(responsePacket);

            String response = new String(responsePacket.getData(), 0, responsePacket.getLength());
            socket.close();

            return response.equals("respuesta:true");
        } catch (Exception e) {
            System.out.println("Error al consultar a la Alcaldía: " + e.getMessage());
            return false;
        }
    }

    public static void main(String[] args) {
        try {
            java.rmi.registry.LocateRegistry.createRegistry(1099);
            RuatRMIUDP ruatService = new RuatRMIUDP();
            java.rmi.Naming.bind("//localhost/RuatService", ruatService);
            System.out.println("Servidor RUAT RMI listo.");
        } catch (Exception e) {
            System.out.println("Error en el servidor RUAT: " + e.getMessage());
        }
    }
}
