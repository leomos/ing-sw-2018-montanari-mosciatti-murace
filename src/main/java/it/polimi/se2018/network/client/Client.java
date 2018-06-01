package it.polimi.se2018.network.client;

import it.polimi.se2018.network.server.ServerInterface;
import it.polimi.se2018.view.ViewClient;
import it.polimi.se2018.view.ViewClientConsole;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Client {

    private static final int PORT = 1111;
    private static final String HOST = "localhost";

    public static void main(String[] args) {

        ViewClient viewClient;

        System.out.println("0: RMI \n1: SOCKET \n\n");
        int i = 0;

        System.out.println("0: CONSOLE \n1: GUI \n\n");
        int j = 0;


        ServerInterface serverInterface;
        if (j == 0){
            if (i == 0) {

                try {

                    serverInterface = (ServerInterface) Naming.lookup("//localhost/MyServer2");
                    viewClient = new ViewClientConsole();
                    ClientImplementationRMI clientImplementationRMI = new ClientImplementationRMI(viewClient);
                    ClientInterface remoteRef = (ClientInterface) UnicastRemoteObject.exportObject(clientImplementationRMI, 0);
                    serverInterface.addClient(remoteRef);


                } catch (MalformedURLException e) {
                    System.err.println("URL non trovato!");
                } catch (RemoteException e) {
                    System.err.println("Errore di connessione: " + e.getMessage() + "!");
                } catch (NotBoundException e) {
                    System.err.println("Il riferimento passato non è associato a nulla!");
                }
            }
        }

        if(i == 1){
            if(j == 0){

                viewClient = new ViewClientConsole();
                Socket socket = null;

                try {
                    socket = new Socket(HOST, PORT);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                ClientImplementationSocket clientImplementationSocket = new ClientImplementationSocket(viewClient, socket);
                clientImplementationSocket.start();
                // stops the connection

            }
        }
    }
}
