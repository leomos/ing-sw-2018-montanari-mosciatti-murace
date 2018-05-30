package it.polimi.se2018.model.events;

public class ModelChangedMessageDice {

    private String idPlayer;

    private String idPatternCard;

    private String representation;

    public ModelChangedMessageDice(String idPlayer, String idPatternCard, String representation) {
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
}
