package it.polimi.se2018.model;

import it.polimi.se2018.model.objectives.PrivateObjective;

import java.util.ArrayList;

public class Player {

    private int id;

    private String name;

    private ArrayList<PatternCard> patternCards;

    private PatternCard chosenPatternCard;

    private int tokens;

    private PrivateObjective privateObjective;

    public Player(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<PatternCard> getPatternCards() {
        return patternCards;
    }

    public PatternCard getChosenPatternCard() {
        return chosenPatternCard;
    }

    public PrivateObjective getPrivateObjective() {
        return privateObjective;
    }

    public int getTokens() {
        return tokens;
    }

    public void setTokens(int tokens) {
        this.tokens = tokens;
    }

    public void setChosenPatternCard(PatternCard chosenPatternCard) {
        this.chosenPatternCard = chosenPatternCard;
    }

    public void setPatternCards(ArrayList<PatternCard> patternCards) {
        this.patternCards = patternCards;
    }

    public void setPrivateObjective(PrivateObjective privateObjective) {
        this.privateObjective = privateObjective;
    }
}


