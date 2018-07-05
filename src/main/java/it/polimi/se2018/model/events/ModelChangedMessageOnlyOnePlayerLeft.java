package it.polimi.se2018.model.events;

import it.polimi.se2018.network.visitor.MessageVisitorInterface;

public class ModelChangedMessageOnlyOnePlayerLeft extends ModelChangedMessage {

    private int playerIdLeft;

    /**
     * Message created by the model and put in a notify when only one player is active in the room
     * @param playerIdLeft player id left
     */
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
