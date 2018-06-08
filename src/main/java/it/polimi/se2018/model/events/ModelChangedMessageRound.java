package it.polimi.se2018.model.events;

import it.polimi.se2018.network.visitor.MessageVisitorInterface;

public class ModelChangedMessageRound extends ModelChangedMessage implements Message {

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

    @Override
    public void accept(MessageVisitorInterface messageVisitorInterface) {
        messageVisitorInterface.visitModelChangedMessageRound(this);
    }
}
