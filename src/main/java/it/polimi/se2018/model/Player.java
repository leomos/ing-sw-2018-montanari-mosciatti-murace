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

    private boolean hasSetDieThisTurn;

    public Player(int id, String name) {
        this.id = id;
        this.name = name;
        hasSetDieThisTurn = false;
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

    public boolean hasSetDieThisTurn() {
        return hasSetDieThisTurn;
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

    public void setHasSetDieThisTurn(boolean hasSetDieThisTurn) {
        this.hasSetDieThisTurn = hasSetDieThisTurn;
    }
}


