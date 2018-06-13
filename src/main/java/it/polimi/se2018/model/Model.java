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

import java.util.HashMap;

public class Model extends Observable<ModelChangedMessage> {

    private GamePhase gamePhase = GamePhase.SETUPPHASE;

    private Table table;

    private HashMap<Integer, String> players = new HashMap<>();

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

    public Table getTable() {
        return table;
    }

    public GamePhase getGamePhase() {
        return gamePhase;
    }

    public void setChosenPatternCard(int idPatternCard, int idPlayer){

        for(PatternCard patternCard : table.getPlayers(idPlayer).getPatternCards())
            if(idPatternCard == patternCard.getId()) {
                table.getPlayers(idPlayer).setChosenPatternCard(patternCard);
                table.getPlayers(idPlayer).setTokens(patternCard.getDifficulty());
                }
    }

    public void setDieInPatternCardFromDiceArena(int idPlayer, int idDie, int x, int y, boolean ignoreAdjency) throws DiceContainerUnsupportedIdException {
        PatternCard patternCard = table.getPlayers(idPlayer).getChosenPatternCard();
            if (checkDieInDiceArena(idPlayer, idDie)) {

                int app = table.getDiceArena().getArena().indexOf(idDie);

                try {
                        table.getPlayers(idPlayer).setDieInPatternCard(idDie, x, y, false, false, ignoreAdjency);


                    if(patternCard.isFirstMove())
                        patternCard.setFirstMove();

                    table.getPlayers(idPlayer).setHasSetDieThisTurn(true);
                    table.getDiceArena().getArena().remove(app);
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
                }
             }
    }

