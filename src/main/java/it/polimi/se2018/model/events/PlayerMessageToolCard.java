package it.polimi.se2018.model.events;

import it.polimi.se2018.network.visitor.MessageVisitorInterface;

public class PlayerMessageToolCard extends PlayerMessage implements Message {

    private int player;

    private int toolcard;

    public PlayerMessageToolCard(int player, int toolCard){
        this.player = player;
        this.toolcard = toolCard;
    }

    public int getPlayer() {
        return player;
    }

    public int getToolcard() {
        return toolcard;
    }

    @Override
    public void accept(MessageVisitorInterface messageVisitorInterface) {
        messageVisitorInterface.visitPlayerMessageToolCard(this);
    }
}
