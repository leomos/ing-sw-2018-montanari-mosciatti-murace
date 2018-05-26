package it.polimi.se2018.network.server;

import it.polimi.se2018.model.events.PlayerMessage;
import it.polimi.se2018.network.client.ClientInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInterface extends Remote {

    public void notify(PlayerMessage playerMessage) throws RemoteException;

    public void addClient(ClientInterface clientInterface) throws RemoteException;

}
