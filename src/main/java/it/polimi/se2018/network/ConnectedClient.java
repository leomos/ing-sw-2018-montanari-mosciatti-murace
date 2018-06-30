package it.polimi.se2018.network;

public class ConnectedClient {

    private int id;

    private String name;

    private ClientInterface clientInterface;

    //private Room room;

    private boolean isInactive;

    public ConnectedClient(int id, String name, ClientInterface clientInterface) {
        this.id = id;
        this.name = name;
        this.clientInterface = clientInterface;
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

    public void setClientInterface(ClientInterface clientInterface) {
        this.clientInterface = clientInterface;
    }

    public void setInactive(boolean inactive) {
        this.isInactive = inactive;
    }

    public boolean isInactive() {
        return isInactive;
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
