package it.polimi.se2018.model.events;

import it.polimi.se2018.model.toolcards.ToolCard;
import it.polimi.se2018.network.visitor.MessageVisitorInterface;

public class PlayerMessageToolCard extends PlayerMessage implements Message {

    private int player;

    private ToolCard toolcard;

    public PlayerMessageToolCard(int player, ToolCard toolCard){
        this.player = player;
        this.toolcard = toolCard;
    }

    public int getPlayer() {
        return player;
    }

    public ToolCard getToolcard() {
        return toolcard;
    }

    @Override
    public void accept(MessageVisitorInterface messageVisitorInterface) {
        messageVisitorInterface.visitPlayerMessageToolCard(this);
    }
}
