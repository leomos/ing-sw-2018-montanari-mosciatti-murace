package it.polimi.se2018.model.events;

import it.polimi.se2018.network.visitor.MessageVisitorInterface;

public class ModelChangedMessageDiceOnPatternCard extends ModelChangedMessage implements Message {

    private int idPlayer;

    private int idPatternCard;

    private String representation;

    /**
     * Message created by the model and put in a notify when the dice on top of a pattern card get changed
     * @param idPlayer player id that owns the pattern card
     * @param idPatternCard pattern card id
     * @param representation dice on pattern card representation
     */
    public ModelChangedMessageDiceOnPatternCard(int idPlayer, int idPatternCard, String representation) {
        this.idPlayer = idPlayer;
        this.idPatternCard = idPatternCard;
        this.representation = representation;
    }

    public int getIdPlayer() {
        return idPlayer;
    }

    public int getIdPatternCard() {
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
