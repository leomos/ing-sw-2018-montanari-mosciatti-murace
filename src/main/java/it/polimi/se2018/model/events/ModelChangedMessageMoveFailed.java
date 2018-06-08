package it.polimi.se2018.model.events;

public class ModelChangedMessageMoveFailed extends ModelChangedMessage{

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
}
