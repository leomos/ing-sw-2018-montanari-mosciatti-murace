package it.polimi.se2018.network.client;

import it.polimi.se2018.model.events.MethodCallMessage;
import it.polimi.se2018.model.events.PlayerMessage;
import it.polimi.se2018.network.ServerInterface;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;

public class ServerImplementationSocket extends Thread implements ServerInterface {

    private ObjectInputStream objectInputStream;

    private ObjectOutputStream objectOutputStream;

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
        MethodCallMessage mc;
        try {
            while (null != (mc = (MethodCallMessage) objectInputStream.readObject())) {
                System.out.println(mc.getMethodName());
                mc = new MethodCallMessage("getDieFromPatternCard", 3);
                objectOutputStream.writeObject(mc);
            }
        } catch (IOException e) {

        } catch (ClassNotFoundException e) {

        }
    }
}
