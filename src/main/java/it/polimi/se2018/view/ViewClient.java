package it.polimi.se2018.view;

import it.polimi.se2018.model.events.ModelChangedMessage;
import it.polimi.se2018.network.server.ServerInterface;

public class ViewClient {

    private ServerInterface serverInterface;

    public void update(ModelChangedMessage modelChangedMessage){

    }

    public int[] getDiePosition(){
        return null;
    };

    public int getDie(){
        return 0;
    }

    public boolean getIncrementedValue(){
        return true;
    }
}
