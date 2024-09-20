package org.example;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Juez {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        IAsfi asfi;
        ArrayList<String> lista = null;
        String ci = "";
        String nombres = "";
        String apellidos = "";
        try {
            asfi = (IAsfi) Naming.lookup("rmi://localhost/Asfi");
            System.out.print("Introduzca ci: ");
            ci = sc.nextLine();
            System.out.print("Introduzca nombres: ");
            nombres = sc.nextLine();
            System.out.print("Introduzca apellidos: ");
            apellidos = sc.nextLine();
            lista = asfi.ConsultarCuentas(ci, nombres, apellidos);
            System.out.println("Lista de cuentas");
            for (String c: lista) {
                System.out.println(c);
            }

            System.out.print("Introduzca el numero de cuenta que quiere retener: ");
            String nCuenta = sc.nextLine();
            System.out.print("Introduzca el monto a retener: ");
            double monto_retener = sc.nextDouble();
            sc.nextLine();
            System.out.print("Introduzca la glosa explicando el motivo de la retención: ");
            String glosa_retener = sc.nextLine();
            boolean res = asfi.RetenerMonto(nCuenta, monto_retener, glosa_retener);

        } catch (NotBoundException ex) {
            Logger.getLogger(Juez.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(Juez.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(Juez.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
