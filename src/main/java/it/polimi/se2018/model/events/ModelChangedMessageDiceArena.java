package it.polimi.se2018.model.events;

import it.polimi.se2018.network.visitor.MessageVisitorInterface;

public class ModelChangedMessageDiceArena extends ModelChangedMessage implements Message {

    private String representation;

    public ModelChangedMessageDiceArena (String representation){
        this.representation = representation;
    }

    public String getRepresentation() {
        return representation;
    }

    @Override
    public void accept(MessageVisitorInterface messageVisitorInterface) {
        messageVisitorInterface.visitModelChangedMessageDiceArena(this);
    }
}
