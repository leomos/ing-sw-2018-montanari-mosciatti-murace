package it.polimi.se2018.network;

import it.polimi.se2018.model.events.HeartbeatMessage;
import it.polimi.se2018.model.events.PlayerMessage;

import java.rmi.Remote;
import java.rmi.RemoteException;


/**
 * A representation of the server to be utilized by the clients.
 * @param <T> type of the client with which the server will communicate
 */
public interface ServerInterface<T> extends Remote {

    /**
     * Notify the real server
     * @param playerMessage message which will be notified by the server
     * @throws RemoteException
     */
    public void notify(PlayerMessage playerMessage) throws RemoteException;

    /**
     * Asks to the real server to register a new client
     * @param client interface of the client
     * @param name name of the player who wants to be registered
     * @return the id representing univocally the client/player in the server
     * @throws RemoteException
     */
    public Integer registerClient(T client, String name) throws RemoteException;

    /**
     * Send heartbeat to real server
     * @param heartbeatMessage message describing the heartbeat sent
     * @throws RemoteException
     */
    public void sendHeartbeat(HeartbeatMessage heartbeatMessage) throws RemoteException;

    /**
     * Asks to the real server to reconnect an already connected player
     * @param client interface of the client
     * @param id integer given to the client during the call to registerClient
     * @return true if the client reconnected successfully, false if not
     * @throws RemoteException
     */
    public Boolean reconnect(T client, Integer id) throws RemoteException;

}
