package it.polimi.se2018.model.events;

import it.polimi.se2018.network.visitor.MessageVisitorInterface;

public class ModelChangedMessageMoveFailed extends ModelChangedMessage implements Message {

    private String player;

    private String errorMessage;

    /**
     * Message created by the model and put in a notify when a player move gets rejected
     * @param player player id that performed the move
     * @param errorMessage string containing the reason the move was rejected
     */
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
