package it.polimi.se2018.model;

import it.polimi.se2018.model.container.DiceContainerUnsupportedIdException;
import it.polimi.se2018.model.objectives.PrivateObjective;

import java.util.ArrayList;

public class Player {

    private int id;

    private String name;

    private boolean firstMove = true;

    private ArrayList<PatternCard> patternCards;

    private PatternCard chosenPatternCard;

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

    public PatternCard getChosenPatternCard() {
        return chosenPatternCard;
    }

    public PrivateObjective getPrivateObjective() {
        return privateObjective;
    }

    public void setTokens(int tokens) {
        this.tokens = tokens;
    }

    public int getTokens() {
        return tokens;
    }

    public void setChosenPatternCard(PatternCard chosenPatternCard) {
        this.chosenPatternCard = chosenPatternCard;
    }


    /**
     *
     * @param idDie id to identify the die
     * @param x cell's abscissa
     * @param y cell's ordinate
     * @param ignoreValueConstraint boolean necessary for tool cards to ignore positioning constraint
     * @param ignoreColorConstraint boolean necessary for tool cards to ignore positioning constraint
     * @throws
     */
    /*TODO: sistema if, non mi piace*/
    public void setDieInPatternCard(int idDie, int x, int y, boolean ignoreValueConstraint, boolean ignoreColorConstraint) {
        try {
            if (firstMove && chosenPatternCard.checkFirstMove(x,y)){
                chosenPatternCard.getPatternCardCell(x, y).setRolledDieId(idDie, ignoreValueConstraint, ignoreColorConstraint);
                firstMove = false;
            } else if (chosenPatternCard.checkProximityCellsValidity(idDie, x, y) && chosenPatternCard.checkDieInAdjacentCells(x, y))
                chosenPatternCard.getPatternCardCell(x, y).setRolledDieId(idDie, ignoreValueConstraint, ignoreColorConstraint);
        } catch (DiceContainerUnsupportedIdException e){}
    }
}


