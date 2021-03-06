package it.polimi.se2018.network.client;

import it.polimi.se2018.network.ClientInterface;
import it.polimi.se2018.network.ServerInterface;
import it.polimi.se2018.view.ViewClient;
import it.polimi.se2018.view.console.ViewClientConsole;
import it.polimi.se2018.view.gui.ViewClientGUI;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        final OptionParser optionParser = new OptionParser();

        final String[] hostOptions = {
                "h",
                "host"
        };
        optionParser.acceptsAll(Arrays.asList(hostOptions),"Host to connect to.")
                .withRequiredArg()
                .ofType(String.class)
                .defaultsTo("localhost");


        final String[] socketPortOptions = {
                "s",
                "socketPort"
        };
        optionParser.acceptsAll(Arrays.asList(socketPortOptions), "Turn timer countdown value.")
                .withRequiredArg()
                .ofType(Integer.class)
                .defaultsTo(1111);


        final String[] rmiPortOptions = {
                "r",
                "rmiPort"
        };

        optionParser.acceptsAll(Arrays.asList(rmiPortOptions), "Server hostname.")
                .withRequiredArg()
                .ofType(Integer.class)
                .defaultsTo(1099);

        final OptionSet optionSet = optionParser.parse(args);


        System.out.println(optionSet.asMap());
        String host = (String) optionSet.valueOf("host");
        int socketPort = (int) optionSet.valueOf("socketPort");
        int rmiPort = (int) optionSet.valueOf("rmiPort");

        boolean moveOk = true;
        Scanner input = new Scanner(System.in);

        String typeView;
        do {
            System.out.println("\nInsert 0 to use Console, 1 to use GUI");
            typeView = input.nextLine();

            if (typeView.equals("0") || typeView.equals("1"))
                moveOk = false;
        }
        while (moveOk);

        String name;
        moveOk = true;
        do {
            System.out.println("\nInsert Name");
            name = input.nextLine();

            if(name.length() < 10)
                moveOk = false;
            else
                System.out.println("Name too long!");

        }
        while (moveOk);

        String typeConnection;
        moveOk = true;
        do {
            System.out.println("\nInsert 0 to use SOCKET, 1 to use RMI");
            typeConnection = input.nextLine();

            if(typeConnection.equals("0") || typeConnection.equals("1"))
                moveOk = false;
        }
        while(moveOk);


        int server = Integer.parseInt(typeConnection);

        ViewClient viewClient;
        if(typeView.equals("0")) {
            System.out.println("Starting console");
            viewClient = new ViewClientConsole(host, socketPort, rmiPort, server);
        }
        else {
            System.out.println("Starting GUI");
            viewClient = new ViewClientGUI(host, socketPort, rmiPort, server);
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
