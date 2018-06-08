package it.polimi.se2018.network.client;

import it.polimi.se2018.model.events.PlayerMessage;
import it.polimi.se2018.model.events.PlayerMessageSetup;
import it.polimi.se2018.network.ServerInterface;

import java.io.IOException;
import java.net.Socket;

public class Client {

    public static void main(String[] args) {
        Socket socket;
        ServerInterface serverInterface = new ServerImplementationSocket();
        int id;
        try {
            socket = new Socket("localhost", 1111);
            id = serverInterface.registerClient(socket, "piero");
            System.out.println(id);
            ((ServerImplementationSocket)serverInterface).start();

            PlayerMessage playerMessage = new PlayerMessageSetup(0,0);
            serverInterface.notify(playerMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
