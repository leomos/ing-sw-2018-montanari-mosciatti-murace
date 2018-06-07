package it.polimi.se2018.network.client;

import it.polimi.se2018.model.events.ModelChangedMessage;
import it.polimi.se2018.model.events.PlayerMessageSetup;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInterface extends Remote {

    public void update(ModelChangedMessage modelChangedMessage) throws RemoteException;


    public int getDieFromPatternCard() throws RemoteException;

    public int getDieFromRoundTrack() throws RemoteException;

    public boolean getIncrementedValue() throws RemoteException;

    public Integer[] getPositionInPatternCard() throws RemoteException;

    public int getValueForDie() throws RemoteException;

    public String askForName() throws RemoteException;

    public PlayerMessageSetup askForPatternCard() throws RemoteException;
}
