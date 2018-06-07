package it.polimi.se2018.view;

import it.polimi.se2018.model.events.ModelChangedMessage;
import it.polimi.se2018.model.events.PlayerMessageSetup;
import it.polimi.se2018.network.server.ServerInterface;

public abstract class ViewClient {

    private ServerInterface serverInterface;

    public void update(ModelChangedMessage modelChangedMessage) {

    }

    public String askForName() { return null; }

    public int getDieFromPatternCard() {
        return 0;
    }

    public int getDieFromRoundTrack() {
        return 0;
    }

    public boolean getIncrementedValue() {
        return true;
    }

    public Integer[] getPositionInPatternCard(){
        return null;
    }

    public int getValueForDie(){
        return 0;
    }

    public PlayerMessageSetup askForPatternCard(){
        return null;
    }
}
