package it.polimi.se2018.network.client;

import it.polimi.se2018.model.events.ModelChangedMessage;
import it.polimi.se2018.network.ClientInterface;
import it.polimi.se2018.view.ViewClient;

import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Implementation of ClientInterface for clients connecting with RMI
 * @see it.polimi.se2018.network.ClientInterface
 */
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
    public Integer getDieFromDiceArena() throws RemoteException{
        return viewClient.getDieFromDiceArena();
    }

    @Override
    public ArrayList<Integer> getDieFromRoundTrack() throws RemoteException {
        return viewClient.getDieFromRoundTrack();
    }

    @Override
    public ArrayList<Integer> getIncrementedValue() throws RemoteException {
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

    @Override
    public ArrayList<Integer> getSinglePositionInPatternCard(ArrayList<Integer> listOfAvailablePositions) throws RemoteException {
        return viewClient.getSinglePositionInPatternCard(listOfAvailablePositions);
    }

    @Override
    public ArrayList<Integer> getDoublePositionInPatternCard() throws RemoteException {
        return viewClient.getDoublePositionInPatternCard();
    }

    public Boolean block() throws RemoteException {
        return viewClient.block();
    }

    public Boolean free() throws RemoteException {
        return viewClient.free();
    }
}
