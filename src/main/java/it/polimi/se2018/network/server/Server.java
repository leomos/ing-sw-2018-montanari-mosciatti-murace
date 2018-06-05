package it.polimi.se2018.network.server;

import it.polimi.se2018.view.VirtualView;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Server {

    private static int PORT = 1099; // porta di default

    public static void main(String[] args) {

        VirtualView virtualView = new VirtualView();

        //Creo la ServerImplementationRMI

        try {

            LocateRegistry.createRegistry(PORT);

        } catch (RemoteException e) {
            System.out.println("Registry già presente!");
        }

        try {

            ServerImplementationRMI serverImplementationRMI = new ServerImplementationRMI(virtualView);
            Naming.rebind("//localhost/MyServer2", serverImplementationRMI);
            System.out.println("Server ready");
        } catch (MalformedURLException e) {
            System.err.println("Impossibile registrare l'oggetto indicato!");
        } catch (RemoteException e) {
            System.err.println("Errore di connessione: " + e.getMessage() + "!");
        }

        // Creo l'infrastruttura per il socket
        (new ClientGatherer(1111, virtualView)).start();
    }

}
