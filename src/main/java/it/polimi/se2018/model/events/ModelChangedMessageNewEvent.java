package it.polimi.se2018.model.events;

import it.polimi.se2018.network.visitor.MessageVisitorInterface;

public class ModelChangedMessageNewEvent extends ModelChangedMessage {


    private String player;

    private String message;

    public ModelChangedMessageNewEvent(String player, String errorMessage){
        this.player = player;
        this.message = errorMessage;
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
