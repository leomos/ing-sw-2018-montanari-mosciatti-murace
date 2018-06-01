package it.polimi.se2018.model;

import it.polimi.se2018.model.container.DiceContainerUnsupportedIdException;
import it.polimi.se2018.model.events.*;
import it.polimi.se2018.model.rounds.RoundTrack;
import it.polimi.se2018.model.toolcards.ToolCardContainer;
import it.polimi.se2018.model.toolcards.ToolCardNotInPlayException;
import it.polimi.se2018.utils.Observable;

import java.util.HashMap;

public class Model extends Observable<ModelChangedMessage> {

    private GamePhase gamePhase = GamePhase.SETUPPHASE;

    private Table table;

    /*TODO: costruttore */

    public Model(){
    }

    public void init(HashMap<Integer, String> players) {
        ModelChangedMessageRefresh modelChangedMessageRefresh;
        table = new Table(players);
        for(Integer key : players.keySet()) {
            for (int j = 0; j < 4; j++) {
                PatternCard patternCard = table.getPlayers(key).getPatternCards().get(j);
                notify(new ModelChangedMessagePatternCard(Integer.toString(key),
                        Integer.toString(patternCard.getId()),
                        patternCard.getName(),
                        Integer.toString(patternCard.getDifficulty()),
                        patternCard.getPatternCardRepresentation()));
            }
        }
        modelChangedMessageRefresh = new ModelChangedMessageRefresh(gamePhase);
        notify(modelChangedMessageRefresh);
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
     * @param ignoreValueConstraint boolean necessary for tool cards to ignore positioning value constraint
     * @param ignoreColorConstraint boolean necessary for tool cards to ignore positioning color constraint
     * @throws
     */
    /*TODO: a seconda dell'esito di setRolledDieID dovremo creare notify diversi!*/
    public void setDieInPatternCard(int idPlayer, int idDie, int x, int y, boolean ignoreValueConstraint, boolean ignoreColorConstraint) throws DiceContainerUnsupportedIdException {
        PatternCard patternCard = table.getPlayers(idPlayer).getChosenPatternCard();

        if(table.getDiceArena().getArena().contains(idDie)) {

            try {
                if (patternCard.isFirstMove() && patternCard.checkFirstMove(x, y)) {
                    patternCard.getPatternCardCell(x, y).setRolledDieId(idDie, ignoreValueConstraint, ignoreColorConstraint);
                    patternCard.setFirstMove();
                } else if (patternCard.checkProximityCellsValidity(idDie, x, y) && patternCard.checkDieInAdjacentCells(x, y))
                    patternCard.getPatternCardCell(x, y).setRolledDieId(idDie, ignoreValueConstraint, ignoreColorConstraint);
            } catch (DiceContainerUnsupportedIdException e) {
            }

            table.getDiceArena().getArena().remove(idDie);
            table.getDiceArena().updateRepresentation();
            patternCard.updateDiceRepresentation();
            String idPL = "" + idPlayer;
            String idPC = "" + patternCard.getId();


            notify(new ModelChangedMessageDiceOnPatternCard(idPL, idPC, patternCard.getDiceRepresentation()));
            notify(new ModelChangedMessageDiceArena(table.getDiceArena().getRepresentation()));
            notify(new ModelChangedMessageRefresh(this.gamePhase));
        }
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
                                         int idToolCard) throws DiceContainerUnsupportedIdException {

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

    public void swapDieAmongRoundTrackAndDiceArena(int dieIdInRoundTrack, int dieIdInDiceArena) {
        RoundTrack roundTrack = table.getRoundTrack();
        DiceArena diceArena = table.getDiceArena();

        try {
            roundTrack.swapDieInRound(dieIdInRoundTrack, dieIdInDiceArena);
        } catch (DieNotPresentException e) {
            /* TODO: notify che il dado swappato non esiste nella RoundTrack */
            e.printStackTrace();
        }

        try {
            diceArena.swapDie(dieIdInDiceArena, dieIdInRoundTrack);
        } catch (DieNotPresentException e) {
            /* TODO: notify che il dado swappato non esiste nella DiceArena */
            e.printStackTrace();
        }

    }
}

