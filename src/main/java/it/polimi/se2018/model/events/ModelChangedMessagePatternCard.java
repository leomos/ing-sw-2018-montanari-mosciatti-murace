package it.polimi.se2018.model.events;

import it.polimi.se2018.network.visitor.MessageVisitorInterface;

public class ModelChangedMessagePatternCard extends ModelChangedMessage implements Message {

    private String idPlayer;

    private String idPatternCard;

    private String name;

    private String difficulty;

    private String representation;

    /**
     * Message created by the model and put in a notify when the game sends the players the pattern cards
     * @param idPlayer player id receiving the pattern card
     * @param idPatternCard pattern card id
     * @param name pattern card name
     * @param difficulty pattern card difficulty
     * @param representation pattern card representation
     */
    public ModelChangedMessagePatternCard(String idPlayer, String idPatternCard, String name, String difficulty, String representation){
        this.idPlayer = idPlayer;
        this.idPatternCard = idPatternCard;
        this.name = name;
        this.difficulty = difficulty;
        this.representation = representation;
    }

    public String getName() {
        return name;
    }

    public String getIdPlayer() {
        return idPlayer;
    }

    public String getIdPatternCard() {
        return idPatternCard;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getRepresentation() {
        return representation;
    }

    @Override
    public void accept(MessageVisitorInterface messageVisitorInterface) {
        messageVisitorInterface.visitModelChangedMessagePatternCard(this);
    }
}
