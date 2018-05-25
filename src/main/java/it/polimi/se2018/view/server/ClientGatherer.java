package it.polimi.se2018.view.server;

import it.polimi.se2018.view.client.ClientInterface;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientGatherer extends Thread{
    private final int port;
    private ServerSocket serverSocket;
    private VirtualViewSocket virtualViewSocket;
    private VirtualView virtualView;

    public ClientGatherer(int port, VirtualView virtualView) {
        this.port = port;
        this.virtualView = virtualView;
        // Inizializzo il server socket
        try {
            this.serverSocket = new ServerSocket(port);
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
                virtualViewSocket = new VirtualViewSocket(virtualView, newClientConnection);
                virtualViewSocket.addClient(virtualViewSocket);
                virtualViewSocket.start();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
