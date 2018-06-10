package it.polimi.se2018.model.events;


import it.polimi.se2018.network.visitor.MessageVisitorInterface;

public class PlayerMessageDie extends PlayerMessage implements Message {

    private int die;

    private int x_position;

    private int y_position;

    public PlayerMessageDie(int player, int die, int x, int y){
        this.player = player;
        this.die = die;
        this.x_position = x;
        this.y_position = y;
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

    @Override
    public void accept(MessageVisitorInterface messageVisitorInterface) {
        messageVisitorInterface.visitPlayerMessageDie(this);
    }
}
