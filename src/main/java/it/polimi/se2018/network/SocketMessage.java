package it.polimi.se2018.network;

public class SocketMessage<T> {
    private T object;

    public SocketMessage(T object) {
        this.object = object;
    }

    public T getObject() {
        return object;
    }
}
