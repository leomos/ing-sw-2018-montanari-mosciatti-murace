package it.polimi.se2018.network.server;

import it.polimi.se2018.model.events.ModelChangedMessage;
import it.polimi.se2018.model.events.PlayerMessage;
import it.polimi.se2018.network.client.ClientInterface;
import it.polimi.se2018.view.VirtualView;

import java.io.*;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.logging.Logger;

public class ServerImplementationSocket extends Thread implements ServerInterface, ClientInterface {
    private static final Logger LOGGER = Logger.getLogger( ServerImplementationSocket.class.getName() );
    private VirtualView virtualView;
    private Socket clientConnection;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;

    public ServerImplementationSocket(VirtualView virtualView, Socket clientConnection) {
        this.virtualView = virtualView;
        this.clientConnection = clientConnection;
        try {
            this.objectOutputStream = new ObjectOutputStream(this.clientConnection.getOutputStream());
            this.objectInputStream = new ObjectInputStream(this.clientConnection.getInputStream());
        } catch (IOException e) {
            LOGGER.severe(e.getMessage());
        }
    }

    @Override
    public void notify(PlayerMessage playerMessage) {
        virtualView.notify(playerMessage);
    }

    @Override
    public void addClient(ClientInterface clientInterface) {
        virtualView.addClient(this);
    }

    @Override
    public void update(ModelChangedMessage modelChangedMessage) throws RemoteException {

        try {
            objectOutputStream.writeObject(modelChangedMessage);
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run(){

        try {
            PlayerMessage playerMessage;

            while((playerMessage = (PlayerMessage) objectInputStream.readObject()) != null) {
                /* TODO: devo fare qui la trasformazione nei vari PlayerMessage{*}? */
                this.notify(playerMessage);
            }

            clientConnection.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }


}
