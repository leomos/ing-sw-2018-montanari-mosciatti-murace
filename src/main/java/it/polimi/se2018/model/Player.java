package it.polimi.se2018.model;

import it.polimi.se2018.model.objectives.PrivateObjective;

import java.util.ArrayList;

public class Player {

    private int id;

    private String name;

    private ArrayList<PatternCard> patternCards;

    private int chosenPatternCard;

    private int tokens;

    private PrivateObjective privateObjective;

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ArrayList<PatternCard> getPatternCards() {
        return patternCards;
    }

    public int getChosenPatternCard() {
        return chosenPatternCard;
    }

    public void setTokens(int tokens) {
        this.tokens = tokens;
    }

    public int getTokens() {
        return tokens;
    }

    public void setChosenPatternCard(int chosenPatternCard) {
        this.chosenPatternCard = chosenPatternCard;
    }
}
