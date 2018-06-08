package it.polimi.se2018.model.events;

import it.polimi.se2018.network.visitor.MessageVisitorInterface;

public interface Message {
    public void accept(MessageVisitorInterface messageVisitorInterface);
}
