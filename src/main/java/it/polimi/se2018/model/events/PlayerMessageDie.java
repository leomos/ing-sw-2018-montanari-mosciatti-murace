package it.polimi.se2018.model.events;

import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.container.Die;

public class PlayerMessageDie {

    private Player player;

    private Die die;

    private int[] position;

    public PlayerMessageDie(Player player, Die die, int x, int y){
        this.player = player;
        this.die = die;
        this.position[0] = x;
        this.position[1] = y;
    }

    public Player getPlayer() {
        return player;
    }

    public Die getDie() {
        return die;
    }

    public int[] getPosition() {
        return position;
    }
}
