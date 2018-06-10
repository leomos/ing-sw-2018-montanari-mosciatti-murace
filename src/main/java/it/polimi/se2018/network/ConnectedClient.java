package it.polimi.se2018.network;

import it.polimi.se2018.network.server.Room;

public class ConnectedClient {

    private int id;

    private String name;

    private ClientInterface clientInterface;

    private Room room;

    public ConnectedClient(int id, String name, ClientInterface clientInterface) {
        this.id = id;
        this.name = name;
        this.clientInterface = clientInterface;
    }

    public void setClientInterface(ClientInterface clientInterface) {
        this.clientInterface = clientInterface;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ClientInterface getClientInterface() {
        return clientInterface;
    }

    public Room getRoom() {
        return room;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (!ConnectedClient.class.isAssignableFrom(object.getClass())) {
            return false;
        }
        final ConnectedClient obj = (ConnectedClient) object;
        return this.id == obj.id;
    }

    @Override
    public String toString() {
        return "[" + id + "," + name + "," + clientInterface.getClass() + "]";
    }
}
