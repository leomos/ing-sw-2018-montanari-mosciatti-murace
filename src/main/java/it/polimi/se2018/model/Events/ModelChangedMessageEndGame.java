package it.polimi.se2018.model.Events;

import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.Scoreboard;

public class ModelChangedMessageEndGame extends ModelChangedMessage{

    private Player player;

    private Scoreboard scoreboard;

    public ModelChangedMessageEndGame(Player player, Scoreboard scoreboard){
        this.player = player;
        this.scoreboard = scoreboard;
    }

    public Player getPlayer() {
        return player;
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }
}
