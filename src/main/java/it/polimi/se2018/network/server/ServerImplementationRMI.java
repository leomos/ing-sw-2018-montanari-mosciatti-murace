package it.polimi.se2018.network.server;

import it.polimi.se2018.model.events.PlayerMessage;
import it.polimi.se2018.network.ClientInterface;
import it.polimi.se2018.network.ServerInterface;
import it.polimi.se2018.view.VirtualView;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServerImplementationRMI extends UnicastRemoteObject implements ServerInterface {

    private VirtualView virtualView;

    public ServerImplementationRMI(VirtualView virtualView) throws RemoteException {
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
