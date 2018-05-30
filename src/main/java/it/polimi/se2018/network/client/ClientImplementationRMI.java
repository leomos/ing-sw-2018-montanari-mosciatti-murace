package it.polimi.se2018.network.client;

import it.polimi.se2018.model.events.ModelChangedMessage;
import it.polimi.se2018.view.ViewClient;

import java.rmi.RemoteException;

public class ClientImplementationRMI implements ClientInterface{

    private ViewClient viewClient;

    public ClientImplementationRMI(ViewClient viewClient) throws RemoteException {
        this.viewClient = viewClient;
    }

    @Override
    public void update(ModelChangedMessage modelChangedMessage) throws RemoteException{
        viewClient.update(modelChangedMessage);
    }

    @Override
    public int getDieFromPatternCard() throws RemoteException {
        return viewClient.getDieFromPatternCard();
    }

}
