package it.polimi.se2018.model.events;

import it.polimi.se2018.network.visitor.MessageVisitorInterface;

public class ModelChangedMessageRound extends ModelChangedMessage implements Message {

    private int idRound;

    private String representation;

    /**
     * Message created by the model and put in a notify when a new round starts containing the dice left in the id round
     * @param idRound round id
     * @param representation representation of the dice left on the round
     */
    public ModelChangedMessageRound (int idRound, String representation){
        this.idRound = idRound;
        this.representation = representation;
    }

    public int getIdRound() {
        return idRound;
    }

    public String getRepresentation() {
        return representation;
    }

    @Override
    public void accept(MessageVisitorInterface messageVisitorInterface) {
        messageVisitorInterface.visitModelChangedMessageRound(this);
    }
}
