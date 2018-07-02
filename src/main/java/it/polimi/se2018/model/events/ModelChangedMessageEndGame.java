package it.polimi.se2018.model.events;

import it.polimi.se2018.network.visitor.MessageVisitorInterface;

public class ModelChangedMessageEndGame extends ModelChangedMessage implements Message {

    private String scoreboard;

    /**
     * Message created by the model and put in a notify when the game ends and the players need the final scoreboard
     * @param scoreboard representation of the scoreboard
     */
    public ModelChangedMessageEndGame(String scoreboard){
        this.scoreboard = scoreboard;
    }

    public String getScoreboard() {
        return scoreboard;
    }

    @Override
    public void accept(MessageVisitorInterface messageVisitorInterface) {
        messageVisitorInterface.visitModelChangedMessageEndGame(this);
    }
}
