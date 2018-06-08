package it.polimi.se2018.model.events;

import it.polimi.se2018.network.visitor.MessageVisitorInterface;

public class ModelChangedMessageDiceOnPatternCard extends ModelChangedMessage implements Message {

    private String idPlayer;

    private String idPatternCard;

    private String representation;

    public ModelChangedMessageDiceOnPatternCard(String idPlayer, String idPatternCard, String representation) {
        this.idPlayer = idPlayer;
        this.idPatternCard = idPatternCard;
        this.representation = representation;
    }

    public String getIdPlayer() {
        return idPlayer;
    }

    public String getIdPatternCard() {
        return idPatternCard;
    }

    public String getRepresentation() {
        return representation;
    }

    @Override
    public void accept(MessageVisitorInterface messageVisitorInterface) {
        messageVisitorInterface.visitModelChangedMessageDiceOnPatternCard(this);
    }
}
