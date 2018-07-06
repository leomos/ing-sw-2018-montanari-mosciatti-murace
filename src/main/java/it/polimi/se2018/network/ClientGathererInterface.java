package it.polimi.se2018.network;

/**
 * Interface used by the server to dispatch the various
 * clients that connects to the server.
 * When a client connects to the server, the gatherer creates a new
 * ClientInterface and hands it over the room dispatcher
 */
public interface ClientGathererInterface extends Runnable {

    /**
     * Sets the room dispatcher
     * @param roomDispatcher RoomDispatcher interface to be used by the gatherer
     */
    public void setRoomDispatcher(RoomDispatcherInterface roomDispatcher);

}
