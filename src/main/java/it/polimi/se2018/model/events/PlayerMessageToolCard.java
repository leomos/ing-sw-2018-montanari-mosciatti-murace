package it.polimi.se2018.model.events;

import it.polimi.se2018.model.toolcards.ToolCard;

public class PlayerMessageToolCard {

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
}
