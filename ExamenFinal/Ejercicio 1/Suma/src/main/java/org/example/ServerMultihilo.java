package org.example;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMultihilo {
    public static void main(String[] args) throws IOException
    {
        // Escuchandoen el puerto 5056
        ServerSocket ss = new ServerSocket(5056);

        //ciclo al infinito
        while (true)
        {
            Socket s = null;
            System.out.println("Server inciado...");
            try
            {
                // Coneccion del cliente
                s = ss.accept();

                System.out.println("un nuevo cliente se ha conectado : " + s);

                // obtener su entrada y salida de stream
                DataInputStream dis = new DataInputStream(s.getInputStream());
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());

                System.out.println("Asignar un nuevo hilo para este cliente ");

                // create a new thread object
                Thread t = new ClientHandler(s, dis, dos);

                // Invoking the start() method
                t.start();

            }
            catch (Exception e){
                s.close();
                e.printStackTrace();
            }
        }
    }
}
