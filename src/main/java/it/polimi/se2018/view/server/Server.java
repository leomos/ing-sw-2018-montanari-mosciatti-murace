package it.polimi.se2018.view.server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Server {

    private static int PORT = 1099; // porta di default

    public static void main(String[] args) {

        //if (stanza_ready)

            VirtualView virtualView = new VirtualView();

            //Creo la VirtualViewRMI

            try {

                LocateRegistry.createRegistry(PORT);

            } catch (RemoteException e) {
                System.out.println("Registry già presente!");
            }

            try {

                VirtualViewRMI virtualViewRMI = new VirtualViewRMI(virtualView);
                Naming.rebind("//localhost/MyServer2", virtualViewRMI);
                System.out.println("Server ready");
            } catch (MalformedURLException e) {
                System.err.println("Impossibile registrare l'oggetto indicato!");
            } catch (RemoteException e) {
                System.err.println("Errore di connessione: " + e.getMessage() + "!");
            }


    }
}
