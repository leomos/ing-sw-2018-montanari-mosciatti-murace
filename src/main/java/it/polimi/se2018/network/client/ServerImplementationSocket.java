package it.polimi.se2018.network.client;

import it.polimi.se2018.model.events.*;
import it.polimi.se2018.network.ServerInterface;
import it.polimi.se2018.network.visitor.MessageVisitorImplementationServer;
import it.polimi.se2018.network.visitor.MessageVisitorInterface;
import it.polimi.se2018.view.ViewClient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;

public class ServerImplementationSocket extends Thread implements ServerInterface<Socket> {

    private ObjectInputStream objectInputStream;

    private ObjectOutputStream objectOutputStream;

    private Message inputMessage;

    private MessageVisitorInterface messageVisitorInterface = new MessageVisitorImplementationServer(this);

    private ViewClient viewClient;

    public ServerImplementationSocket(ViewClient viewClient) {
        this.viewClient = viewClient;
    }

    @Override
    public void notify(PlayerMessage playerMessage) throws RemoteException {
        send(playerMessage);
    }

    @Override
    public Integer registerClient(Socket client, String name) throws RemoteException {
        MethodCallMessage methodCallMessage;
        try {
            this.objectOutputStream = new ObjectOutputStream(client.getOutputStream());
            methodCallMessage = new MethodCallMessage("registerNewClient");
            methodCallMessage.addArgument("name", name);
            this.objectOutputStream.writeObject(methodCallMessage);

            this.objectInputStream = new ObjectInputStream(client.getInputStream());
            methodCallMessage = (MethodCallMessage)this.objectInputStream.readObject();
            this.start();
            return (Integer)methodCallMessage.getReturnValue();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void sendHeartbeat(HeartbeatMessage heartbeatMessage) {
        send(heartbeatMessage);
    }

    @Override
    public Boolean reconnect(Socket client, Integer id) throws RemoteException {
        MethodCallMessage methodCallMessage;
        try {
            this.objectOutputStream = new ObjectOutputStream(client.getOutputStream());
            methodCallMessage = new MethodCallMessage("reconnectClient");
            methodCallMessage.addArgument("id", id);
            this.objectOutputStream.writeObject(methodCallMessage);

            this.objectInputStream = new ObjectInputStream(client.getInputStream());
            methodCallMessage = (MethodCallMessage)this.objectInputStream.readObject();
            this.start();
            return (Boolean) methodCallMessage.getReturnValue();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
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

    public ViewClient getViewClient() {
        return viewClient;
    }

    public void updateView(Message message) {
        Runnable task = () -> {
            viewClient.update((ModelChangedMessage) message);
        };
        Thread thread = new Thread(task);
        thread.start();
    }

    public void writeMessage(Message message) {
        send(message);
    }

    private void send(Object object) {
        try {
            this.objectOutputStream.writeObject(object);
        } catch (IOException e) {
            viewClient.handleDisconnection();
        }
    }

}
