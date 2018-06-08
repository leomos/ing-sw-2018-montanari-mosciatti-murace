package it.polimi.se2018.network;

public interface RoomDispatcherInterface extends Runnable {

    public Integer handleClient(ClientInterface clientInterface, String name);

    public void stopDispatcher();

}
