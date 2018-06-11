package it.polimi.se2018.model.events;

import it.polimi.se2018.network.visitor.MessageVisitorInterface;

import java.io.Serializable;

public class HeartbeatMessage implements Message, Serializable {

    private int id;

    public HeartbeatMessage(int id) {
        this.id = id;
    }

    @Override
    public void accept(MessageVisitorInterface messageVisitorInterface) {
        messageVisitorInterface.visitHeartbeatMessage(this);
    }

    public int getId() {
        return id;
    }
}
