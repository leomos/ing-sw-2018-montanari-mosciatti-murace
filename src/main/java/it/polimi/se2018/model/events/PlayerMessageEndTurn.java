package it.polimi.se2018.model.events;

public class PlayerMessageEndTurn extends PlayerMessage {

    private int player;

    public PlayerMessageEndTurn(int player){
        this.player = player;
    }

    public int getPlayer() {
        return player;
    }
}