    public boolean checkDieInDiceArena(int idPlayer, int idDie) {

        if (table.getDiceArena().getArena().contains(idDie)) {
            return true;
        }else {
            notify(new ModelChangedMessageMoveFailed(Integer.toString(idPlayer), "Die not in Dice Arema"));
        }
        return false;
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
        Player currentPlayer = this.table.getPlayers(idPlayer);
        ToolCardContainer toolCardContainer = this.table.getToolCardContainer();

        if(checkDieInDiceArena(idPlayer, idDie)){

            Die d = null;
            try {
                d = table.getDiceContainer().getDie(idDie);
            } catch (DiceContainerUnsupportedIdException e) {
                e.printStackTrace();
            }



            try {
                if(changeValue == 1)
                    d.setRolledValue( d.getRolledValue() + 1);
                else
                    d.setRolledValue(d.getRolledValue() - 1);

                currentPlayer.setTokens(currentPlayer.getTokens() - toolCardContainer.getToolCard(idToolCard).cost());

                this.getTable().getToolCardContainer().getToolCard(idToolCard).setUsed();
                this.getTable().getPlayers(idPlayer).setHasUsedToolCardThisTurn(true);

                ToolCard toolCard = table.getToolCardContainer().getToolCard(idToolCard);

                notify(new ModelChangedMessageTokensLeft(Integer.toString(idPlayer), Integer.toString(table.getPlayers(idPlayer).getTokens())));
                notify(new ModelChangedMessageToolCard(Integer.toString(idToolCard), toolCard.getName(), toolCard.getDescription(), Integer.toString(toolCard.cost())));

                notify(new ModelChangedMessageDiceArena(table.getDiceArena().getRepresentation()));

                notify(new ModelChangedMessageRefresh(this.gamePhase, Integer.toString(table.getRoundTrack().getCurrentRound().getIdPlayerPlaying())));

            } catch (DieRolledValueOutOfBoundException e) {
                notify(new ModelChangedMessageMoveFailed(Integer.toString(idPlayer), "Can't turn a 6 into a 1 or a 6 into a 1!"));
            }



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
        ToolCardContainer toolCardContainer = this.table.getToolCardContainer();
        String idPL = "" + idPlayer;


            if (!patternCard.getPatternCardCell(x_i, y_i).isEmpty() &&
                    patternCard.getPatternCardCell(x_f, y_f).isEmpty()) {

                int idDie = patternCard.getPatternCardCell(x_i, y_i).getRolledDieId();
                patternCard.getPatternCardCell(x_i, y_i).removeDie();
                boolean moveFailed = true;
                try {

                    try {
                        patternCard.setDieInPatternCard(idDie, x_f, y_f, ignoreValueConstraint, ignoreColorConstraint, false);
                        moveFailed = false;

                        currentPlayer.setTokens(currentPlayer.getTokens() - toolCardContainer.getToolCard(idToolCard).cost());
                        this.getTable().getToolCardContainer().getToolCard(idToolCard).setUsed();
                        this.getTable().getPlayers(idPlayer).setHasUsedToolCardThisTurn(true);


                        String idPC = "" + patternCard.getId();
                        ToolCard toolCard = table.getToolCardContainer().getToolCard(idToolCard);

                        notify(new ModelChangedMessageToolCard(Integer.toString(idToolCard), toolCard.getName(), toolCard.getDescription(), Integer.toString(toolCard.cost())));
                        notify(new ModelChangedMessageTokensLeft(idPL, Integer.toString(table.getPlayers(idPlayer).getTokens())));

                        notify(new ModelChangedMessageDiceOnPatternCard(idPL, idPC, patternCard.getDiceRepresentation()));

                        notify(new ModelChangedMessageRefresh(gamePhase, idPL));

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
                    }

                    if(moveFailed)
                        patternCard.getPatternCardCell(x_i, y_i).setRolledDieId(idDie, true, true);

                } catch (DiceContainerUnsupportedIdException e) {
                    e.printStackTrace();
                }

            } else {
                notify(new ModelChangedMessageMoveFailed(idPL, "One of the two is position is not available"));
            }

    }

    public void turnDieAround(int idPlayer, int idDie, int idToolCard) {
        Player currentPlayer = this.table.getPlayers(idPlayer);
        ToolCardContainer toolCardContainer = this.table.getToolCardContainer();

        if(checkDieInDiceArena(idPlayer, idDie)){
            try {
                table.getDiceContainer().getDie(idDie).turnAround();

                ToolCard toolCard = table.getToolCardContainer().getToolCard(idToolCard);

                currentPlayer.setTokens(currentPlayer.getTokens() - toolCardContainer.getToolCard(idToolCard).cost());
                this.getTable().getToolCardContainer().getToolCard(idToolCard).setUsed();
                this.getTable().getPlayers(idPlayer).setHasUsedToolCardThisTurn(true);

                notify(new ModelChangedMessageDiceArena(table.getDiceArena().getRepresentation()));

                notify(new ModelChangedMessageToolCard(Integer.toString(idToolCard), toolCard.getName(), toolCard.getDescription(), Integer.toString(toolCard.cost())));
                notify(new ModelChangedMessageTokensLeft(Integer.toString(idPlayer), Integer.toString(table.getPlayers(idPlayer).getTokens())));

                notify(new ModelChangedMessageRefresh(gamePhase, Integer.toString(idPlayer)));

            } catch (DiceContainerUnsupportedIdException e) {

            }
        }
    }

    public void rerollDiceArena(int idPlayer, int idToolCard){

        try {
            table.getDiceArena().rerollDiceArena(table.getRoundTrack(), table.getPlayers(idPlayer));
            ToolCardContainer toolCardContainer = this.table.getToolCardContainer();
            Player currentPlayer = table.getPlayers(idPlayer);

            currentPlayer.setTokens(currentPlayer.getTokens() - toolCardContainer.getToolCard(idToolCard).cost());

            this.getTable().getToolCardContainer().getToolCard(idToolCard).setUsed();
            this.getTable().getPlayers(idPlayer).setHasUsedToolCardThisTurn(true);

            ToolCard toolCard = table.getToolCardContainer().getToolCard(idToolCard);

            notify(new ModelChangedMessageDiceArena(table.getDiceArena().getRepresentation()));

            notify(new ModelChangedMessageTokensLeft(Integer.toString(idPlayer), Integer.toString(table.getPlayers(idPlayer).getTokens())));
            notify(new ModelChangedMessageToolCard(Integer.toString(idToolCard), toolCard.getName(), toolCard.getDescription(), Integer.toString(toolCard.cost())));

            notify(new ModelChangedMessageRefresh(this.gamePhase, Integer.toString(table.getRoundTrack().getCurrentRound().getIdPlayerPlaying())));

        } catch (RoundTrackNotInSecondPartOfRoundException e) {
            notify(new ModelChangedMessageMoveFailed(Integer.toString(idPlayer), "Can only be used during second turn of round"));
        } catch (PlayerHasAlreadySetDieThisTurnException e) {
            notify(new ModelChangedMessageMoveFailed(Integer.toString(idPlayer), "Can only use this card before setting a die"));
        }

    }

    public void swapDieAmongRoundTrackAndDiceArena(int idPlayer, int dieIdInRoundTrack, int dieIdInDiceArena, int idToolCard) {
        RoundTrack roundTrack = table.getRoundTrack();
        DiceArena diceArena = table.getDiceArena();
        Player currentPlayer = this.table.getPlayers(idPlayer);

        if(checkDieInDiceArena(idPlayer, dieIdInDiceArena)) {
            try {
                roundTrack.swapDieInRound(dieIdInRoundTrack, dieIdInDiceArena);

                try {
                    diceArena.swapDie(dieIdInDiceArena, dieIdInRoundTrack);

                    //TODO: update e manda nuovo round -> servirebbe mettere pubblico un metodo privato

                    ToolCardContainer toolCardContainer = this.table.getToolCardContainer();
                    currentPlayer.setTokens(currentPlayer.getTokens() - toolCardContainer.getToolCard(idToolCard).cost());

                    this.getTable().getToolCardContainer().getToolCard(idToolCard).setUsed();
                    this.getTable().getPlayers(idPlayer).setHasUsedToolCardThisTurn(true);

                    ToolCard toolCard = table.getToolCardContainer().getToolCard(idToolCard);

                    notify(new ModelChangedMessageDiceArena(table.getDiceArena().getRepresentation()));

                    notify(new ModelChangedMessageTokensLeft(Integer.toString(idPlayer), Integer.toString(table.getPlayers(idPlayer).getTokens())));
                    notify(new ModelChangedMessageToolCard(Integer.toString(idToolCard), toolCard.getName(), toolCard.getDescription(), Integer.toString(toolCard.cost())));

                    notify(new ModelChangedMessageRefresh(this.gamePhase, Integer.toString(table.getRoundTrack().getCurrentRound().getIdPlayerPlaying())));

                } catch (DieNotPresentException e) {
                    roundTrack.swapDieInRound(dieIdInDiceArena, dieIdInRoundTrack);
                    notify(new ModelChangedMessageMoveFailed(Integer.toString(idPlayer), "Die not Present in Die Arena"));
                }


            } catch (DieNotPresentException e) {
                notify(new ModelChangedMessageMoveFailed(Integer.toString(idPlayer), "Die not present in RoundTrack"));
            }

        }

    }
}

