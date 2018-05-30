package it.polimi.se2018.network.client;

import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.events.ModelChangedMessage;
import it.polimi.se2018.model.events.PlayerMessage;
import it.polimi.se2018.model.events.PlayerMessageDie;
import it.polimi.se2018.network.ControllerActionEnum;
import it.polimi.se2018.network.SocketMessage;
import it.polimi.se2018.view.ViewClient;

import java.io.*;
import java.net.Socket;
import java.rmi.RemoteException;

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

    @Override
    public int getDieFromPatternCard() throws RemoteException {
        return 0;
    }

    public void notify(PlayerMessage playerMessage) {
        SocketMessage<PlayerMessage> socketMessage = new SocketMessage<>(playerMessage);
        try {
            objectOutputStream.writeObject(socketMessage);
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run(){
        try {
            SocketMessage<?> inputMessage;

            while((inputMessage = (SocketMessage<?>) objectInputStream.readObject()) != null) {
                if(inputMessage.isModelChangedMessage()) {
                    this.update((ModelChangedMessage)inputMessage.getObject());
                } else if(inputMessage.isControllerActionEnum()) {
                    SocketMessage<?> responseMessage = null;
                    ControllerActionEnum controllerActionEnum = (ControllerActionEnum)inputMessage.getObject();
                    switch (controllerActionEnum) {
                        case GET_VALUE_FOR_DIE:
                            //responseMessage = new SocketMessage<Integer>(viewClient.getValueForDie(), true);
                            break;
                        case GET_INCREMENTED_VALUE:
                            //responseMessage = new SocketMessage<Boolean>(viewClient.getIncrementedValue(), true);
                            break;
                        case GET_DIE_FROM_ROUNDTRACK:
                            //responseMessage = new SocketMessage<Integer>(viewClient.getDieFromRoundTrack(), true);
                            break;
                        case GET_DIE_FROM_PATTERNCARD:
                            responseMessage = new SocketMessage<Integer>(getDieFromPatternCard(), true);
                            break;
                        case GET_POSITION_IN_PATTERNCARD:
                            //responseMessage = new SocketMessage<Integer[]>(viewClient.getPositionInPatternCard(), true);
                            break;
                        default:
                            break;
                    }
                    objectOutputStream.writeObject(responseMessage);
                    objectOutputStream.flush();
                }
            }

            serverConnection.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
