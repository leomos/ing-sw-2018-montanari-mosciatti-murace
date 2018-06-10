package it.polimi.se2018.network.client;

import it.polimi.se2018.model.events.PlayerMessage;
import it.polimi.se2018.model.events.PlayerMessageSetup;
import it.polimi.se2018.network.ClientInterface;
import it.polimi.se2018.network.ServerInterface;
import it.polimi.se2018.view.ViewClient;
import it.polimi.se2018.view.console.ViewClientConsole;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;

public class Client {

    public static void main(String[] args) {
        int server = (new Random()).nextInt(2);
        ViewClient viewClient = new ViewClientConsole();
        ServerInterface serverInterface = null;
        int id = 0;
        if(server == 0) {
            System.out.println("SOCKET!");
            try {
                serverInterface = new ServerImplementationSocket(viewClient);
                Socket socket = new Socket("localhost", 1111);
                id = serverInterface.registerClient(socket, "piero");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if(server == 1) {
            System.out.println("RMI!");
            try {
                serverInterface = (ServerInterface) Naming.lookup("//localhost/sagrada");
                ClientImplementationRMI clientImplementationRMI = new ClientImplementationRMI(viewClient);
                ClientInterface remoteRef = (ClientInterface) UnicastRemoteObject.exportObject(clientImplementationRMI, 0);
                id = serverInterface.registerClient(remoteRef, "piero");
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (NotBoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        System.out.println(id);
        viewClient.setServerInterface(serverInterface);
        ((ViewClientConsole) viewClient).run();
    }
}
