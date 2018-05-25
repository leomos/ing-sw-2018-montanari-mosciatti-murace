package it.polimi.se2018.view.server;

import it.polimi.se2018.model.events.PlayerMessage;
import it.polimi.se2018.view.client.ClientInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class VirtualViewRMI extends UnicastRemoteObject implements VirtualViewInterface {

    private VirtualView virtualView;

    public VirtualViewRMI(VirtualView virtualView) throws RemoteException {
        super(0);
        this.virtualView = virtualView;
    }

    public void notify(PlayerMessage playerMessage) throws RemoteException{
        virtualView.notify(playerMessage);
    }

    public void addClient(ClientInterface clientInterface) throws RemoteException{
        virtualView.addClient(clientInterface);
    }

    //addVirtualView
}
