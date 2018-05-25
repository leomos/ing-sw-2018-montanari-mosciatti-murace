package it.polimi.se2018.view.server;

import it.polimi.se2018.model.events.PlayerMessage;
import it.polimi.se2018.view.client.ClientInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VirtualViewInterface extends Remote {

    public void notify(PlayerMessage playerMessage) throws RemoteException;

    public void addClient(ClientInterface clientInterface) throws RemoteException;

}
