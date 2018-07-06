package it.polimi.se2018.network;

import it.polimi.se2018.model.events.HeartbeatMessage;
import it.polimi.se2018.network.server.Room;

import java.util.Set;


/**
 * Runnable process that manages the rooms
 */
public interface RoomDispatcherInterface extends Runnable {

    /**
     * Handles a new client given by a gatherer
     * @param clientInterface interface of the client
     * @param name name of the player who wants to be registered
     * @return the id representing univocally the client/player in the server
     */
    public Integer handleClient(ClientInterface clientInterface, String name);

    /**
     * Returns the set of all the clients connected to the server
     * @return the set of all the clients connected to the server
     */
    public Set<ConnectedClient> getAllConnectedClients();

    /**
     * Notifies a specific room about a new heartbeat
     * @param heartbeatMessage message containing all the informations
     *                         to notify a new heartbeat
     */
    public void heartbeat(HeartbeatMessage heartbeatMessage);

    /**
     * Stops this process
     */
    public void stopDispatcher();

    /**
     * Returns the room where a specific client is
     * @param id integer univocally representing a client
     * @return the room where a specific client is
     */
    public Room getRoomForId(int id);

    /**
     * Asks to the respective room to reconnect an already connected player
     * @param clientInterface interface of the client
     * @param id integer given to the client during the call to registerClient
     * @return true if the client reconnected successfully, false if not
     */
    public Boolean reconnectClient(ClientInterface clientInterface, int id);

    /**
     * Sends all the information needed for a reconnected client to start
     * again to play in a room
     * @param id
     */
    public void sendGameStateToReconnectedClient(int id);

}
