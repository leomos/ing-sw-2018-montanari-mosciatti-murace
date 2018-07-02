package it.polimi.se2018.model.events;

import it.polimi.se2018.network.visitor.MessageVisitorInterface;

public class ModelChangedMessageDiceArena extends ModelChangedMessage implements Message {

    private String representation;

    /**
     * Message created by the model and put in a notify when the draft pool changes in the model
     * @param representation string that represents the dice left in the dice arena
     */
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
