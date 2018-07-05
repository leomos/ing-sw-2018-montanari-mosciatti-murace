package it.polimi.se2018.network.client;

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
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        //String host = "163.172.183.230";
        String host = "localhost";
        //String host = "192.168.43.123";
        int socketPort = 1200;
        int rmiPort = 8080;

        System.out.println("\nInsert Type View");
        Scanner input = new Scanner(System.in);
        String typeView = input.nextLine();

        System.out.println("\nInsert Name");
        input = new Scanner(System.in);
        String name = input.nextLine();

        boolean moveOk = true;
        String type;
        do {
            System.out.println("\nInsert 0 to use SOCKET, 1 to use RMI");
            type = input.nextLine();

            if(type.equals("0") || type.equals("1"))
                moveOk = false;
        }
        while(moveOk);


        int server = Integer.parseInt(type);

        ViewClient viewClient;
        if(typeView.equals("0")) {
            System.out.println("Starting console");
            viewClient = new ViewClientConsole(host, socketPort, rmiPort, 0, server);
        }
        else {
            System.out.println("Starting GUI");
            viewClient = new ViewClientConsole(host, socketPort, rmiPort, 1, server);
        }
        ServerInterface serverInterface = null;
        int id = 0;
        if(server == 0) {
            System.out.println("SOCKET!");
            try {
                serverInterface = new ServerImplementationSocket(viewClient);
                Socket socket = new Socket(host, socketPort);
                id = serverInterface.registerClient(socket, name);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if(server == 1) {
            System.out.println("RMI!");
            try {
                serverInterface = (ServerInterface) Naming.lookup("//"+host+":"+rmiPort+"/sagrada");
                ClientImplementationRMI clientImplementationRMI = new ClientImplementationRMI(viewClient);
                ClientInterface remoteRef = (ClientInterface) UnicastRemoteObject.exportObject(clientImplementationRMI, 0);
                id = serverInterface.registerClient(remoteRef, name);
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (NotBoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        viewClient.setServerInterface(serverInterface);
        viewClient.startHeartbeating(id);
        System.out.println(id);
        viewClient.setIdClient(id);

        System.out.println("Waiting for other players to start a lobby...");

        if(typeView.equals("0"))
            ((ViewClientConsole)viewClient).run();

    }
}
