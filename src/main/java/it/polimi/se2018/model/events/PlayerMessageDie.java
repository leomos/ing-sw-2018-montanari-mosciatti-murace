package it.polimi.se2018.model.events;


import it.polimi.se2018.network.visitor.MessageVisitorInterface;

public class PlayerMessageDie extends PlayerMessage implements Message {

    private int die;

    private int[] position;

    public PlayerMessageDie(int player, int die, int x, int y){
        this.player = player;
        this.die = die;
        this.position[0] = x;
        this.position[1] = y;
    }

    public int getDie() {
        return die;
    }

    public int[] getPosition() {
        return position;
    }

    @Override
    public void accept(MessageVisitorInterface messageVisitorInterface) {
        messageVisitorInterface.visitPlayerMessageDie(this);
    }
}
