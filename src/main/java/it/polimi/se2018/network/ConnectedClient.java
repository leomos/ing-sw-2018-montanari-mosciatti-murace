package it.polimi.se2018.network;

/**
 * A client that is connected to the server
 */
public class ConnectedClient {


    /**
     * Integer representing the client *univocally* in the server
     */
    private int id;

    /**
     * The name choosen by the player
     */
    private String name;

    /**
     * The interface where the client wants to communicate
     */
    private ClientInterface clientInterface;

    /**
     * False if the client is currently, true otherwise
     */
    private boolean isInactive;


    /**
     * Constructs a ConnectedClient with specified id, name and clientInterface
     * @param id the id
     * @param name the name
     * @param clientInterface the interface
     */
    public ConnectedClient(int id, String name, ClientInterface clientInterface) {
        this.id = id;
        this.name = name;
        this.clientInterface = clientInterface;
    }


    /**
     * Returns the id
     * @return the id
     */
    public int getId() {
        return id;
    }


    /**
     * Returns the name
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the client interface
     * @return the client interface
     */
    public ClientInterface getClientInterface() {
        return clientInterface;
    }

    /**
     * Sets the client interface
     * @param clientInterface client interface to be set
     */
    public void setClientInterface(ClientInterface clientInterface) {
        this.clientInterface = clientInterface;
    }

    /**
     * Sets isInactive
     * @param inactive value to set isInactive to
     */
    public void setInactive(boolean inactive) {
        this.isInactive = inactive;
    }

    /**
     * Returns isInactive
     * @return isInactive
     */
    public boolean isInactive() {
        return isInactive;
    }


    /**
     * Two ConnectedClients are considered equals if their ids are the same
     * @param object client to compare
     * @return true if they're equalse, false otherwise
     */
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
