package it.polimi.se2018.model.events;

import it.polimi.se2018.network.visitor.MessageVisitorInterface;

public class ModelChangedMessagePatternCard extends ModelChangedMessage implements Message {

    private int idPlayer;

    private String playerName;

    private int idPatternCard;

    private String name;

    private int difficulty;

    private String representation;

    /**
     * Message created by the model and put in a notify when the game sends the players the pattern cards
     * @param idPlayer player id receiving the pattern card
     * @param idPatternCard pattern card id
     * @param name pattern card name
     * @param difficulty pattern card difficulty
     * @param representation pattern card representation
     */
    public ModelChangedMessagePatternCard(int idPlayer, String playerName, int idPatternCard, String name, int difficulty, String representation){
        this.idPlayer = idPlayer;
        this.idPatternCard = idPatternCard;
        this.playerName = playerName;
        this.name = name;
        this.difficulty = difficulty;
        this.representation = representation;
    }

    public String getName() {
        return name;
    }

    public int getIdPlayer() {
        return idPlayer;
    }

    public int getIdPatternCard() {
        return idPatternCard;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public String getRepresentation() {
        return representation;
    }

    public String getPlayerName(){ return playerName; }

    @Override
    public void accept(MessageVisitorInterface messageVisitorInterface) {
        messageVisitorInterface.visitModelChangedMessagePatternCard(this);
    }
}
