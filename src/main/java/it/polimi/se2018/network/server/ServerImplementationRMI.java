package it.polimi.se2018.network.server;

import it.polimi.se2018.model.events.HeartbeatMessage;
import it.polimi.se2018.model.events.PlayerMessage;
import it.polimi.se2018.network.*;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Implementation of ServerInterface and ClientGathererInterface for clients connecting with RMI.
 * This object will be passed as a remote reference to the client
 * @see it.polimi.se2018.network.ClientGathererInterface
 * @see it.polimi.se2018.network.ServerInterface
 */
public class ServerImplementationRMI extends UnicastRemoteObject implements ClientGathererInterface,
                                                                            ServerInterface<ClientInterface> {

    private RoomDispatcherInterface roomDispatcher;

    private int port;

    private String host;

    private String serverName;


    protected ServerImplementationRMI(int port, String host, String serverName) throws RemoteException {
        this.port = port;
        this.host = host;
        this.serverName = serverName;
    }

    @Override
    public void notify(PlayerMessage playerMessage) throws RemoteException {

        Runnable task = () -> {
            roomDispatcher.getRoomForId(playerMessage.getPlayerId()).notifyView(playerMessage);
        };
        Thread t = new Thread(task);
        t.start();
    }

    @Override
    public Integer registerClient(ClientInterface client, String name) throws RemoteException {

        return roomDispatcher.handleClient(client, name);
    }

    @Override
    public void sendHeartbeat(HeartbeatMessage heartbeatMessage) {
        roomDispatcher.heartbeat(heartbeatMessage);
    }

    @Override
    public Boolean reconnect(ClientInterface clientInterface, Integer id) throws RemoteException {
        return roomDispatcher.reconnectClient(clientInterface, id);
    }

    @Override
    public void setRoomDispatcher(RoomDispatcherInterface roomDispatcher) {
        this.roomDispatcher = roomDispatcher;
    }


    /**
     * Creates the RMI registry, and bind this object to the specified url.
     */
    @Override
    public void run() {

        try {
            LocateRegistry.createRegistry(this.port);
        } catch (RemoteException e) {
            System.out.println("Registry already running!");
        }

        try {
            Naming.rebind("rmi://"+this.host+":"+this.port+"/"+this.serverName, (ServerInterface)this);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        System.out.println("RMI started.");
    }
}
