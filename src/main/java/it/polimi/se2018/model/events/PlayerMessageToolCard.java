package it.polimi.se2018.model.events;

public class PlayerMessageToolCard extends PlayerMessage {

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
}
