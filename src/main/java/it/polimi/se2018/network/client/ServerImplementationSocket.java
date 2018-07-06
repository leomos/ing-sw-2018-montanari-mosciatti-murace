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

/**
 * @see it.polimi.se2018.network.ServerInterface
 */
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
        Object received;
        try {
            this.objectOutputStream = new ObjectOutputStream(client.getOutputStream());
            methodCallMessage = new MethodCallMessage("reconnectClient");
            methodCallMessage.addArgument("id", id);
            this.objectOutputStream.writeObject(methodCallMessage);

            this.objectInputStream = new ObjectInputStream(client.getInputStream());
            while((received = this.objectInputStream.readObject()) != null) {
                if(received instanceof MethodCallMessage) {
                    methodCallMessage = (MethodCallMessage) received;
                    this.start();

                    return (Boolean) methodCallMessage.getReturnValue();
                }
            }
            /*methodCallMessage = (MethodCallMessage) this.objectInputStream.readObject();

            Boolean returnValue = (Boolean) methodCallMessage.getReturnValue();

            if(returnValue) {
                methodCallMessage = new MethodCallMessage("reconnectClientSendUpdates");
                this.objectOutputStream.writeObject(methodCallMessage);
                this.start();
            }*/
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

    /**
     * Returns the viewClient used by this implementation
     * @return the viewClient used by this implementation
     */
    public ViewClient getViewClient() {
        return viewClient;
    }


    /**
     * Updates the view with a message
     * @param message message to pass to the view
     */
    public synchronized void updateView(Message message) {
        viewClient.update((ModelChangedMessage) message);
    }

    /**
     * Writes a message to the output stream
     * @param message message to be written
     */
    public void writeMessage(Message message) {
        send(message);
    }

    /**
     * Writes an object to the output stream
     * @param object object to be written
     */
    private void send(Object object) {
        try {
            this.objectOutputStream.writeObject(object);
        } catch (IOException e) {
            viewClient.handleDisconnection();
        }
    }

}
