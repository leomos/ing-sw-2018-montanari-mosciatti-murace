package it.polimi.se2018.model.events;

import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.toolcards.ToolCard;

public class PlayerMessageToolCard {

    private Player player;

    private ToolCard toolcard;

    public PlayerMessageToolCard(Player player, ToolCard toolCard){
        this.player = player;
        this.toolcard = toolCard;
    }

    public Player getPlayer() {
        return player;
    }

    public ToolCard getToolcard() {
        return toolcard;
    }
}
