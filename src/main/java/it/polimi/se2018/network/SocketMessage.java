package it.polimi.se2018.network;

import it.polimi.se2018.model.events.ModelChangedMessage;
import it.polimi.se2018.model.events.PlayerMessage;

import java.io.Serializable;

public class SocketMessage<T> implements Serializable {

    private T object;
    private boolean isControllerActionResponse = false;

    public SocketMessage(T object) {
        this.object = object;
    }

    public SocketMessage(T object, boolean isControllerActionResponse) {
        this.object = object;
        this.isControllerActionResponse = isControllerActionResponse;
    }

    public boolean isModelChangedMessage() {
        return object.getClass() == ModelChangedMessage.class;
    }

    public boolean isControllerActionEnum() {
        return object.getClass() == ControllerActionEnum.class;
    }

    public boolean isPlayerMessage() {
        return object.getClass() == PlayerMessage.class;
    }

    public boolean isControllerActionResponse() {
        return isControllerActionResponse;
    }

    public T getObject() {
        return this.object;
    }
}
