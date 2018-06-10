package it.polimi.se2018.network;

import it.polimi.se2018.model.events.ModelChangedMessage;
import it.polimi.se2018.network.server.Room;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface ClientInterface extends Remote {

    public void update(ModelChangedMessage modelChangedMessage) throws RemoteException;

    public Integer getDieFromPatternCard() throws RemoteException;

    public Integer getDieFromRoundTrack() throws RemoteException;

    public Boolean getIncrementedValue() throws RemoteException;

    public ArrayList<Integer> getPositionInPatternCard() throws RemoteException;

    public Integer getValueForDie() throws RemoteException;

    public Integer askForPatternCard() throws RemoteException;

}
