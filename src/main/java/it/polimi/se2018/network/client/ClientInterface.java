package it.polimi.se2018.network.client;

import it.polimi.se2018.model.events.ModelChangedMessage;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInterface extends Remote {

    public void update(ModelChangedMessage modelChangedMessage) throws RemoteException;
}
