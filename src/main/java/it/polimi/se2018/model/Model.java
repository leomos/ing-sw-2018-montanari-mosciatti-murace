package it.polimi.se2018.model;

import it.polimi.se2018.model.container.DiceContainerUnsupportedIdException;
import it.polimi.se2018.model.toolcards.ToolCard;
import it.polimi.se2018.model.toolcards.ToolCardContainer;
import it.polimi.se2018.model.toolcards.ToolCardNotInPlayException;
import it.polimi.se2018.utils.Observable;

public class Model extends Observable<Object> {
    private GamePhase gamePhase = GamePhase.SETUPPHASE;

    private Table table;

    public Model(){

    }

    public Table getTable() {
        return table;
    }

    public GamePhase getGamePhase() {
        return gamePhase;
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


    /**
     * @param player
     * @param x_i
     * @param y_i
     * @param x_f
     * @param y_f
     * @param ignoreValueConstraint
     * @param ignoreColorConstraint
     * @param idToolCard
     */
    /*TODO: a seconda dell'esito di setRolledDieID dovremo creare notify diversi!*/
    public void moveDieInsidePatternCard(int player,
                                         int x_i,
                                         int y_i,
                                         int x_f,
                                         int y_f,
                                         boolean ignoreValueConstraint,
                                         boolean ignoreColorConstraint,
                                         int idToolCard) {

        Player currentPlayer = this.table.getPlayers(player);
        PatternCard patternCard = currentPlayer.getChosenPatternCard();
        ToolCardContainer toolCardContainer = this.table.getToolCardContainer();

        /* TODO: controllo se toolCard è tra le toolcard disponibili */
        try {
            if(currentPlayer.getTokens() >= toolCardContainer.getToolCardInPlay(idToolCard).cost())
                if (!patternCard.getPatternCardCell(x_i, y_i).isEmpty() &&
                        patternCard.getPatternCardCell(x_f, y_f).isEmpty()) {
                    int dieId = patternCard.getPatternCardCell(x_i, y_i).getRolledDieId();
                    this.setDieInPatternCard(player, dieId, x_i, y_i, ignoreValueConstraint, ignoreColorConstraint);
                    /*TODO: queso metodo sopra restituirà un bool e se è false, non farà le istruzioni seguenti*/
                    patternCard.getPatternCardCell(x_i, y_i).removeDie();
                    this.getTable().getToolCardContainer().getToolCard(idToolCard).setUsed();
                }
        } catch (ToolCardNotInPlayException e) {
            /* TODO: notify che toolcard non è in play? */
            e.printStackTrace();
        }
    }
}

