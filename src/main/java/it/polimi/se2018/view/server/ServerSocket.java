package it.polimi.se2018.view.server;

import it.polimi.se2018.model.events.ModelChangedMessage;
import it.polimi.se2018.model.events.PlayerMessage;
import it.polimi.se2018.view.client.ClientInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.rmi.RemoteException;

public class ServerSocket extends Thread implements ServerInterface, ClientInterface {
    private VirtualView virtualView;

    private Socket clientConnection;

    public ServerSocket(VirtualView virtualView, Socket clientConnection) {
        this.virtualView = virtualView;
        this.clientConnection = clientConnection;
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
        //faccio la send
        OutputStreamWriter writer;

        try {
            writer = new OutputStreamWriter(this.clientConnection.getOutputStream());
            writer.write(modelChangedMessage.toString());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run(){

        try {

            BufferedReader is = new BufferedReader(new InputStreamReader(clientConnection.getInputStream()));

            boolean loop = true;

            while(loop) {
                String message = is.readLine();
                if ( message == null ) {
                    loop = false;
                } else {
                    System.out.println("ServerSocket: " + message);
                }

            }

            clientConnection.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
