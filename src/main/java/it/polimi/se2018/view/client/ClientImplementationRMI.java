package it.polimi.se2018.view.client;

import it.polimi.se2018.model.events.ModelChangedMessage;

import java.rmi.RemoteException;

public class ClientImplementationRMI implements ClientInterface{

    private ViewClient viewClient;

    public ClientImplementationRMI(ViewClient viewClient) throws RemoteException {
        this.viewClient = viewClient;
    }

    public void update(ModelChangedMessage modelChangedMessage) throws RemoteException{
        viewClient.update(modelChangedMessage);
    }

}
