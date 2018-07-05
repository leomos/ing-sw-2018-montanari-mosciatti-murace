package it.polimi.se2018.model.events;

import it.polimi.se2018.network.visitor.MessageVisitorInterface;

public class ModelChangedMessageOnlyOnePlayerLeft extends ModelChangedMessage {

    private int playerIdLeft;

    public ModelChangedMessageOnlyOnePlayerLeft(int playerIdLeft){
        this.playerIdLeft = playerIdLeft;
    }

    public int getPlayerIdLeft() {
        return playerIdLeft;
    }

    @Override
    public void accept(MessageVisitorInterface messageVisitorInterface) {
        messageVisitorInterface.visitModelChangedMessageOnlyOnePlayerLeft(this);
    }
}
