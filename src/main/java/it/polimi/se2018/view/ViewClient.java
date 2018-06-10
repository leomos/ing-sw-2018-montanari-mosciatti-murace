package it.polimi.se2018.view;

import it.polimi.se2018.model.events.ModelChangedMessage;
import it.polimi.se2018.network.ServerInterface;

import java.util.ArrayList;

public abstract class ViewClient {

    protected ServerInterface serverInterface;

    public void update(ModelChangedMessage modelChangedMessage) {

    }


    public void setServerInterface(ServerInterface serverInterface) {
        this.serverInterface = serverInterface;
    }

    public String askForName() { return null; }

    public Integer getDieFromPatternCard() {
        return 0;
    }

    public Integer getDieFromRoundTrack() {
        return 0;
    }

    public Boolean getIncrementedValue() {
        return true;
    }

    public ArrayList<Integer> getPositionInPatternCard(){
        return null;
    }

    public Integer getValueForDie(){
        return 0;
    }

    public Integer askForPatternCard(){
        return null;
    }

}
