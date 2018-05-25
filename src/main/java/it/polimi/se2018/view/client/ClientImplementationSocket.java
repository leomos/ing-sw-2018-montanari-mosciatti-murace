package it.polimi.se2018.view.client;

import it.polimi.se2018.model.events.ModelChangedMessage;

import java.net.Socket;

public class ClientImplementationSocket extends Thread implements ClientInterface {
    private ViewClient viewClient;

    private Socket socket;

    public ClientImplementationSocket(ViewClient viewClient, Socket socket) {
        this.viewClient = viewClient;
        this.socket = socket;
    }

    @Override
    public void update(ModelChangedMessage modelChangedMessage) {
        viewClient.update(modelChangedMessage);
    }


}
