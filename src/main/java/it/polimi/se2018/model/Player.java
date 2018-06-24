package it.polimi.se2018.model;

import it.polimi.se2018.model.container.DiceContainerUnsupportedIdException;
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

    private boolean hasUsedToolCardThisTurn;

    public Player(int id, String name) {
        this.id = id;
        this.name = name;
        hasSetDieThisTurn = false;
        hasUsedToolCardThisTurn = false;
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

    public boolean hasUsedToolCardThisTurn() {
        return hasUsedToolCardThisTurn;
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

    public void setHasUsedToolCardThisTurn(boolean hasUsedToolCardThisTurn) {this.hasUsedToolCardThisTurn = hasUsedToolCardThisTurn;    }

    public void setDieInPatternCard(int idDie, int x, int y, boolean ignoreValueConstraint, boolean ignoreColorConstraint, boolean ignoreAdjency) throws PatternCardNotRespectingNearbyDieExpection, PatternCardNoAdjacentDieException, PatternCardDidNotRespectFirstMoveException, PatternCardNotRespectingCellConstraintException, PatternCardCellIsOccupiedException, DiceContainerUnsupportedIdException, PlayerHasAlreadySetDieThisTurnException, PatternCardAdjacentDieException {
        if(!hasSetDieThisTurn){
            chosenPatternCard.setDieInPatternCard(idDie, x , y, ignoreValueConstraint, ignoreColorConstraint, ignoreAdjency);
        } else {
            throw new PlayerHasAlreadySetDieThisTurnException();
        }
    }
}


