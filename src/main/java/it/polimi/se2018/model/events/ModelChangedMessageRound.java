package it.polimi.se2018.model.events;

public class ModelChangedMessageRound {

    private String idRound;

    private String representation;

    public ModelChangedMessageRound (String idRound, String representation){
        this.idRound = idRound;
        this.representation = representation;
    }
    public String getIdRound() {
        return idRound;
    }

    public String getRepresentation() {
        return representation;
    }
}
