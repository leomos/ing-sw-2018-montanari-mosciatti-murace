package it.polimi.se2018.network.server;

import it.polimi.se2018.model.events.Message;
import it.polimi.se2018.model.events.MethodCallMessage;
import it.polimi.se2018.model.events.ModelChangedMessage;
import it.polimi.se2018.network.ClientInterface;
import it.polimi.se2018.network.visitor.MessageVisitorImplementationClient;
import it.polimi.se2018.network.visitor.MessageVisitorInterface;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.List;

public class ClientImplementationSocket extends Thread implements ClientInterface {

    private Socket clientSocket;

    private ObjectInputStream objectInputStream;

    private ObjectOutputStream objectOutputStream;

    private Room room;

    private boolean waitingForMethodCallResponse = false, ready = false;

    private Object lock = new Object();

    private Message inputMessage;

    private MessageVisitorInterface messageVisitorInterface = new MessageVisitorImplementationClient(this);


    public ClientImplementationSocket(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        this.objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
        this.objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
    }

    public ClientImplementationSocket(ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream) throws IOException {
        this.objectInputStream = objectInputStream;
        this.objectOutputStream = objectOutputStream;
    }

    @Override
    public void run() {
        try {
            while (null != (inputMessage = (Message) objectInputStream.readObject())) {
                inputMessage.accept(messageVisitorInterface);
            }
        } catch (IOException e) {

        } catch (ClassNotFoundException e) {

        }
    }

    public void notifyRoom(Message message) {
        room.notifyView(message);
    }

    @Override
    public void setRoom(Room room) throws RemoteException {
        this.room = room;
        this.start();
    }

    @Override
    public void update(ModelChangedMessage modelChangedMessage) throws RemoteException {

    }

    @Override
    public Integer getDieFromPatternCard() throws RemoteException {
        return (Integer) waitForMethodCallResponse("getDieFromPatternCard");
    }

    @Override
    public Integer getDieFromRoundTrack() throws RemoteException {
        return (Integer) waitForMethodCallResponse("getDieFromRoundTrack");
    }

    @Override
    public Boolean getIncrementedValue() throws RemoteException {
        return (Boolean) waitForMethodCallResponse("getIncrementedValue");
    }

    @Override
    public Integer[] getPositionInPatternCard() throws RemoteException {
        return (Integer[]) waitForMethodCallResponse("getPositionInPatternCard");
    }

    @Override
    public Integer getValueForDie() throws RemoteException {
        return (Integer) waitForMethodCallResponse("getValueForDie");
    }

    @Override
    public List<Integer> askForPatternCard() throws RemoteException {
        return (List<Integer>) waitForMethodCallResponse("askForPatternCard");
    }

    public void unlockAndSetReady() {
        synchronized (lock) {
            ready = true;
            lock.notifyAll();
        }
    }

    private Object waitForMethodCallResponse(String methodName) {
        waitingForMethodCallResponse = true;
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


}
