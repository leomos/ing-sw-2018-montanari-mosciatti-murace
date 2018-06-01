package it.polimi.se2018.model.events;

public class ModelChangedMessageConnected extends ModelChangedMessage {

    private int idClient;

    public ModelChangedMessageConnected(int id){
        this.idClient = id;
    }

    public int getIdClient() {
        return idClient;
    }
}
