package it.polimi.se2018.model.events;

import it.polimi.se2018.network.visitor.MessageVisitorInterface;

import java.util.HashMap;

public class ModelChangedMessageEndGame extends ModelChangedMessage implements Message {

    private String scoreboard;

    private HashMap<Integer, String> players;

    /**
     * Message created by the model and put in a notify when the game ends and the players need the final scoreboard
     * @param scoreboard representation of the scoreboard
     */
    public ModelChangedMessageEndGame(String scoreboard, HashMap<Integer, String> players){
        this.scoreboard = scoreboard;
        this.players = players;
    }

    public String getScoreboard() {
        return scoreboard;
    }

    public HashMap<Integer, String> getPlayers() {
        return players;
    }

    @Override
    public void accept(MessageVisitorInterface messageVisitorInterface) {
        messageVisitorInterface.visitModelChangedMessageEndGame(this);
    }
}
