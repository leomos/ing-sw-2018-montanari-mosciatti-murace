package it.polimi.se2018.network.client;

import it.polimi.se2018.model.events.Message;
import it.polimi.se2018.model.events.MethodCallMessage;
import it.polimi.se2018.model.events.ModelChangedMessage;
import it.polimi.se2018.model.events.PlayerMessage;
import it.polimi.se2018.network.ServerInterface;
import it.polimi.se2018.network.visitor.MessageVisitorImplementationServer;
import it.polimi.se2018.network.visitor.MessageVisitorInterface;
import it.polimi.se2018.view.ViewClient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;

public class ServerImplementationSocket extends Thread implements ServerInterface {

    private ObjectInputStream objectInputStream;

    private ObjectOutputStream objectOutputStream;

    private Message inputMessage;

    private MessageVisitorInterface messageVisitorInterface = new MessageVisitorImplementationServer(this);

    private ViewClient viewClient;


    /*
    public ServerImplementationSocket(ViewClient viewClient) {
        this.viewClient = viewClient;
    }*/

    @Override
    public void notify(PlayerMessage playerMessage) throws RemoteException {
        try {
            this.objectOutputStream.writeObject(playerMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Integer registerClient(Object client, String name) throws RemoteException {
        Socket socket = (Socket) client;
        MethodCallMessage methodCallMessage;
        try {
            this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            methodCallMessage = new MethodCallMessage("registerNewClient");
            methodCallMessage.addArgument("name", name);
            this.objectOutputStream.writeObject(methodCallMessage);

            this.objectInputStream = new ObjectInputStream(socket.getInputStream());
            methodCallMessage = (MethodCallMessage)this.objectInputStream.readObject();
            return (Integer)methodCallMessage.getReturnValue();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
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
        viewClient.update((ModelChangedMessage) message);
    }

    public void writeMessage(Message message) {
        try {
            this.objectOutputStream.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
