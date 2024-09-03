package org.example;
import java.rmi.Remote;
import java.rmi.RemoteException;
public interface RuatInterface extends Remote {
    Deuda[] buscar(String ci) throws RemoteException;
    boolean pagar(Deuda deuda) throws RemoteException;
}
