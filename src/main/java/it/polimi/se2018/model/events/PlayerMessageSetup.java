package it.polimi.se2018.model.events;

import it.polimi.se2018.model.Player;

public class PlayerMessageSetup {

    private Player player;

    private int patternCardId;

    public PlayerMessageSetup(Player player, int patternCardId){
        this.player = player;
        this.patternCardId = patternCardId;
    }

    public Player getPlayer() {
        return player;
    }

    public int getPatternCardId() {
        return patternCardId;
    }
}
