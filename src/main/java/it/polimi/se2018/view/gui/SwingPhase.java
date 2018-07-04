package it.polimi.se2018.view.gui;

import it.polimi.se2018.model.events.*;
import it.polimi.se2018.network.ServerInterface;

import javax.swing.*;
import java.util.ArrayList;

public abstract class SwingPhase {

    protected ServerInterface serverInterface;

    protected boolean newTurn = true;

    public abstract void update(ModelChangedMessagePatternCard message);
    public abstract void update(ModelChangedMessagePrivateObjective message);
    public abstract void update(ModelChangedMessageDiceOnPatternCard message);
    public abstract void update(ModelChangedMessagePublicObjective message);
    public abstract void update(ModelChangedMessageDiceArena message);
    public abstract void update(ModelChangedMessageRound message);
    public abstract void update(ModelChangedMessageTokensLeft message);
    public abstract void update(ModelChangedMessageEndGame message);
    public abstract void update(ModelChangedMessageToolCard message);
    public abstract void update(ModelChangedMessageRefresh message);

    public abstract void print();

    public void setServerInterface(ServerInterface serverInterface) {
        this.serverInterface = serverInterface;
    }

    public abstract Integer askForPatternCard();

    public abstract PlayerMessage getMainMove();

    public abstract ArrayList<Integer> getPositionInPatternCard();

    public abstract Integer getDieFromDiceArena();

    public abstract ArrayList<Integer> getIncrementedValue();

    public abstract ArrayList<Integer> getSinglePositionInPatternCard(ArrayList<Integer> listOfAvailablePosition);

    public abstract ArrayList<Integer> getDoublePositionInPatternCard();

    public abstract ArrayList<Integer> getDieFromRoundTrack();

    public abstract Integer getValueForDie();

    public abstract void close();

    public boolean isNewTurn() {
        return this.newTurn;
    }

    public void setNewTurn(boolean b) {
        this.newTurn = b;
    }
}