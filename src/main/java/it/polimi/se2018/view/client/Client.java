package it.polimi.se2018.view.client;

import it.polimi.se2018.view.server.VirtualViewInterface;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Client {

    public static void main(String[] args) {

        //if RMI

        try {

            VirtualViewInterface virtualViewInterface = (VirtualViewInterface)Naming.lookup("//localhost/MyServer");
            //ClientImplementationRMI clientImplementationRMI = new ClientImplementationRMI();
            //ClientInterface remoteRef = (ClientInterface) UnicastRemoteObject.exportObject(clientImplementationRMI, 0);
            //virtualViewInterface.addClient(remoteRef);

        } catch (MalformedURLException e) {
            System.err.println("URL non trovato!");
        } catch (RemoteException e) {
            System.err.println("Errore di connessione: " + e.getMessage() + "!");
        } catch (NotBoundException e) {
            System.err.println("Il riferimento passato non è associato a nulla!");
        }
    }
}