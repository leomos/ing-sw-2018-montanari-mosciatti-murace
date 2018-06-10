package it.polimi.se2018.network;

import java.util.Set;

public interface RoomDispatcherInterface extends Runnable {

    public Integer handleClient(ClientInterface clientInterface, String name);

    public Set<ConnectedClient> getAllConnectedClients();

    public void stopDispatcher();

}
