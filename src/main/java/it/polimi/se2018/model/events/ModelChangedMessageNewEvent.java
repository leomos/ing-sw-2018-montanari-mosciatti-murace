package it.polimi.se2018.model.events;

import it.polimi.se2018.network.visitor.MessageVisitorInterface;

public class ModelChangedMessageNewEvent extends ModelChangedMessage {


    private String player;

    private String message;

    /**
     * Message created by the model and put in a notify when a new event happens.
     * A new event can be a player re-connecting or it can be a message from the model
     * to the player during the input of data for the use of tool cards
     * @param player player id receiving the new event
     * @param message string containing the info about the new event
     */
    public ModelChangedMessageNewEvent(String player, String message){
        this.player = player;
        this.message = message;
    }

    public String getPlayer() {
        return player;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public void accept(MessageVisitorInterface messageVisitorInterface) {
        messageVisitorInterface.visitModelChangedMessageNewEvent(this);
    }

}
