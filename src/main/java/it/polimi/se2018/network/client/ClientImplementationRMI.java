package it.polimi.se2018.network.client;

import it.polimi.se2018.model.events.ModelChangedMessage;
import it.polimi.se2018.model.events.PlayerMessageSetup;
import it.polimi.se2018.view.ViewClient;

import java.rmi.RemoteException;

public class ClientImplementationRMI implements ClientInterface{

    private ViewClient viewClient;

    public ClientImplementationRMI(ViewClient viewClient) throws RemoteException {
        this.viewClient = viewClient;
    }

    @Override
    public void update(ModelChangedMessage modelChangedMessage) throws RemoteException{
        viewClient.update(modelChangedMessage);
    }

    @Override
    public int getDieFromPatternCard() throws RemoteException {
        return viewClient.getDieFromPatternCard();
    }

    @Override
    public int getDieFromRoundTrack() throws RemoteException {
        return viewClient.getDieFromRoundTrack();
    }

    @Override
    public boolean getIncrementedValue() throws RemoteException {
        return viewClient.getIncrementedValue();
    }

    @Override
    public Integer[] getPositionInPatternCard() throws RemoteException {
        return viewClient.getPositionInPatternCard();
    }

    @Override
    public int getValueForDie() throws RemoteException {
        return viewClient.getValueForDie();
    }

    @Override
    public String askForName() throws RemoteException {
        return viewClient.askForName();
    }

    @Override
    public PlayerMessageSetup askForPatternCard()throws RemoteException{
        return viewClient.askForPatternCard();
    }
}
