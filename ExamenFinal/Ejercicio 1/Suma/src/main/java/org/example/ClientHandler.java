package org.example;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClientHandler extends Thread {
    DateFormat fordate = new SimpleDateFormat("yyyy/MM/dd");
    DateFormat fortime = new SimpleDateFormat("hh:mm:ss");
    final DataInputStream dis;
    final DataOutputStream dos;
    final Socket s;
    int total = 0;
    int n = 0;

    // Constructor
    public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos) {
        this.s = s;
        this.dis = dis;
        this.dos = dos;
    }

    @Override
    public void run() {
        String received;
        String toreturn;
        while (true) {
            try {

                // Ask user what he wants
                dos.writeUTF("FEcha hora?[Date | Time]..\n"
                        + "Escibir 0 para terminar la conneccion.");

                // receive the answer from client
                received = dis.readUTF();

                if (received.equals("0")) {
                    dos.writeUTF("Cantidad de veces: "+n+" Total: "+total);
//                    System.out.println("Client " + this.s + " sends exit...");
                    System.out.println("Closing this connection.");
                    this.s.close();
                    System.out.println("Connection closed");
                    break;
                } else {
                    System.out.println("No entro");
                }
                total += Integer.parseInt(received);
                n += 1;
                dos.writeUTF("Total: "+total);
                // creating Date object
//                Date date = new Date();

                // write on output stream based on the
                // answer from the client
//                switch (received) {
//
//                    case "Date":
//                        toreturn = fordate.format(date);
//                        dos.writeUTF(toreturn);
//                        break;
//
//                    case "Time":
//                        toreturn = fortime.format(date);
//                        dos.writeUTF(toreturn);
//                        break;
//
//                    default:
//                        dos.writeUTF("Invalid input");
//                        break;
//                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            // closing resources
            this.dis.close();
            this.dos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
