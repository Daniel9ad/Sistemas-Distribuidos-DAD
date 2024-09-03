package org.example;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
public class RuatRMIUDP extends UnicastRemoteObject implements RuatInterface {
    private List<Deuda> deudaList;

    protected RuatRMIUDP() throws RemoteException {
        super();
        deudaList = new ArrayList<>();
        deudaList.add(new Deuda("1234567", 2022, "Vehículo", 2451));
        deudaList.add(new Deuda("1234567", 2022, "Casa", 2500));
        deudaList.add(new Deuda("555587", 2021, "Vehículo", 5000));
        deudaList.add(new Deuda("333357", 2023, "Casa", 24547));
    }

    @Override
    public Deuda[] buscar(String ci) throws RemoteException {
        return deudaList.stream().filter(d -> d.getCi().equals(ci)).toArray(Deuda[]::new);
    }

    @Override
    public boolean pagar(Deuda deuda) throws RemoteException {
        // Verificar con la Alcaldía si el CI tiene observaciones
        boolean noObservations = consultarAlcaldia(deuda.getCi());

        if (noObservations) {
            // Eliminar la deuda de la lista
            deudaList.removeIf(d -> d.getCi().equals(deuda.getCi()) && d.getYear() == deuda.getYear() && d.getTaxType().equals(deuda.getTaxType()));
            return true;
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
            System.setProperty("java.rmi.server.hostname", "26.125.21.51");
            java.rmi.registry.LocateRegistry.createRegistry(1099) ;
            RuatRMIUDP ruatService = new RuatRMIUDP();
            java.rmi.Naming.bind("//26.125.21.51/RuatService", ruatService);
            System.out.println("Servidor RUAT RMI listo.");
        } catch (Exception e) {
            System.out.println("Error en el servidor RUAT: " + e.getMessage());
        }
    }
}
