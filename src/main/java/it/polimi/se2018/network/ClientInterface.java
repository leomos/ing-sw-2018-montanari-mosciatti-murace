package it.polimi.se2018.network;

import it.polimi.se2018.model.events.ModelChangedMessage;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * An interface used by the server to communicate with the client
 */
public interface ClientInterface extends Remote {


    /**
     * Sends an update message to the client
     * @param modelChangedMessage message to be sent
     * @throws RemoteException
     */
    public void update(ModelChangedMessage modelChangedMessage) throws RemoteException;

    /**
     * Ask the client for a die from the roundtrack
     * @return
     * @throws RemoteException
     */
    public ArrayList<Integer> getDieFromRoundTrack() throws RemoteException;

    /**
     * @return
     * @throws RemoteException
     */
    public ArrayList<Integer> getIncrementedValue() throws RemoteException;

    /**
     * @return
     * @throws RemoteException
     */
    public ArrayList<Integer> getPositionInPatternCard() throws RemoteException;

    /**
     * @param listOfAvailablePositions
     * @return
     * @throws RemoteException
     */
    public ArrayList<Integer> getSinglePositionInPatternCard(ArrayList<Integer> listOfAvailablePositions) throws RemoteException;

    /**
     * @return
     * @throws RemoteException
     */
    public Integer getValueForDie() throws RemoteException;

    /**
     * @return
     * @throws RemoteException
     */
    public Integer askForPatternCard() throws RemoteException;

    /**
     * @return
     * @throws RemoteException
     */
    public Integer getDieFromDiceArena() throws RemoteException;

    /**
     * @return
     * @throws RemoteException
     */
    public ArrayList<Integer> getDoublePositionInPatternCard() throws RemoteException;

    /**
     * @return
     * @throws RemoteException
     */
    public Integer getDieFromPatternCard() throws RemoteException;

    /**
     * @return
     * @throws RemoteException
     */
    public Boolean block() throws RemoteException;

    /**
     * @return
     * @throws RemoteException
     */
    public Boolean free() throws RemoteException;
}
