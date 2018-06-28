package it.polimi.se2018.model.events;

import it.polimi.se2018.network.visitor.MessageVisitorInterface;

public class PlayerMessageNotAFK extends PlayerMessage {

    public PlayerMessageNotAFK(int player) {
        this.player = player;
    }

    @Override
    public void accept(MessageVisitorInterface messageVisitorInterface) {
        messageVisitorInterface.visitPlayerMessageNotAFK(this);
    }

    public int getPlayer() {
        return player;
    }
}



