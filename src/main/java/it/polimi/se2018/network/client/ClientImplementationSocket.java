package it.polimi.se2018.network.client;

import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.events.ModelChangedMessage;
import it.polimi.se2018.model.events.PlayerMessage;
import it.polimi.se2018.model.events.PlayerMessageDie;
import it.polimi.se2018.view.ViewClient;

import java.io.*;
import java.net.Socket;

public class ClientImplementationSocket extends Thread implements ClientInterface {
    private ViewClient viewClient;
    private Socket serverConnection;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;

    public ClientImplementationSocket(ViewClient viewClient, Socket serverConnection) {
        this.viewClient = viewClient;
        this.serverConnection = serverConnection;
        try {
            this.objectInputStream = new ObjectInputStream(this.serverConnection.getInputStream());
            this.objectOutputStream = new ObjectOutputStream(this.serverConnection.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(ModelChangedMessage modelChangedMessage) {
        viewClient.update(modelChangedMessage);
    }

    public void notify(PlayerMessage playerMessage) {
        try {
            objectOutputStream.writeObject(playerMessage);
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run(){
        try {
            ModelChangedMessage modelChangedMessage;

            while((modelChangedMessage = (ModelChangedMessage) objectInputStream.readObject()) != null) {
                /* TODO: devo fare qui la trasformazione nei vari ModelChangedMessage{*}? */
                this.update(modelChangedMessage);
            }

            serverConnection.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
