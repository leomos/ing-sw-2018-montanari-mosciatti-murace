package it.polimi.se2018.view.client;

import it.polimi.se2018.view.server.VirtualViewInterface;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Client {

    public static void main(String[] args) {

        ViewClient viewClient = new ViewClient();

        System.out.println("0: RMI \n 1: SOCKET \n\n");
        int i = 0;

        System.out.println("0: CONSOLE \n 1: GUI \n\n");
        int j = 0;

        if(j == 0)
            viewClient = new ViewClientConsole();

        VirtualViewInterface virtualViewInterface;

        if(i == 0) {

            try {

                virtualViewInterface = (VirtualViewInterface) Naming.lookup("//localhost/MyServer2");
                ClientImplementationRMI clientImplementationRMI = new ClientImplementationRMI(viewClient);
                ClientInterface remoteRef = (ClientInterface) UnicastRemoteObject.exportObject(clientImplementationRMI, 0);
                virtualViewInterface.addClient(remoteRef);


            } catch (MalformedURLException e) {
                System.err.println("URL non trovato!");
            } catch (RemoteException e) {
                System.err.println("Errore di connessione: " + e.getMessage() + "!");
            } catch (NotBoundException e) {
                System.err.println("Il riferimento passato non è associato a nulla!");
            }
        }
    }
}
