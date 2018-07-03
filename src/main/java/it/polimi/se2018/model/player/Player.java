package it.polimi.se2018.model.player;

import it.polimi.se2018.model.container.DiceContainerUnsupportedIdException;
import it.polimi.se2018.model.objectives.PrivateObjective;
import it.polimi.se2018.model.patternCard.*;

import java.util.ArrayList;

public class Player {

    private int id;

    private String name;

    private boolean suspended;

    private ArrayList<PatternCard> patternCards;

    private PatternCard chosenPatternCard;

    private int tokens;

    private PrivateObjective privateObjective;

    private boolean hasChosenPatternCard = false;

    private boolean hasSetDieThisTurn;

    private boolean hasUsedToolCardThisTurn;

    public int finalScore;

    public Player(int id, String name) {
        this.id = id;
        this.name = name;
        hasSetDieThisTurn = false;
        suspended = false;
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

    public boolean hasChosenPatternCard() {
        return hasChosenPatternCard;
    }

    public boolean hasSetDieThisTurn() {
        return hasSetDieThisTurn;
    }

    public void setFinalScore(int finalScore) {
        this.finalScore = finalScore;
    }

    public int getFinalScore() {
        return finalScore;
    }

    public boolean hasUsedToolCardThisTurn() {
        return hasUsedToolCardThisTurn;
    }

    public boolean isSuspended() {
        return suspended;
    }

    public void setTokens(int tokens) {
        this.tokens = tokens;
    }

    public void setSuspended(boolean suspended) {
        this.suspended = suspended;
    }

    public void setChosenPatternCard(PatternCard chosenPatternCard) {
        this.hasChosenPatternCard = true;
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

    /**
     * This method is used whenever the game need to place a die
     * in the player's chosen pattern card.
     * @param idDie die id to place in the pattern card
     * @param x abscissa of the position where the player wants to place a die
     * @param y ordinate of the position where the player wants to place a die
     * @param ignoreValueConstraint whether or not it can ignore the value constraint of the cell selected (tool card n.3)
     * @param ignoreColorConstraint whether or not it can ignore the color constraint of the cell selected (tool card n.2)
     * @param ignoreAdjency whether or not it can ignore the rule where a die must be placed adjacent to another die
     * @throws PatternCardNotRespectingNearbyDieExpection
     * @throws PatternCardNoAdjacentDieException
     * @throws PatternCardDidNotRespectFirstMoveException
     * @throws PatternCardNotRespectingCellConstraintException
     * @throws PatternCardCellIsOccupiedException
     * @throws DiceContainerUnsupportedIdException
     * @throws PlayerHasAlreadySetDieThisTurnException if the player has already set a die this turn
     * @throws PatternCardAdjacentDieException
     */
    public void setDieInPatternCard(int idDie, int x, int y, boolean ignoreValueConstraint, boolean ignoreColorConstraint, boolean ignoreAdjency) throws PatternCardNotRespectingNearbyDieExpection, PatternCardNoAdjacentDieException, PatternCardDidNotRespectFirstMoveException, PatternCardNotRespectingCellConstraintException, PatternCardCellIsOccupiedException, DiceContainerUnsupportedIdException, PlayerHasAlreadySetDieThisTurnException, PatternCardAdjacentDieException {
        if(!hasSetDieThisTurn){
            chosenPatternCard.setDieInPatternCard(idDie, x , y, ignoreValueConstraint, ignoreColorConstraint, ignoreAdjency);
        } else {
            throw new PlayerHasAlreadySetDieThisTurnException();
        }
    }
}


