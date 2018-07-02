package it.polimi.se2018.model.events;

import it.polimi.se2018.network.visitor.MessageVisitorInterface;

public class PlayerMessageToolCard extends PlayerMessage implements Message {

    private int toolCard;

    public PlayerMessageToolCard(int player, int toolCard){
        this.player = player;
        this.toolCard = toolCard;
    }

    public int getPlayer() {
        return player;
    }

    public int getToolCard() {
        return toolCard;
    }

    @Override
    public void accept(MessageVisitorInterface messageVisitorInterface) {
        messageVisitorInterface.visitPlayerMessageToolCard(this);
    }
}
