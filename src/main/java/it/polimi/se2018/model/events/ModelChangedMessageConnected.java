package it.polimi.se2018.model.events;

import it.polimi.se2018.network.visitor.MessageVisitorInterface;

import java.io.Serializable;

public class ModelChangedMessageConnected extends ModelChangedMessage implements Serializable, Message {

    private int idClient;

    public ModelChangedMessageConnected(int id){
        this.idClient = id;
    }

    public int getIdClient() {
        return idClient;
    }

    @Override
    public void accept(MessageVisitorInterface messageVisitorInterface) {
        messageVisitorInterface.visitModelChangedMessageConnected(this);
    }
}
