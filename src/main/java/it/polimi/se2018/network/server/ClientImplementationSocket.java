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

public class ClientImplementationSocket extends Thread implements ClientInterface {

    private Socket clientSocket;

    private ObjectInputStream objectInputStream;

    private ObjectOutputStream objectOutputStream;

    private Room room = null;

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

    public void notifyRoom(PlayerMessage playerMessage) {
        if(room == null) {
            setCurrentRoom(playerMessage.getPlayerId());
        }
        room.notifyView(playerMessage);
    }

    @Override
    public void update(ModelChangedMessage modelChangedMessage) throws RemoteException {
        try {
            this.objectOutputStream.writeObject(modelChangedMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Integer getDieFromPatternCard() throws RemoteException {
        return (Integer) waitForMethodCallResponse("getDieFromPatternCard");
    }

    @Override
    public ArrayList<Integer> getDieFromRoundTrack() throws RemoteException {
        return (ArrayList<Integer>) waitForMethodCallResponse("getDieFromRoundTrack");
    }

    @Override
    public Integer getDieFromDiceArena() throws RemoteException {
        return (Integer) waitForMethodCallResponse("getDieFromDiceArena");
    }

    @Override
    public ArrayList<Integer> getIncrementedValue() throws RemoteException {
        return (ArrayList<Integer>) waitForMethodCallResponse("getIncrementedValue");
    }

    @Override
    public ArrayList<Integer> getPositionInPatternCard() throws RemoteException {
        return (ArrayList<Integer>) waitForMethodCallResponse("getPositionInPatternCard");
    }

    @Override
    public ArrayList<Integer> getSinglePositionInPatternCard(ArrayList<Integer> listOfAvailablePositions) throws RemoteException {
        return (ArrayList<Integer>) waitForMethodCallResponse("getSinglePositionInPatternCard");
    }

    @Override
    public Integer getValueForDie() throws RemoteException {
        return (Integer) waitForMethodCallResponse("getValueForDie");
    }

    @Override
    public Integer askForPatternCard() throws RemoteException {
        return (Integer) waitForMethodCallResponse("askForPatternCard");
    }

    @Override
    public void block() throws RemoteException {
        waitForMethodCallResponse("block");
    }

    @Override
    public void free() throws RemoteException {
        waitForMethodCallResponse("free");
    }


    public void notifyHeartbeat(HeartbeatMessage heartbeatMessage) {
        roomDispatcher.heartbeat(heartbeatMessage);
    }

    public void unlockAndSetReady() {
        synchronized (lock) {
            ready = true;
            lock.notifyAll();
        }
    }

    private Object waitForMethodCallResponse(String methodName) {
        MethodCallMessage methodCallMessage = new MethodCallMessage(methodName);
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

    private void setCurrentRoom(int id) {
        roomDispatcher.getAllConnectedClients().forEach(client -> {
            if (client.getId() == id) {
                room = client.getRoom();
            }
        });
    }


}
