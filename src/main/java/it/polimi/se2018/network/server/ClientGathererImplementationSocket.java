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

/**
 * Implementation of ClientGathererInterface for RMI
 * @see it.polimi.se2018.network.ClientGathererInterface
 */
public class ClientGathererImplementationSocket implements ClientGathererInterface {

    private static final Logger LOGGER = Logger.getLogger(ClientGathererImplementationSocket.class.getName());

    private final int port;

    private ServerSocket serverSocket;

    private RoomDispatcherInterface roomDispatcher;

    private String loggingSuffix;


    /**
     * Constructs a new client gatherer, serving on the port specified
     * @param port integer representing the port where the gatherer should
     *             listen for incoming connections
     */
    public ClientGathererImplementationSocket(int port) {
        this.port = port;
        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            LOGGER.severe(e.getMessage());
        }
    }

    /**
     * Constructs a new client gatherer, using the ServerSocket specified
     * @param serverSocket ServerSocket to use to listen for incoming
     *                     connections
     */
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

            handleClient(newClientConnection);

        } catch (IOException e) {
            LOGGER.severe(loggingSuffix + "IOException in serverSocket.accept()!");
            LOGGER.severe(loggingSuffix + e.getMessage());
        } catch (WrongMethodException e) {
            LOGGER.warning(loggingSuffix + "WrongMethodException in handleClient!");
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


    /**
     * Handles a new client represented by a socket.
     * When this is called it start another thread of this gatherer,
     * listening on the same port.
     * In this way no clients connecting with socket have to wait for
     * the preceding client to connect.
     * @param clientConnection
     * @throws WrongMethodException
     */
    private void handleClient(Socket clientConnection) throws WrongMethodException {
        ObjectInputStream objectInputStream;
        ObjectOutputStream objectOutputStream;
        ClientInterface newClientInterface;
        MethodCallMessage clientMethodMessage;
        String clientName;

        Integer clientId;

        Boolean reconnectResult;
        MethodCallMessage clientReturnMessage;

        try {
            objectInputStream = new ObjectInputStream(clientConnection.getInputStream());
            clientMethodMessage = (MethodCallMessage) objectInputStream.readObject();

            switch (clientMethodMessage.getMethodName()) {
                case "registerNewClient":
                    clientName = (String)clientMethodMessage.getArgument("name");

                    objectOutputStream = new ObjectOutputStream(clientConnection.getOutputStream());

                    newClientInterface = new ClientImplementationSocket(objectInputStream, objectOutputStream, roomDispatcher);

                    clientId = roomDispatcher.handleClient(newClientInterface, clientName);

                    clientReturnMessage = new MethodCallMessage("registerNewClient", clientId);

                    objectOutputStream.writeObject(clientReturnMessage);

                    ((ClientImplementationSocket) newClientInterface).start();
                    break;
                case "reconnectClient":
                    clientId = (Integer) clientMethodMessage.getArgument("id");
                    System.out.println("TENTATIVO DI RICONNESSIONE DA " + clientId);
                    objectOutputStream = new ObjectOutputStream(clientConnection.getOutputStream());

                    newClientInterface = new ClientImplementationSocket(objectInputStream, objectOutputStream, roomDispatcher);

                    reconnectResult = roomDispatcher.reconnectClient(newClientInterface, clientId);

                    clientReturnMessage = new MethodCallMessage("reconnectClient", reconnectResult);

                    objectOutputStream.writeObject(clientReturnMessage);

                    ((ClientImplementationSocket) newClientInterface).start();

                    roomDispatcher.sendGameStateToReconnectedClient(clientId);

                    break;
                default:
                    throw new WrongMethodException();
             }

        } catch (IOException e) {
            LOGGER.severe(loggingSuffix + "IOException in handleClient!");
        } catch (ClassNotFoundException e) {
            LOGGER.severe(loggingSuffix + "ClassNotFoundException in objectInputStream.readObject()");
            LOGGER.severe(e.getMessage());
        }
    }

    /**
     * Starts a new thread of this gatherer
     */
    private void startNewClientGathererSocket() {
        Thread t = new Thread(this);
        t.start();
    }

    @Override
    public void setRoomDispatcher(RoomDispatcherInterface roomDispatcher) {
        this.roomDispatcher = roomDispatcher;
    }
}
