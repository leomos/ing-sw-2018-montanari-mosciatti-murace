package it.polimi.se2018.model.events;

import it.polimi.se2018.model.Player;

public class ModelChangedMessageMoveFailed extends ModelChangedMessage{

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
}
