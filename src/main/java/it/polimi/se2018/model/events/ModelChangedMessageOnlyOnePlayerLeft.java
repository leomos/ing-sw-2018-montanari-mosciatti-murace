package it.polimi.se2018.model.events;

import it.polimi.se2018.network.visitor.MessageVisitorInterface;

import java.util.HashMap;

public class ModelChangedMessageOnlyOnePlayerLeft extends ModelChangedMessage {

    private int playerIdLeft;

    private HashMap<Integer, String> players;

    /**
     * Message created by the model and put in a notify when only one player is active in the room
     * @param playerIdLeft player id left
     */
    public ModelChangedMessageOnlyOnePlayerLeft(int playerIdLeft, HashMap<Integer, String> players){
        this.playerIdLeft = playerIdLeft;
        this.players = players;
    }

    public int getPlayerIdLeft() {
        return playerIdLeft;
    }

    public HashMap<Integer, String> getPlayers() {
        return players;
    }

    @Override
    public void accept(MessageVisitorInterface messageVisitorInterface) {
        messageVisitorInterface.visitModelChangedMessageOnlyOnePlayerLeft(this);
    }
}
