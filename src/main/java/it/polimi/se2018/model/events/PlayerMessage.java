package it.polimi.se2018.model.events;

import java.io.Serializable;

public abstract class PlayerMessage implements Serializable, Message {
    private static final long serialVersionUID = 5000L;
    protected int player;

    public int getPlayerId() {
        return this.player;
    }
}
