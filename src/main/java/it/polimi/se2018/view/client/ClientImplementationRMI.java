package it.polimi.se2018.view.client;

import it.polimi.se2018.model.events.ModelChangedMessage;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientImplementationRMI extends UnicastRemoteObject implements ClientInterface{

    private ViewClient viewClient;

    public ClientImplementationRMI() throws RemoteException {
        super(0);
    }

    public void update(ModelChangedMessage modelChangedMessage){
        viewClient.update(modelChangedMessage);
    }

}
