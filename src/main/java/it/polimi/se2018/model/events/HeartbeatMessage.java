package it.polimi.se2018.model.events;

import it.polimi.se2018.network.visitor.MessageVisitorInterface;

import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;

public class HeartbeatMessage implements Message, Serializable {

    private int id;

    private Instant sent;

    private Instant received;

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

    public void setReceived(Instant received) {
        this.received = received;
    }

    public void setSent(Instant sent) {
        this.sent = sent;
    }

    public long getTripDuration() {
        return Duration.between(sent, received).toMillis();
    }
}
