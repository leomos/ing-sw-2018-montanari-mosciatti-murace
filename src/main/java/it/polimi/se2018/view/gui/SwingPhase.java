package it.polimi.se2018.view.gui;

import it.polimi.se2018.model.events.ModelChangedMessage;
import it.polimi.se2018.model.events.PlayerMessage;
import it.polimi.se2018.network.ServerInterface;

import java.util.ArrayList;

public abstract class SwingPhase {

    protected ServerInterface serverInterface;

    public abstract void update(ModelChangedMessage modelChangedMessage);

    public abstract void print();

    public void setServerInterface(ServerInterface serverInterface){
        this.serverInterface = serverInterface;
    }

    public abstract Integer askForPatternCard();

    public abstract PlayerMessage getMainMove();

    public abstract ArrayList<Integer> getPositionInPatternCard();

    public abstract Integer getDieFromDiceArena();

    public abstract ArrayList<Integer> getIncrementedValue();

    public abstract ArrayList<Integer> getSinglePositionInPatternCard(ArrayList<Integer> listOfAvailablePosition);

    public abstract ArrayList<Integer> getDoublePositionInPatternCard();
}