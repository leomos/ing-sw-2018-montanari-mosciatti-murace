package it.polimi.se2018.view.server;

import it.polimi.se2018.model.events.PlayerMessage;
import it.polimi.se2018.view.client.ClientInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class VirtualViewRMI extends UnicastRemoteObject implements VirtualViewInterface {

    private VirtualView virtualView;

    public VirtualViewRMI() throws RemoteException {
        super(0);
    }

    public void notify(PlayerMessage playerMessage){
        virtualView.notify(playerMessage);
    }

    public void addClient(ClientInterface clientInterface){
        virtualView.addClient(clientInterface);
    }

    //addVirtualView
}
