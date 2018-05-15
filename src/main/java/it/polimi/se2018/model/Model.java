package it.polimi.se2018.model;

import it.polimi.se2018.model.container.DiceContainerUnsupportedIdException;
import it.polimi.se2018.utils.Observable;

public class Model extends Observable<Object> {

    private Table table;

    public Model(){

    }

    public Table getTable() {
        return table;
    }

    public void calculateScore() {
        return;
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
    /*TODO: a seconda dell'esito di setRolledDieID dovremo creare notify diversi!*/
    public void setDieInPatternCard(int idPlayer, int idDie, int x, int y, boolean ignoreValueConstraint, boolean ignoreColorConstraint) {
        PatternCard patternCard = table.getPlayers(idPlayer).getChosenPatternCard();

        try {
            if (patternCard.isFirstMove() && patternCard.checkFirstMove(x,y)){
                patternCard.getPatternCardCell(x, y).setRolledDieId(idDie, ignoreValueConstraint, ignoreColorConstraint);
                patternCard.setFirstMove();
            } else if (patternCard.checkProximityCellsValidity(idDie, x, y) && patternCard.checkDieInAdjacentCells(x, y))
                patternCard.getPatternCardCell(x, y).setRolledDieId(idDie, ignoreValueConstraint, ignoreColorConstraint);
        } catch (DiceContainerUnsupportedIdException e){}
    }
}

