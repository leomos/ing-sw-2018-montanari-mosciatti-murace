package it.polimi.se2018.network;

import it.polimi.se2018.model.events.PlayerMessage;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInterface<T> extends Remote {

    public void notify(PlayerMessage playerMessage) throws RemoteException;

    public Integer registerClient(T client, String name) throws RemoteException;

}
