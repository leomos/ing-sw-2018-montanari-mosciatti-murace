package it.polimi.se2018.model.events;

import java.io.Serializable;

public abstract class PlayerMessage implements Serializable, Message {
    protected int player;

    public int getPlayerId() {
        return this.player;
    }
}
