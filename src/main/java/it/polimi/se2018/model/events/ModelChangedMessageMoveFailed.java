package it.polimi.se2018.model.events;

import it.polimi.se2018.model.Player;
import it.polimi.se2018.network.visitor.MessageVisitorInterface;

public class ModelChangedMessageMoveFailed extends ModelChangedMessage implements Message {

    private Player player;

    private String errorMessage;

    public ModelChangedMessageMoveFailed(Player player, String errorMessage){
        this.player = player;
        this.errorMessage = errorMessage;
    }

    public Player getPlayer() {
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
