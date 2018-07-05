package it.polimi.se2018.model.events;

import java.io.Serializable;

public abstract class ModelChangedMessage implements Serializable, Message {
    protected static final long serialVersionUID = 4000L;
}
