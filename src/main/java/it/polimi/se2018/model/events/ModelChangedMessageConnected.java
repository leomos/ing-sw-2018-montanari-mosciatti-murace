package it.polimi.se2018.model.events;

import java.io.Serializable;

public class ModelChangedMessageConnected extends ModelChangedMessage implements Serializable {

    private int idClient;

    public ModelChangedMessageConnected(int id){
        this.idClient = id;
    }

    public int getIdClient() {
        return idClient;
    }
}
