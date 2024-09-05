package Votacion;

import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Votaciones extends ReceiverAdapter {
    private JChannel channel;
    private final Map<String, Integer> votes = new HashMap<>();
    private final Map<String, Integer> totalVotes = new HashMap<>();

    // Constructor para inicializar la votación
    public Votaciones() {
        // Inicializando los candidatos
        totalVotes.put("juan", 0);
        totalVotes.put("pedro", 0);
        totalVotes.put("maria", 0);
    }

    @Override
    public void viewAccepted(View new_view) {
        System.out.println("** Vista: " + new_view);
    }

    @Override
    public void receive(Message msg) {
        // Se recibe un mensaje con los votos de una mesa
        @SuppressWarnings("unchecked")
        Map<String, Integer> receivedVotes = (Map<String, Integer>) msg.getObject();
        System.out.println("Votos recibidos de " + msg.getSrc() + ": " + receivedVotes);
        // Actualizar el conteo de votos
        synchronized (totalVotes) {
            for (String candidate : receivedVotes.keySet()) {
                int count = totalVotes.get(candidate) + receivedVotes.get(candidate);;
                totalVotes.put(candidate, count);
            }
        }

        // Mostrar el resultado actual de la votación
        showCurrentResults();
    }

    private void showCurrentResults() {
        System.out.println("Resultado actual:");
        synchronized (totalVotes) {
            totalVotes.forEach((candidate, count) -> {
                System.out.println(candidate + ": " + count + " votos");
            });
        }
    }

    private void start() throws Exception {
        channel = new JChannel();  // Crea un canal JGroups
        channel.setReceiver(this); // Establece el receptor
        channel.connect("VotingCluster"); // Se conecta al clúster
        eventLoop(); // Inicia el bucle de eventos
        channel.close(); // Cierra el canal cuando finaliza
    }

    private void eventLoop() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                // Leer el resultado de la votación por mesa
                System.out.println("Introduce los votos en formato 'mesa 1, juan 10, pedro 5, maria 3' ( exit para salir): ");
                String input = scanner.nextLine().toLowerCase();

                if (input.startsWith("exit")) {
                    break;
                }

                // Procesar el input y extraer los votos
                parseVotes(input);

                // Enviar el mensaje con los votos al canal
                Message msg = new Message(null, new HashMap<>(votes));
                channel.send(msg);

                // Limpiar los votos después de enviarlos
                votes.clear();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Método para procesar los votos
    private void parseVotes(String input) {
        // Ejemplo: mesa 1, juan 10, pedro 5, maria 3
        String[] parts = input.split(",");
        for (String part : parts) {
            part = part.trim();
            if (part.startsWith("mesa")) {
                System.out.println("Procesando " + part);
            } else {
                String[] voteInfo = part.split(" ");
                String candidate = voteInfo[0].trim();
                int voteCount = Integer.parseInt(voteInfo[1].trim());
                votes.put(candidate, voteCount);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        new Votaciones().start();
    }
}
