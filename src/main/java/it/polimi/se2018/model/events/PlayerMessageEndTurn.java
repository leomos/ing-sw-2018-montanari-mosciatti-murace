package it.polimi.se2018.model.events;

import it.polimi.se2018.network.visitor.MessageVisitorInterface;

public class PlayerMessageEndTurn extends PlayerMessage {

    private int player;

    public PlayerMessageEndTurn(int player){
        this.player = player;
    }

    @Override
    public void accept(MessageVisitorInterface messageVisitorInterface) {
        messageVisitorInterface.visitPlayerMessageEndTurn(this);
    }

    public int getPlayer() {
        return player;
    }
}
