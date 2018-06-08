package it.polimi.se2018.model.events;


public class PlayerMessageDie extends PlayerMessage {

    private int player;

    private int die;

    private int x_position;

    private int y_position;

    public PlayerMessageDie(int player, int die, int x, int y){
        this.player = player;
        this.die = die;
        this.x_position = x;
        this.y_position = y;
    }

    public int getPlayer() {
        return player;
    }

    public int getDie() {
        return die;
    }

    public int getX_position() {
        return x_position;
    }

    public int getY_position() {
        return y_position;
    }
}
