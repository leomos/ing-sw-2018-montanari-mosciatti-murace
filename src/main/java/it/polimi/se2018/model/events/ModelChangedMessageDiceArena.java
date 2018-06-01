package it.polimi.se2018.model.events;

public class ModelChangedMessageDiceArena extends ModelChangedMessage{

    private String representation;

    public ModelChangedMessageDiceArena (String representation){
        this.representation = representation;
    }

    public String getRepresentation() {
        return representation;
    }

}
