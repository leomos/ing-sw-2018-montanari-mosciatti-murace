package it.polimi.se2018.network.server;

import it.polimi.se2018.model.events.*;
import it.polimi.se2018.network.ClientInterface;
import it.polimi.se2018.network.RoomDispatcherInterface;
import it.polimi.se2018.network.visitor.MessageVisitorImplementationClient;
import it.polimi.se2018.network.visitor.MessageVisitorInterface;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Implementation of ClientInterface for clients connecting with Socket
 * @see it.polimi.se2018.network.ClientInterface
 */
public class ClientImplementationSocket extends Thread implements ClientInterface {

    private Socket clientSocket;

    private ObjectInputStream objectInputStream;

    private ObjectOutputStream objectOutputStream;

    private RoomDispatcherInterface roomDispatcher;

    private boolean ready = false;

    private Object lock = new Object();

    private Message inputMessage;

    private MessageVisitorInterface messageVisitorInterface = new MessageVisitorImplementationClient(this);


    public ClientImplementationSocket(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        this.objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
        this.objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
    }


    /**
     * Constructs a new ClientImplementationSocket using specfic object stream and room dispatcher
     * @param objectInputStream ObjectInputStream to be used to receive messages
     * @param objectOutputStream  ObjectOutputStream to be used to send messages
     * @param roomDispatcher RoomDispatcherInterface to be used to dispatch new clients
     * @throws IOException
     */
    public ClientImplementationSocket(ObjectInputStream objectInputStream,
                                      ObjectOutputStream objectOutputStream,
                                      RoomDispatcherInterface roomDispatcher) throws IOException {
        this.objectInputStream = objectInputStream;
        this.objectOutputStream = objectOutputStream;
        this.roomDispatcher = roomDispatcher;
    }

    @Override
    public void run() {
        try {
            while (null != (inputMessage = (Message) objectInputStream.readObject())) {
                Runnable task = () -> {
                    inputMessage.accept(messageVisitorInterface);
                };
                Thread thread = new Thread(task);
                thread.start();
            }
        } catch (IOException e) {

        } catch (ClassNotFoundException e) {

        }
    }


    /**
     * Notify the room where this client is playing, passing from the room dispatcher
     * @param playerMessage message to notify to the room
     */
    public void notifyRoom(PlayerMessage playerMessage) {
        roomDispatcher.getRoomForId(playerMessage.getPlayerId()).notifyView(playerMessage);
    }

    @Override
    public void update(ModelChangedMessage modelChangedMessage) throws RemoteException {
        try {
            this.objectOutputStream.writeObject(modelChangedMessage);
        } catch (IOException e) {
            //e.printStackTrace()-;
            System.out.println("Cannot talk with client even though is not inactive!");
        }
    }

    @Override
    public Integer getDieFromPatternCard() throws RemoteException {
        return (Integer) waitForMethodCallResponse("getDieFromPatternCard", Optional.empty());
    }

    @Override
    public ArrayList<Integer> getDieFromRoundTrack() throws RemoteException {
        return (ArrayList<Integer>) waitForMethodCallResponse("getDieFromRoundTrack", Optional.empty());
    }

    @Override
    public Integer getDieFromDiceArena() throws RemoteException {
        return (Integer) waitForMethodCallResponse("getDieFromDiceArena", Optional.empty());
    }

    @Override
    public ArrayList<Integer> getDoublePositionInPatternCard() throws RemoteException {
        return (ArrayList<Integer>) waitForMethodCallResponse("getDoublePositionInPatternCard", Optional.empty());
    }

    @Override
    public ArrayList<Integer> getIncrementedValue() throws RemoteException {
        return (ArrayList<Integer>) waitForMethodCallResponse("getIncrementedValue", Optional.empty());
    }

    @Override
    public ArrayList<Integer> getPositionInPatternCard() throws RemoteException {
        return (ArrayList<Integer>) waitForMethodCallResponse("getPositionInPatternCard", Optional.empty());
    }

    @Override
    public ArrayList<Integer> getSinglePositionInPatternCard(ArrayList<Integer> listOfAvailablePositions) throws RemoteException {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("listOfAvailablePositions", listOfAvailablePositions);
        return (ArrayList<Integer>) waitForMethodCallResponse("getSinglePositionInPatternCard", Optional.of(arguments));
    }

    @Override
    public Integer getValueForDie() throws RemoteException {
        return (Integer) waitForMethodCallResponse("getValueForDie", Optional.empty());
    }

    @Override
    public Integer askForPatternCard() throws RemoteException {
        return (Integer) waitForMethodCallResponse("askForPatternCard", Optional.empty());
    }

    @Override
    public Boolean block() throws RemoteException {
        return (Boolean) waitForMethodCallResponse("block", Optional.empty());
    }

    @Override
    public Boolean free() throws RemoteException {
        return (Boolean) waitForMethodCallResponse("free", Optional.empty());
    }


    /**
     * Sends heartbeat to the room
     * @param heartbeatMessage heartbeat message to send to the room
     */
    public void notifyHeartbeat(HeartbeatMessage heartbeatMessage) {
        roomDispatcher.heartbeat(heartbeatMessage);
    }


    /**
     * Unlocks the lock waiting for the response from a called method
     */
    public void unlockAndSetReady() {
        synchronized (lock) {
            ready = true;
            lock.notifyAll();
        }
    }


    /**
     * When a message of MethodCallMessage type is sent, the server needs to wait for its return value.
     * This is done calling this method: the name of the method to be called on the view and its arguments
     * are written into the message. After that the message is written to the client with the objectOutputStream.
     * Now this method enters in a while loop, waiting for a lock to be unlocked (see unlockAndSetReady).
     * When the lock is unlocked and variable ready is set to true, this method returns an object containing the
     * return value.
     * @param methodName String representing the method to be called on the view
     * @param args Map of the arguments to be passed to the function where the key is the name of the argument and
     *             the value is the value of the argument
     * @return an object that is the return value of the function called on the view
     */
    private Object waitForMethodCallResponse(String methodName, Optional<Map> args) {
        MethodCallMessage methodCallMessage = new MethodCallMessage(methodName);
        args.ifPresent(argumentsMap -> {
            argumentsMap.forEach((name, value) -> {
                methodCallMessage.addArgument((String)name, value);
            });
        });
        try {
            objectOutputStream.writeObject(methodCallMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        synchronized (lock) {
            while(!ready) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            ready = false;
            return ((MethodCallMessage)inputMessage).getReturnValue();
        }
    }

    @Override
    public String toString() {
        return clientSocket.getInetAddress().toString();
    }

}
