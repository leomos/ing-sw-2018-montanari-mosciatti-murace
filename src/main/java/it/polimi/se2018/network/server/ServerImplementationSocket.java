package it.polimi.se2018.network.server;

import it.polimi.se2018.model.events.ModelChangedMessage;
import it.polimi.se2018.model.events.PlayerMessage;
import it.polimi.se2018.network.ControllerActionEnum;
import it.polimi.se2018.network.SocketMessage;
import it.polimi.se2018.network.client.ClientInterface;
import it.polimi.se2018.view.VirtualView;

import java.io.*;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Logger;

public class ServerImplementationSocket extends Thread implements ServerInterface, ClientInterface {
    private static final Logger LOGGER = Logger.getLogger( ServerImplementationSocket.class.getName() );
    private VirtualView virtualView;
    private Socket clientConnection;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;

    private Object controllerActionResult;

    private boolean lock = true;

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
        virtualView.callNotify(playerMessage);
    }

    @Override
    public void addClient(ClientInterface clientInterface) {
        virtualView.addClient(this);
    }

    @Override
    public void update(ModelChangedMessage modelChangedMessage) throws RemoteException {
        SocketMessage<ModelChangedMessage> socketMessage = new SocketMessage<>(modelChangedMessage);
        try {
            objectOutputStream.writeObject(socketMessage);
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private synchronized void waitForLock() {
        while(lock) {
            try {
                wait(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        lock = true;
    }

    private void sendAction(ControllerActionEnum controllerActionEnum) {
        SocketMessage<ControllerActionEnum> socketMessage = new SocketMessage<>(controllerActionEnum);
        try {
            objectOutputStream.writeObject(socketMessage);
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getDieFromPatternCard() throws RemoteException {
        sendAction(ControllerActionEnum.GET_DIE_FROM_PATTERNCARD);
        waitForLock();
        return (int)controllerActionResult;
    }

    public int getDieFromRoundTrack() {
        sendAction(ControllerActionEnum.GET_DIE_FROM_ROUNDTRACK);
        waitForLock();
        return (int)controllerActionResult;
    }

    public boolean getIncrementedValue() throws RemoteException {
        sendAction(ControllerActionEnum.GET_INCREMENTED_VALUE);
        waitForLock();
        return (boolean)controllerActionResult;
    }

    public Integer[] getPositionInPatternCard() {
        sendAction(ControllerActionEnum.GET_POSITION_IN_PATTERNCARD);
        waitForLock();
        /* TODO: transform from Integer[] to int[] */
        return (Integer[])controllerActionResult;
    }

    public int getValueForDie() {
        sendAction(ControllerActionEnum.GET_VALUE_FOR_DIE);
        waitForLock();
        return (int)controllerActionResult;
    }

    @Override
    public String askForName() throws RemoteException {
        return null;
    }

    @Override
    public ArrayList<Integer> askForPatternCard(){
        return null;
    }


    @Override
    public void run(){
        try {
            SocketMessage<?> inputMessage;

            while((inputMessage = (SocketMessage<?>) objectInputStream.readObject()) != null) {
                if (inputMessage.isPlayerMessage()) {
                    this.notify((PlayerMessage) inputMessage.getObject());
                } else if(inputMessage.isControllerActionResponse()){
                    controllerActionResult = inputMessage.getObject();
                    lock = false;
                }
            }
            clientConnection.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
