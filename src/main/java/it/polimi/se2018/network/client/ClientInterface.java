package it.polimi.se2018.network.client;

import it.polimi.se2018.model.events.ModelChangedMessage;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ClientInterface extends Remote {

    public void update(ModelChangedMessage modelChangedMessage) throws RemoteException;


    public int getDieFromPatternCard() throws RemoteException;

    public int getDieFromRoundTrack() throws RemoteException;

    public boolean getIncrementedValue() throws RemoteException;

    public Integer[] getPositionInPatternCard() throws RemoteException;

    public int getValueForDie() throws RemoteException;

    public String askForName() throws RemoteException;

    public ArrayList<Integer> askForPatternCard() throws RemoteException;
}
