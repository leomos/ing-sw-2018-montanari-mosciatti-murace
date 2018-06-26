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

        if(table.getDiceArena().getArena().size() > idDie) {
            int actualIdDie = table.getDiceArena().getArena().get(idDie);

            try {
                table.getPlayers(idPlayer).setDieInPatternCard(actualIdDie, x, y, false, false, ignoreAdjency);

                if (patternCard.isFirstMove())
                    patternCard.setFirstMove();

                if (idToolCard != -1)
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
            } catch (PlayerHasAlreadySetDieThisTurnException e) {
                notify(new ModelChangedMessageMoveFailed(Integer.toString(idPlayer), "Already set a Die this turn"));
            } catch (PatternCardAdjacentDieException e) {
                notify(new ModelChangedMessageMoveFailed(Integer.toString(idPlayer), "There can't be a die close to the selected cell"));
            } catch (DiceContainerUnsupportedIdException e) {
                e.printStackTrace();
            }
        } else
            notify(new ModelChangedMessageMoveFailed(Integer.toString(idPlayer), "idDie non present in diceArena"));

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
                table.calculateScores();

                notify(new ModelChangedMessageRefresh(GamePhase.ENDGAMEPHASE, null));
                notify(new ModelChangedMessageEndGame(table.getScoreboard().getRepresentation()));
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
                if(currentPlayer.getTokens() >= toolCardContainer.getToolCard(idToolCard - 1).cost()){
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

        if(table.getDiceArena().getArena().size() > idDie) {
            Die d = null;
            try {
                d = table.getDiceContainer().getDie(table.getDiceArena().getArena().get(idDie));
            } catch (DiceContainerUnsupportedIdException e) {
                e.printStackTrace();
            }


            try {
                if (changeValue == 1)
                    d.setRolledValue(d.getRolledValue() + 1);
                else
                    d.setRolledValue(d.getRolledValue() - 1);

                updateToolCard(idPlayer, idToolCard);

                notify(new ModelChangedMessageDiceArena(table.getDiceArena().getRepresentation()));

                notify(new ModelChangedMessageRefresh(this.gamePhase, Integer.toString(table.getRoundTrack().getCurrentRound().getIdPlayerPlaying())));

            } catch (DieRolledValueOutOfBoundException e) {
                notify(new ModelChangedMessageMoveFailed(Integer.toString(idPlayer), "Can't turn a 6 into a 1 or a 6 into a 1!"));
            }
        } else
            notify(new ModelChangedMessageMoveFailed(Integer.toString(idPlayer), "There can't be a die close to the selected cell"));



    }

    public void moveDieInsidePatternCard(int idPlayer, ArrayList<Integer> positions, boolean ignoreValueConstraint, boolean ignoreColorConstraint, int idToolCard){

        try {
            performMoveDieInsidePatternCard(idPlayer, positions.get(0), positions.get(1), positions.get(2), positions.get(3), ignoreValueConstraint, ignoreColorConstraint);

            updateToolCard(idPlayer, idToolCard);
            notify(new ModelChangedMessageRefresh(gamePhase, Integer.toString(idPlayer)));

        } catch (PatternCardMoveFailedException e) {
            //
        }

    }

    public void moveTwoDiceInsidePatternCard(int idPlayer, ArrayList<Integer> positions1, ArrayList<Integer>positions2, int idToolCard){

        try {
            performMoveDieInsidePatternCard(idPlayer, positions1.get(0), positions1.get(1), positions1.get(2), positions1.get(3), false, false);

            try {
                performMoveDieInsidePatternCard(idPlayer, positions2.get(0), positions2.get(1), positions2.get(2), positions2.get(3), false, false);

                updateToolCard(idPlayer, idToolCard);

                notify(new ModelChangedMessageRefresh(gamePhase, Integer.toString(idPlayer)));

            } catch (PatternCardMoveFailedException e){
                performMoveDieInsidePatternCard(idPlayer, positions1.get(2), positions1.get(3), positions1.get(0), positions1.get(1), true, true);
                //
            }
        } catch (PatternCardMoveFailedException e) {
            //
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
     */
    /*TODO: a seconda dell'esito di setRolledDieID dovremo creare notify diversi!*/
    public void performMoveDieInsidePatternCard(int idPlayer,
                                         int x_i,
                                         int y_i,
                                         int x_f,
                                         int y_f,
                                         boolean ignoreValueConstraint,
                                         boolean ignoreColorConstraint) throws PatternCardMoveFailedException {

        //todo check start and final positions are different

        Player currentPlayer = this.table.getPlayers(idPlayer);
        PatternCard patternCard = currentPlayer.getChosenPatternCard();
        int idDie = patternCard.getPatternCardCell(x_i, y_i).getRolledDieId();

        try {
            patternCard.getPatternCardCell(x_i, y_i).removeDie();

            boolean moveFailed = true;

            try {

                patternCard.setDieInPatternCard(idDie, x_f, y_f, ignoreValueConstraint, ignoreColorConstraint, false);
                moveFailed = false;

                notify(new ModelChangedMessageDiceOnPatternCard(Integer.toString(idPlayer), Integer.toString(patternCard.getId()), patternCard.getDiceRepresentation()));


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
                    throw new PatternCardMoveFailedException();
                } catch (DiceContainerUnsupportedIdException e) {
                    e.printStackTrace();
                }
            }

        } catch (CellIsEmptyException e) {
            notify(new ModelChangedMessageMoveFailed(Integer.toString(idPlayer), "Starting cell is empty"));
        }
    }

    public void turnDieAround(int idPlayer, int idDie, int idToolCard) {

        if (table.getDiceArena().getArena().size() > idDie) {

            try {
                table.getDiceContainer().getDie(table.getDiceArena().getArena().get(idDie)).turnAround();

                updateToolCard(idPlayer, idToolCard);

                notify(new ModelChangedMessageDiceArena(table.getDiceArena().getRepresentation()));

                notify(new ModelChangedMessageRefresh(gamePhase, Integer.toString(idPlayer)));

            } catch (DiceContainerUnsupportedIdException e) {

            }
        } else
            notify(new ModelChangedMessageMoveFailed(Integer.toString(idPlayer), "There can't be a die close to the selected cell"));


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

        if(table.getDiceArena().getArena().size() > idDie) {

            try {
                Die d = table.getDiceContainer().getDie(table.getDiceArena().getArena().get(idDie));

                updateToolCard(idPlayer, idToolCard);
                d.roll();

                notify(new ModelChangedMessageDiceArena(table.getDiceArena().getRepresentation()));
                notify(new ModelChangedMessageRefresh(this.gamePhase, Integer.toString(table.getRoundTrack().getCurrentRound().getIdPlayerPlaying())));


                notify(new ModelChangedMessageNewEvent(Integer.toString(idPlayer), "The new value for the die is " + d.getRolledValue()));

            } catch (DiceContainerUnsupportedIdException e) {
                e.printStackTrace();
            }
        } else
            notify(new ModelChangedMessageMoveFailed(Integer.toString(idPlayer), "There can't be a die close to the selected cell"));

    }

    public void swapDieAmongRoundTrackAndDiceArena(int idPlayer, int dieIdInRoundTrack, int idRound, int dieIdInDiceArena, int idToolCard) {
        RoundTrack roundTrack = table.getRoundTrack();
        DiceArena diceArena = table.getDiceArena();

        if(table.getDiceArena().getArena().size() > dieIdInDiceArena) {

            int actualIdDieInDiceArena = table.getDiceArena().getArena().get(dieIdInDiceArena);

            try {
                int actualIdDieInRoundTrack = roundTrack.swapDieInRound(dieIdInRoundTrack, idRound, actualIdDieInDiceArena);

                try {
                    diceArena.swapDie(actualIdDieInDiceArena, actualIdDieInRoundTrack);

                    Round round = table.getRoundTrack().getRound(idRound);
                    notify(new ModelChangedMessageRound(Integer.toString(round.getId()), round.getRepresentation()));

                    updateToolCard(idPlayer, idToolCard);

                    notify(new ModelChangedMessageDiceArena(table.getDiceArena().getRepresentation()));

                    notify(new ModelChangedMessageRefresh(this.gamePhase, Integer.toString(table.getRoundTrack().getCurrentRound().getIdPlayerPlaying())));

                } catch (DieNotPresentException e) {
                    roundTrack.swapDieInRound(actualIdDieInDiceArena, idRound, actualIdDieInRoundTrack);
                    notify(new ModelChangedMessageMoveFailed(Integer.toString(idPlayer), "Die not Present in DiceArena"));
                }


            } catch (DieNotPresentException e) {
                notify(new ModelChangedMessageMoveFailed(Integer.toString(idPlayer), "Die not present in RoundTrack"));
            } catch (RoundHasNotBeenInitializedYetException e) {
                notify(new ModelChangedMessageMoveFailed(Integer.toString(idPlayer), "Round selected has not been completed yet"));
            }
        } else
            notify(new ModelChangedMessageMoveFailed(Integer.toString(idPlayer), "There can't be a die close to the selected cell"));

    }

    public Integer swapDieWithDieFromDiceBag(int idPlayer, int idDie, int idToolCard){
        DiceArena diceArena = table.getDiceArena();

        if(diceArena.getArena().size() > idDie) {

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

            notify(new ModelChangedMessageNewEvent(Integer.toString(idPlayer), "\nThe new die has the color " + d.getColor()));
            return idNewDie;
        } else
            notify(new ModelChangedMessageMoveFailed(Integer.toString(idPlayer), "There can't be a die close to the selected cell"));

        return null;
    }

    public void giveValueToDie(int idPlayer, int positionInDiceArena, int actualIdDie, int value){

        table.getDiceArena().rollOneDieIntoDiceArena(positionInDiceArena, actualIdDie, value);

        notify(new ModelChangedMessageDiceArena(table.getDiceArena().getRepresentation()));
        notify(new ModelChangedMessageRefresh(this.gamePhase, Integer.toString(table.getRoundTrack().getCurrentRound().getIdPlayerPlaying())));
    }

    public ArrayList<Integer> checkAvailablePositions(int idPlayer, int idDie){

        try {
            return table.getPlayers(idPlayer).getChosenPatternCard().getAvailablePositions(table.getDiceArena().getArena().get(idDie));
        } catch (DiceContainerUnsupportedIdException e) {
            //niente da fare qui
        }

        return new ArrayList<>();

    }

    public boolean checkDiceColor(int idPlayer, ArrayList<Integer> positions1, ArrayList<Integer> positions2){

        PatternCard patternCard = table.getPlayers(idPlayer).getChosenPatternCard();
        Die d1 = null, d2 = null;

        if(!patternCard.getPatternCardCell(positions1.get(0), positions1.get(1)).isEmpty() && !patternCard.getPatternCardCell(positions1.get(0), positions1.get(1)).isEmpty())
        try {
            d1 = table.getDiceContainer().getDie(patternCard.getPatternCardCell(positions1.get(0), positions1.get(1)).getRolledDieId());
            d2 = table.getDiceContainer().getDie(patternCard.getPatternCardCell(positions2.get(0), positions2.get(1)).getRolledDieId());
        } catch (DiceContainerUnsupportedIdException e) {
            e.printStackTrace();
        }

        if( d1.getColor() == d2.getColor() )
            return true;
        else {
            notify(new ModelChangedMessageMoveFailed(Integer.toString(idPlayer), "The Dice have different colors"));
            return false;
        }


    }

    public boolean checkMovementPossibility(int idPlayer) {

        if (table.getPlayers(idPlayer).getChosenPatternCard().getNumberOfDiceInThePatternCard() > 1)
            return true;
        else{
            notify(new ModelChangedMessageMoveFailed(Integer.toString(idPlayer), "You need at least 2 dice on the pattern card to perform a die movement"));
            return false;
        }

    }

    public boolean checkRoundIsPastSecond(int idPlayer){

        if(table.getRoundTrack().getCurrentRound().getId() > 0)
            return true;
        else{
            notify(new ModelChangedMessageMoveFailed(Integer.toString(idPlayer), "You can only use this card after round 2"));
            return false;
        }

    }

    public boolean checkEnoughDiceInDiceBag(int idPlayer){

        if(table.getDiceContainer().getUnrolledDice().size() != 0)
            return true;
        else{
            notify(new ModelChangedMessageMoveFailed(Integer.toString(idPlayer), "There are no more dice in the dice bag"));
            return false;
        }

    }

    private void updateToolCard(int idPlayer, int idToolCard){

        int actualIdToolCard = idToolCard - 1;
        ToolCardContainer toolCardContainer = this.table.getToolCardContainer();
        table.getPlayers(idPlayer).setTokens(table.getPlayers(idPlayer).getTokens() - toolCardContainer.getToolCard(actualIdToolCard).cost());

        this.table.getToolCardContainer().getToolCard(actualIdToolCard).setUsed();
        this.table.getPlayers(idPlayer).setHasUsedToolCardThisTurn(true);

        ToolCard toolCard = table.getToolCardContainer().getToolCard(actualIdToolCard);

        notify(new ModelChangedMessageDiceArena(table.getDiceArena().getRepresentation()));

        notify(new ModelChangedMessageTokensLeft(Integer.toString(idPlayer), Integer.toString(table.getPlayers(idPlayer).getTokens())));
        notify(new ModelChangedMessageToolCard(Integer.toString(idToolCard), toolCard.getName(), toolCard.getDescription(), Integer.toString(toolCard.cost())));
    }
}

