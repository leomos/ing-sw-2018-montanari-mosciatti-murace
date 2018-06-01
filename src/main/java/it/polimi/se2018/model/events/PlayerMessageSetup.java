package it.polimi.se2018.model.events;

import java.io.Serializable;

public class PlayerMessageSetup extends PlayerMessage implements Serializable {

    private int player;

    private int patternCardId;

    public PlayerMessageSetup(int player, int patternCardId){
        this.player = player;
        this.patternCardId = patternCardId;
    }

    public int getPlayer() {
        return player;
    }

    public int getPatternCardId() {
        return patternCardId;
    }
}
