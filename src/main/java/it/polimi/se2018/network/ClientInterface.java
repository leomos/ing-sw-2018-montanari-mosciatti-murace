package it.polimi.se2018.network;

import it.polimi.se2018.model.events.ModelChangedMessage;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ClientInterface extends Remote {

    public void update(ModelChangedMessage modelChangedMessage) throws RemoteException;

    public ArrayList<Integer> getDieFromRoundTrack() throws RemoteException;

    public ArrayList<Integer> getIncrementedValue() throws RemoteException;

    public ArrayList<Integer> getPositionInPatternCard() throws RemoteException;

    public ArrayList<Integer> getSinglePositionInPatternCard(ArrayList<Integer> listOfAvailablePositions) throws RemoteException;

    public Integer getValueForDie() throws RemoteException;

    public Integer askForPatternCard() throws RemoteException;

    public Integer getDieFromDiceArena() throws RemoteException;

    public Integer getDieFromPatternCard() throws RemoteException;

    public void block() throws RemoteException;

    public void free() throws RemoteException;
}
