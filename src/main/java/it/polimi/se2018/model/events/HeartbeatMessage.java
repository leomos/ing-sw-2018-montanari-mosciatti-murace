package it.polimi.se2018.model.events;

import it.polimi.se2018.network.visitor.MessageVisitorInterface;

import java.io.Serializable;
import java.time.Instant;

public class HeartbeatMessage implements Message, Serializable {

    private static final long serialVersionUID = 1000L;

    private int id;

    private Instant sent;

    public HeartbeatMessage(int id, Instant sent) {
        this.id = id;
        this.sent = sent;
    }

    @Override
    public void accept(MessageVisitorInterface messageVisitorInterface) {
        messageVisitorInterface.visitHeartbeatMessage(this);
    }

    public int getId() {
        return id;
    }

    public Instant getSent() {
        return sent;
    }
}
