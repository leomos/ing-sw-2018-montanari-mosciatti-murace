package it.polimi.se2018.model;

import it.polimi.se2018.model.container.DiceContainerUnsupportedIdException;
import it.polimi.se2018.model.container.Die;
import it.polimi.se2018.model.container.DieRolledValueOutOfBoundException;
import it.polimi.se2018.model.events.*;
import it.polimi.se2018.model.objectives.PrivateObjective;
import it.polimi.se2018.model.objectives.PublicObjective;
import it.polimi.se2018.model.rounds.*;
import it.polimi.se2018.model.toolcards.ToolCard;
import it.polimi.se2018.model.toolcards.ToolCardContainer;
import it.polimi.se2018.utils.Observable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Model extends Observable<ModelChangedMessage> {

    private GamePhase gamePhase = GamePhase.SETUPPHASE;

    private Table table;

    private HashMap<Integer, String> players;

    /*TODO: costruttore */

    public Model(HashMap<Integer, String> players){
        this.players = players;
    }

    public void initSetup() {
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

            PrivateObjective privateObjective = table.getPlayers(key).getPrivateObjective();
            notify(new ModelChangedMessagePrivateObjective(Integer.toString(key),
                    Integer.toString(privateObjective.getId()),
                    privateObjective.getName(),
                    privateObjective.getDescription()));

        }
        modelChangedMessageRefresh = new ModelChangedMessageRefresh(gamePhase, null);
        notify(modelChangedMessageRefresh);
    }

    public void initGame() {
        ModelChangedMessageRefresh modelChangedMessageRefresh;
        this.gamePhase = GamePhase.GAMEPHASE;
        modelChangedMessageRefresh = new ModelChangedMessageRefresh(gamePhase, null);
        notify(modelChangedMessageRefresh);

        for(Integer key : players.keySet()) {
            PatternCard patternCard = table.getPlayers(key).getChosenPatternCard();

            notify(new ModelChangedMessagePatternCard(Integer.toString(key),
                    Integer.toString(patternCard.getId()),
                    patternCard.getName(),
                    Integer.toString(patternCard.getDifficulty()),
                    patternCard.getPatternCardRepresentation()));

            notify(new ModelChangedMessageDiceOnPatternCard(Integer.toString(key),
                    Integer.toString(patternCard.getId()),
                    patternCard.getDiceRepresentation()));

            PrivateObjective privateObjective = table.getPlayers(key).getPrivateObjective();
            notify(new ModelChangedMessagePrivateObjective(Integer.toString(key),
                    Integer.toString(privateObjective.getId()),
                    privateObjective.getName(),
                    privateObjective.getDescription()));

            notify(new ModelChangedMessageTokensLeft(Integer.toString(key),
                    Integer.toString(table.getPlayers(key).getTokens())));

        }

        for(int j = 0; j < 3; j ++) {
            PublicObjective publicObjective = table.getPublicObjective(j);
            notify(new ModelChangedMessagePublicObjective(Integer.toString(publicObjective.getId()),
                    publicObjective.getName(),
                    publicObjective.getDescription()));

            ToolCard toolCard = table.getToolCardContainer().getToolCardInPlay().get(j);
            notify(new ModelChangedMessageToolCard(Integer.toString(toolCard.getToolCardId()),
                    toolCard.getName(),
                    toolCard.getDescription(),
                    Integer.toString(toolCard.cost())));
        }

        notify(new ModelChangedMessageDiceArena(table.getDiceArena().getRepresentation()));

        modelChangedMessageRefresh = new ModelChangedMessageRefresh(gamePhase, Integer.toString(table.getRoundTrack().getCurrentRound().getIdPlayerPlaying()));
        notify(modelChangedMessageRefresh);

    }

    public void setChosenPatternCard(int idPatternCard, int idPlayer){

        for(PatternCard patternCard : table.getPlayers(idPlayer).getPatternCards())
            if(idPatternCard == patternCard.getId()) {
                table.getPlayers(idPlayer).setChosenPatternCard(patternCard);
                table.getPlayers(idPlayer).setTokens(patternCard.getDifficulty());
                }
    }

    public void setDieInPatternCardFromDiceArena(int idPlayer, int idDie, int x, int y, boolean ignoreAdjency, int idToolCard) {
        PatternCard patternCard = table.getPlayers(idPlayer).getChosenPatternCard();

        int actualIdDie = table.getDiceArena().getArena().get(idDie);

        try {
            table.getPlayers(idPlayer).setDieInPatternCard(actualIdDie, x, y, false, false, ignoreAdjency);

            if(patternCard.isFirstMove())
                patternCard.setFirstMove();

            if(idToolCard != -1)
                updateToolCard(idPlayer, idToolCard);

            table.getPlayers(idPlayer).setHasSetDieThisTurn(true);
            table.getDiceArena().getArena().remove(idDie);
            String idPL = "" + idPlayer;
            String idPC = "" + patternCard.getId();


            notify(new ModelChangedMessageDiceOnPatternCard(idPL, idPC, patternCard.getDiceRepresentation()));
            notify(new ModelChangedMessageDiceArena(table.getDiceArena().getRepresentation()));
            notify(new ModelChangedMessageRefresh(this.gamePhase, Integer.toString(table.getRoundTrack().getCurrentRound().getIdPlayerPlaying())));

        } catch (PatternCardDidNotRespectFirstMoveException e) {
            notify(new ModelChangedMessageMoveFailed(Integer.toString(idPlayer), "Did not respect first move constraint"));
        } catch (PatternCardNoAdjacentDieException e) {
            notify(new ModelChangedMessageMoveFailed(Integer.toString(idPlayer), "No die adjacent to the cell selected"));
        } catch (PatternCardCellIsOccupiedException e) {
            notify(new ModelChangedMessageMoveFailed(Integer.toString(idPlayer), "Already a die in that position"));
        } catch (PatternCardNotRespectingCellConstraintException e) {
            notify(new ModelChangedMessageMoveFailed(Integer.toString(idPlayer), "Did not respect cell constraint"));
        } catch (PatternCardNotRespectingNearbyDieExpection patternCardNotRespectingNearbyDieExpection) {
            notify(new ModelChangedMessageMoveFailed(Integer.toString(idPlayer), "Not respecting nearby dice colors or values"));
        } catch (PlayerHasAlreadySetDieThisTurnException e){
            notify(new ModelChangedMessageMoveFailed(Integer.toString(idPlayer), "Already set a Die this turn"));
        } catch (PatternCardAdjacentDieException e) {
            notify(new ModelChangedMessageMoveFailed(Integer.toString(idPlayer), "There can't be a die close to the selected cell"));
        } catch (DiceContainerUnsupportedIdException e) {
            e.printStackTrace();
        }

    }

    public void endTurn(PlayerMessageEndTurn playerMessageEndTurn){
        if(!table.getRoundTrack().getCurrentRound().isRoundOver()) {
            try {
                table.getRoundTrack().getCurrentRound().setNextPlayer();
            } catch (RoundFinishedException e) {
                e.printStackTrace();
            }
        }
        else {
            Round round = table.getRoundTrack().getCurrentRound();
            table.getRoundTrack().setRolledDiceLeftForCurrentRound(table.getDiceArena().getArena());
            round.updateRepresentation();
            table.getDiceArena().rollDiceIntoArena();

            notify(new ModelChangedMessageDiceArena(table.getDiceArena().getRepresentation()));
            notify(new ModelChangedMessageRound(Integer.toString(round.getId()), round.getRepresentation()));

            try {
                table.getRoundTrack().startNextRound();
            } catch (RoundTrackNoMoreRoundsException e) {
                //TODO: here what to do once the rounds finish
            }
        }
        table.getPlayers(playerMessageEndTurn.getPlayer()).setHasSetDieThisTurn(false);
        table.getPlayers(playerMessageEndTurn.getPlayer()).setHasUsedToolCardThisTurn(false);

        notify(new ModelChangedMessageRefresh(this.gamePhase, Integer.toString((table.getRoundTrack().getCurrentRound().getIdPlayerPlaying()))));
    }

    public boolean checkToolCard(int idPlayer, int idToolCard){
        Player currentPlayer = this.table.getPlayers(idPlayer);
        ToolCardContainer toolCardContainer = this.table.getToolCardContainer();
        String idPL = "" + idPlayer;

        boolean toolCardPresent = false;
        boolean enoughTokens = false;

        for(int i = 0; i < toolCardContainer.getToolCardInPlay().size(); i ++){
            if(toolCardContainer.getToolCardInPlay().get(i).getToolCardId() == idToolCard)
                toolCardPresent = true;
        }

        if(toolCardPresent) {
            if (!currentPlayer.hasUsedToolCardThisTurn()) {
                if(currentPlayer.getTokens() >= toolCardContainer.getToolCard(idToolCard).cost()){
                    enoughTokens = true;
                } else {
                    notify(new ModelChangedMessageMoveFailed(idPL, "Not enough tokens"));
                }
            } else {
                notify(new ModelChangedMessageMoveFailed(idPL, "Already used a toolCard this turn"));
            }
        } else {
            notify(new ModelChangedMessageMoveFailed(idPL, "ToolCard selected is not in Play"));
        }

        return toolCardPresent && enoughTokens;
    }


    public void incrementOrDecrementDieValue(int idPlayer, int idDie, int changeValue, int idToolCard) {

        Die d = null;
        try {
            d = table.getDiceContainer().getDie(table.getDiceArena().getArena().get(idDie));
        } catch (DiceContainerUnsupportedIdException e) {
            e.printStackTrace();
        }



        try {
            if(changeValue == 1)
                d.setRolledValue( d.getRolledValue() + 1);
            else
                d.setRolledValue(d.getRolledValue() - 1);

            updateToolCard(idPlayer, idToolCard);

            notify(new ModelChangedMessageDiceArena(table.getDiceArena().getRepresentation()));

            notify(new ModelChangedMessageRefresh(this.gamePhase, Integer.toString(table.getRoundTrack().getCurrentRound().getIdPlayerPlaying())));

        } catch (DieRolledValueOutOfBoundException e) {
            notify(new ModelChangedMessageMoveFailed(Integer.toString(idPlayer), "Can't turn a 6 into a 1 or a 6 into a 1!"));
        }



    }

    /**
     * @param idPlayer
     * @param x_i
     * @param y_i
     * @param x_f
     * @param y_f
     * @param ignoreValueConstraint
     * @param ignoreColorConstraint
     * @param idToolCard
     */
    /*TODO: a seconda dell'esito di setRolledDieID dovremo creare notify diversi!*/
    public void moveDieInsidePatternCard(int idPlayer,
                                         int x_i,
                                         int y_i,
                                         int x_f,
                                         int y_f,
                                         boolean ignoreValueConstraint,
                                         boolean ignoreColorConstraint,
                                         int idToolCard)  {

        Player currentPlayer = this.table.getPlayers(idPlayer);
        PatternCard patternCard = currentPlayer.getChosenPatternCard();
        int idDie = patternCard.getPatternCardCell(x_i, y_i).getRolledDieId();

        try {
            patternCard.getPatternCardCell(x_i, y_i).removeDie();

            boolean moveFailed = true;

            try {

                patternCard.setDieInPatternCard(idDie, x_f, y_f, ignoreValueConstraint, ignoreColorConstraint, false);
                moveFailed = false;

                updateToolCard(idPlayer, idToolCard);

                notify(new ModelChangedMessageDiceOnPatternCard(Integer.toString(idPlayer), Integer.toString(patternCard.getId()), patternCard.getDiceRepresentation()));

                notify(new ModelChangedMessageRefresh(gamePhase, Integer.toString(idPlayer)));

            } catch (PatternCardDidNotRespectFirstMoveException e) {
                notify(new ModelChangedMessageMoveFailed(Integer.toString(idPlayer), "Did not respect first move constraint"));
            } catch (PatternCardNoAdjacentDieException e) {
                notify(new ModelChangedMessageMoveFailed(Integer.toString(idPlayer), "No die adjacent to the cell selected"));
            } catch (PatternCardCellIsOccupiedException e) {
                notify(new ModelChangedMessageMoveFailed(Integer.toString(idPlayer), "Already a die in that position"));
            } catch (PatternCardNotRespectingCellConstraintException e) {
                notify(new ModelChangedMessageMoveFailed(Integer.toString(idPlayer), "Did not respect cell constraint"));
            } catch (PatternCardNotRespectingNearbyDieExpection patternCardNotRespectingNearbyDieExpection) {
                notify(new ModelChangedMessageMoveFailed(Integer.toString(idPlayer), "Not respecting nearby dice colors or values"));
            }  catch (DiceContainerUnsupportedIdException e) {
                e.printStackTrace();
            } catch (PatternCardAdjacentDieException e) {
                notify(new ModelChangedMessageMoveFailed(Integer.toString(idPlayer), "There can't be a die close to the selected cell"));
            }

            if(moveFailed) {
                try {
                    patternCard.getPatternCardCell(x_i, y_i).setRolledDieId(idDie, true, true);
                } catch (DiceContainerUnsupportedIdException e) {
                    e.printStackTrace();
                }
            }

        } catch (CellIsEmptyException e) {
            notify(new ModelChangedMessageMoveFailed(Integer.toString(idPlayer), "Starting cell is empty"));
        }
    }

    public void turnDieAround(int idPlayer, int idDie, int idToolCard) {
        try {
            table.getDiceContainer().getDie(table.getDiceArena().getArena().get(idDie)).turnAround();

            updateToolCard(idPlayer, idToolCard);

            notify(new ModelChangedMessageDiceArena(table.getDiceArena().getRepresentation()));

            notify(new ModelChangedMessageRefresh(gamePhase, Integer.toString(idPlayer)));

        } catch (DiceContainerUnsupportedIdException e) {

        }

    }

    public void rerollDiceArena(int idPlayer, int idToolCard){

        try {
            table.getDiceArena().rerollDiceArena(table.getRoundTrack(), table.getPlayers(idPlayer));

            updateToolCard(idPlayer, idToolCard);

            notify(new ModelChangedMessageDiceArena(table.getDiceArena().getRepresentation()));

            notify(new ModelChangedMessageRefresh(this.gamePhase, Integer.toString(table.getRoundTrack().getCurrentRound().getIdPlayerPlaying())));

        } catch (RoundTrackNotInSecondPartOfRoundException e) {
            notify(new ModelChangedMessageMoveFailed(Integer.toString(idPlayer), "Can only be used during second turn of round"));
        } catch (PlayerHasAlreadySetDieThisTurnException e) {
            notify(new ModelChangedMessageMoveFailed(Integer.toString(idPlayer), "Can only use this card before setting a die"));
        }

    }

    public void giveConsecutiveRoundsToPlayer(int idPlayer, int idToolCard){

        try {
            table.getRoundTrack().getCurrentRound().giveConsecutiveTurnsToPlayer(idPlayer, table.getPlayers(idPlayer));

            updateToolCard(idPlayer, idToolCard);

        } catch (RoundPlayerAlreadyPlayedSecondTurnException e) {
            notify(new ModelChangedMessageMoveFailed(Integer.toString(idPlayer), "Can only use this card during first turn"));
        } catch (PlayerHasNotSetDieThisTurnException playerHasNotSetDieThisTurnException) {
            notify(new ModelChangedMessageMoveFailed(Integer.toString(idPlayer), "Can only use this card after setting a die"));
        }

    }

    public void rollDieAgain(int idPlayer, int idDie, int idToolCard){

        try {
            Die d = table.getDiceContainer().getDie(table.getDiceArena().getArena().get(idDie));

            updateToolCard(idPlayer, idToolCard);
            d.roll();

            PatternCard patternCard = table.getPlayers(idPlayer).getChosenPatternCard();
            notify(new ModelChangedMessageDiceOnPatternCard(Integer.toString(idPlayer), Integer.toString(patternCard.getId()), patternCard.getDiceRepresentation()));
            notify(new ModelChangedMessageDiceArena(table.getDiceArena().getRepresentation()));
            notify(new ModelChangedMessageRefresh(this.gamePhase, Integer.toString(table.getRoundTrack().getCurrentRound().getIdPlayerPlaying())));


            notify(new ModelChangedMessageNewEvent(Integer.toString(idPlayer), "The new value for the die is " + d.getRolledValue()));

        } catch (DiceContainerUnsupportedIdException e) {
            e.printStackTrace();
        }


    }

    public void swapDieAmongRoundTrackAndDiceArena(int idPlayer, int dieIdInRoundTrack, int dieIdInDiceArena, int idToolCard) {
        RoundTrack roundTrack = table.getRoundTrack();
        DiceArena diceArena = table.getDiceArena();
        int actualIdDieInDiceArena = table.getDiceArena().getArena().get(dieIdInDiceArena);

        try {
            roundTrack.swapDieInRound(actualIdDieInDiceArena, dieIdInDiceArena);

            try {
                diceArena.swapDie(dieIdInDiceArena, actualIdDieInDiceArena);

                Round round = table.getRoundTrack().getRound(table.getRoundTrack().getRoundIdForDieId(dieIdInDiceArena));
                notify(new ModelChangedMessageRound(Integer.toString(round.getId()), round.getRepresentation()));

                updateToolCard(idPlayer, idToolCard);

                notify(new ModelChangedMessageDiceArena(table.getDiceArena().getRepresentation()));

                notify(new ModelChangedMessageRefresh(this.gamePhase, Integer.toString(table.getRoundTrack().getCurrentRound().getIdPlayerPlaying())));

            } catch (DieNotPresentException e) {
                roundTrack.swapDieInRound(dieIdInDiceArena, actualIdDieInDiceArena);
                notify(new ModelChangedMessageMoveFailed(Integer.toString(idPlayer), "Die not Present in Die Arena"));
            }


        } catch (DieNotPresentException e) {
            notify(new ModelChangedMessageMoveFailed(Integer.toString(idPlayer), "Die not present in RoundTrack"));
        }


    }

    public Integer swapDieWithDieFromDiceBag(int idPlayer, int idDie, int idToolCard){
        DiceArena diceArena = table.getDiceArena();

        diceArena.removeDieFromDiceArena(table.getDiceArena().getArena().get(idDie));
        ArrayList<Integer> diceToRoll = table.getDiceContainer().getUnrolledDice();
        Collections.shuffle(diceToRoll);


        Die d = null;
        int idNewDie = diceToRoll.get(0);
        try {
            d = table.getDiceContainer().getDie(idNewDie);
        } catch (DiceContainerUnsupportedIdException e) {
            e.printStackTrace();
        }

        updateToolCard(idPlayer, idToolCard);

        notify(new ModelChangedMessageNewEvent(Integer.toString(idPlayer), "\nThe new die has the color " + d.getColor()));
        return idNewDie;

    }

    public void giveValueToDie(int idPlayer, int idDie, int value){

        table.getDiceArena().rollOneDieIntoDiceArena(idDie, value);

        notify(new ModelChangedMessageDiceArena(table.getDiceArena().getRepresentation()));
        notify(new ModelChangedMessageRefresh(this.gamePhase, Integer.toString(table.getRoundTrack().getCurrentRound().getIdPlayerPlaying())));
    }

    private void updateToolCard(int idPlayer, int idToolCard){
        ToolCardContainer toolCardContainer = this.table.getToolCardContainer();
        table.getPlayers(idPlayer).setTokens(table.getPlayers(idPlayer).getTokens() - toolCardContainer.getToolCard(idToolCard).cost());

        this.table.getToolCardContainer().getToolCard(idToolCard).setUsed();
        this.table.getPlayers(idPlayer).setHasUsedToolCardThisTurn(true);

        ToolCard toolCard = table.getToolCardContainer().getToolCard(idToolCard);

        notify(new ModelChangedMessageDiceArena(table.getDiceArena().getRepresentation()));

        notify(new ModelChangedMessageTokensLeft(Integer.toString(idPlayer), Integer.toString(table.getPlayers(idPlayer).getTokens())));
        notify(new ModelChangedMessageToolCard(Integer.toString(idToolCard), toolCard.getName(), toolCard.getDescription(), Integer.toString(toolCard.cost())));
    }
}

