package it.polimi.se2018.model.events;

import it.polimi.se2018.network.visitor.MessageVisitorInterface;

public class ModelChangedMessagePlayerAFK  extends ModelChangedMessage{


    private int player;

    private String message;

    /**
     * Message created by the model and put in a notify when a player disconnects or get suspended for not finishing
     * the round before the timer ends
     * @param player player that disconnected
     * @param message message sent to the player
     */
    public ModelChangedMessagePlayerAFK(int player, String message){
        this.player = player;
        this.message = message;
    }

    public int getPlayer() {
        return player;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public void accept(MessageVisitorInterface messageVisitorInterface) {
        messageVisitorInterface.visitModelChangedMessagePlayerAFK(this);
    }


}
