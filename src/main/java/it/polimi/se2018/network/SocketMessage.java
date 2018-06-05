package it.polimi.se2018.network;

import java.io.Serializable;
import java.util.ArrayList;

public class SocketMessage<T> implements Serializable {

    private T object;
    private ArrayList<Object> additionalElements;

    public SocketMessage(T object, ArrayList<Object> additionalElements) {
        this.object = object;
        this.additionalElements = additionalElements;
    }

    public T getObject() {
        return object;
    }

    public ArrayList<Object> getAdditionalElements() {
        return additionalElements;
    }
}
