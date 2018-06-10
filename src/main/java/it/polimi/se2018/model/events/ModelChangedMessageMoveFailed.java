package it.polimi.se2018.model.events;

import it.polimi.se2018.network.visitor.MessageVisitorInterface;

public class ModelChangedMessageMoveFailed extends ModelChangedMessage implements Message {

    private String player;

    private String errorMessage;

    public ModelChangedMessageMoveFailed(String player, String errorMessage){
        this.player = player;
        this.errorMessage = errorMessage;
    }

    public String getPlayer() {
        return player;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public void accept(MessageVisitorInterface messageVisitorInterface) {
        messageVisitorInterface.visitModelChangedMessageMoveFailed(this);
    }
}
