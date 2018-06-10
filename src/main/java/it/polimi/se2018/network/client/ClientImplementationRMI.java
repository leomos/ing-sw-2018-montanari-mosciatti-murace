package it.polimi.se2018.network.client;

import it.polimi.se2018.model.events.ModelChangedMessage;
import it.polimi.se2018.network.ClientInterface;
import it.polimi.se2018.view.ViewClient;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class ClientImplementationRMI implements ClientInterface {

    private ViewClient viewClient;

    public ClientImplementationRMI(ViewClient viewClient) {
        this.viewClient = viewClient;
    }

    @Override
    public void update(ModelChangedMessage modelChangedMessage) throws RemoteException {
        viewClient.update(modelChangedMessage);
    }

    @Override
    public Integer getDieFromPatternCard() throws RemoteException {
        return viewClient.getDieFromPatternCard();
    }

    @Override
    public Integer getDieFromRoundTrack() throws RemoteException {
        return viewClient.getDieFromRoundTrack();
    }

    @Override
    public Boolean getIncrementedValue() throws RemoteException {
        return viewClient.getIncrementedValue();
    }

    @Override
    public ArrayList<Integer> getPositionInPatternCard() throws RemoteException {
        return viewClient.getPositionInPatternCard();
    }

    @Override
    public Integer getValueForDie() throws RemoteException {
        return viewClient.getValueForDie();
    }

    @Override
    public Integer askForPatternCard() throws RemoteException {
        return viewClient.askForPatternCard();
    }
}
