package org.example;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface IAsfi extends Remote {
    public ArrayList<String> ConsultarCuentas (String ci, String nombres, String apellidos) throws RemoteException;
    public Boolean RetenerMonto(String cuenta, double montoBs, String glosa) throws RemoteException;
}
