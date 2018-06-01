package it.polimi.se2018.model.events;


public class PlayerMessageDie extends PlayerMessage {

    private int player;

    private int die;

    private int[] position;

    public PlayerMessageDie(int player, int die, int x, int y){
        this.player = player;
        this.die = die;
        this.position[0] = x;
        this.position[1] = y;
    }

    public int getPlayer() {
        return player;
    }

    public int getDie() {
        return die;
    }

    public int[] getPosition() {
        return position;
    }
}
