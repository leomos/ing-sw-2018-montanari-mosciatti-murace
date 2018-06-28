package it.polimi.se2018.network;

import it.polimi.se2018.model.events.HeartbeatMessage;
import it.polimi.se2018.network.server.Room;

import java.util.Set;

public interface RoomDispatcherInterface extends Runnable {

    public Integer handleClient(ClientInterface clientInterface, String name);

    public Set<ConnectedClient> getAllConnectedClients();

    public void heartbeat(HeartbeatMessage heartbeatMessage);

    public void stopDispatcher();

    public Room getRoomForId(int id);

}
