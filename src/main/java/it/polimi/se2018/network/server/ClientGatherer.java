package it.polimi.se2018.network.server;

import it.polimi.se2018.view.VirtualView;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

public class ClientGatherer extends Thread{
    private static final Logger LOGGER = Logger.getLogger( ClientGatherer.class.getName() );
    private final int port;
    private ServerSocket serverSocket;
    private ServerImplementationSocket serverImplementationSocket;
    private VirtualView virtualView;

    public ClientGatherer(int port, VirtualView virtualView) {
        this.port = port;
        this.virtualView = virtualView;
        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            LOGGER.severe(e.getMessage());
        }
    }

    @Override
    public void run(){

        LOGGER.info("Thread started.");

        while(true) {

            Socket newClientConnection;
            LOGGER.info("Waiting for a new client.");

            try {
                newClientConnection = serverSocket.accept();
                LOGGER.info("A new client connected: " + newClientConnection.getInetAddress());
                serverImplementationSocket = new ServerImplementationSocket(virtualView, newClientConnection);
                serverImplementationSocket.addClient(serverImplementationSocket);
                serverImplementationSocket.start();
            } catch (IOException e) {
                LOGGER.severe(e.getMessage());
            }

        }
    }
}
