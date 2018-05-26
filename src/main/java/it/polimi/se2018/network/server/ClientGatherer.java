package it.polimi.se2018.network.server;

import it.polimi.se2018.network.client.ClientInterface;
import it.polimi.se2018.view.VirtualView;

import java.io.IOException;
import java.net.Socket;

public class ClientGatherer extends Thread{
    private final int port;
    private java.net.ServerSocket serverSocket;
    private ServerSocket virtualViewSocket;
    private VirtualView virtualView;

    public ClientGatherer(int port, VirtualView virtualView) {
        this.port = port;
        this.virtualView = virtualView;
        // Inizializzo il server socket
        try {
            this.serverSocket = new java.net.ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run(){

        // In loop attendo la connessione di nuovi client

        System.out.println("Waiting for clients.\n");

        while(true) {

            Socket newClientConnection;

            try {

                ClientInterface clientInterface;
                newClientConnection = serverSocket.accept();
                System.out.println("A new client connected.");
                virtualViewSocket = new ServerSocket(virtualView, newClientConnection);
                virtualViewSocket.addClient(virtualViewSocket);
                virtualViewSocket.start();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
