package it.polimi.se2018.model.Events;

import it.polimi.se2018.model.Player;

public class PlayerMessageEndTurn {

    private Player player;

    public PlayerMessageEndTurn(Player player){
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
