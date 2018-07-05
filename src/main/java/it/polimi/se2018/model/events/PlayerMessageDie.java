package it.polimi.se2018.model.events;


import it.polimi.se2018.network.visitor.MessageVisitorInterface;

public class PlayerMessageDie extends PlayerMessage implements Message {

    private Integer die;

    private Integer x_position;

    private Integer y_position;

    /**
     * Message generated by the view and sent it to the Virtual View when the player wants to put a die in
     * a pattern card cell
     * @param player player id performing the move
     * @param die die id that the player wants to set
     * @param x cell's abscissa
     * @param y cell's ordinate
     */
    public PlayerMessageDie(Integer player, Integer die, Integer x, Integer y){
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
