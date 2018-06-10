package it.polimi.se2018.network.server;

import it.polimi.se2018.model.events.MethodCallMessage;
import it.polimi.se2018.network.ClientInterface;
import it.polimi.se2018.network.ClientGathererInterface;
import it.polimi.se2018.network.RoomDispatcherInterface;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

public class ClientGathererImplementationSocket implements ClientGathererInterface {

    private static final Logger LOGGER = Logger.getLogger(ClientGathererImplementationSocket.class.getName());

    private final int port;

    private ServerSocket serverSocket;

    private RoomDispatcherInterface roomDispatcher;

    private String loggingSuffix;


    /**
     * This constructor is used the fir
     * @param port
     */
    public ClientGathererImplementationSocket(int port) {
        this.port = port;
        this.roomDispatcher = roomDispatcher;
        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            LOGGER.severe(e.getMessage());
        }
    }

    public ClientGathererImplementationSocket(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
        this.port = serverSocket.getLocalPort();
    }

    @Override
    public void run() {
        Socket newClientConnection = null;
        ClientInterface newClient;
        ObjectOutputStream objectOutputStream;

        LOGGER.info("Thread started with id " + Thread.currentThread().getId());
        loggingSuffix = new String(Thread.currentThread().getId() + " | ");
        LOGGER.info(loggingSuffix + "Listening on port " + this.port);

        try {

            newClientConnection = serverSocket.accept();

            startNewClientGathererSocket();

            handleNewClientRegistration(newClientConnection);

        } catch (IOException e) {
            LOGGER.severe(loggingSuffix + "IOException in serverSocket.accept()!");
            LOGGER.severe(loggingSuffix + e.getMessage());
        } catch (WrongMethodException e) {
            LOGGER.warning(loggingSuffix + "WrongMethodException in handleNewClientRegistration!");
            LOGGER.warning(loggingSuffix + "Sending WrongMethodException to client. ");
            try {
                objectOutputStream = new ObjectOutputStream(newClientConnection.getOutputStream());
                objectOutputStream.writeObject(e);
            } catch (IOException e1) {
                LOGGER.warning(loggingSuffix + "IOException in ObjectOutputStream creation from newClientConnection!");
                LOGGER.warning(e.getMessage());
            }
            LOGGER.warning(loggingSuffix + "Closing newClientConnection.");
            try {
                newClientConnection.close();
            } catch (IOException e1) {
                LOGGER.warning(loggingSuffix + "IOException in newClientConnection.close()!");
                LOGGER.warning(e.getMessage());
            }
        }

    }

    private void handleNewClientRegistration(Socket clientConnection) throws WrongMethodException {
        ObjectInputStream objectInputStream;
        ObjectOutputStream objectOutputStream;
        ClientInterface newClientInterface;
        MethodCallMessage registerClientMethodMessage;
        String clientName;

        Integer clientId;
        MethodCallMessage registerClientReturnMessage;

        try {
            objectInputStream = new ObjectInputStream(clientConnection.getInputStream());
            registerClientMethodMessage = (MethodCallMessage) objectInputStream.readObject();

            if(!registerClientMethodMessage.getMethodName().equals("registerNewClient")) {
                throw new WrongMethodException();
            }

            clientName = (String)registerClientMethodMessage.getArgument("name");

            objectOutputStream = new ObjectOutputStream(clientConnection.getOutputStream());

            newClientInterface = new ClientImplementationSocket(objectInputStream, objectOutputStream, roomDispatcher);

            clientId = roomDispatcher.handleClient(newClientInterface, clientName);

            registerClientReturnMessage = new MethodCallMessage("registerNewClient", clientId);

            objectOutputStream.writeObject(registerClientReturnMessage);

            ((ClientImplementationSocket) newClientInterface).start();

        } catch (IOException e) {
            LOGGER.severe(loggingSuffix + "IOException in handleNewClientRegistration!");
        } catch (ClassNotFoundException e) {
            LOGGER.severe(loggingSuffix + "ClassNotFoundException in objectInputStream.readObject()");
            LOGGER.severe(e.getMessage());
        }
    }

    private void startNewClientGathererSocket() {
        Thread t = new Thread(this);
        t.start();
    }

    @Override
    public void setRoomDispatcher(RoomDispatcherInterface roomDispatcher) {
        this.roomDispatcher = roomDispatcher;
    }
}
