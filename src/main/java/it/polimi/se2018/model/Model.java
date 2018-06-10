package it.polimi.se2018.model;

import it.polimi.se2018.model.container.DiceContainerUnsupportedIdException;
import it.polimi.se2018.model.container.Die;
import it.polimi.se2018.model.events.*;
import it.polimi.se2018.model.objectives.PrivateObjective;
import it.polimi.se2018.model.objectives.PublicObjective;
import it.polimi.se2018.model.rounds.Round;
import it.polimi.se2018.model.rounds.RoundTrack;
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

    public void setDieInPatternCardFromDiceArena(int idPlayer, int idDie, int x, int y) throws DiceContainerUnsupportedIdException {
        PatternCard patternCard = table.getPlayers(idPlayer).getChosenPatternCard();
        if (!table.getPlayers(idPlayer).hasSetDieThisTurn()){
            if (checkDieInDiceArena(idPlayer, idDie) && checkDieInPatternCard(idPlayer, idDie, x, y, false, false)) {
                int app = table.getDiceArena().getArena().indexOf(idDie);

                if(patternCard.isFirstMove())
                    patternCard.setFirstMove();

                patternCard.getPatternCardCell(x, y).setRolledDieId(idDie, false, false);
                table.getPlayers(idPlayer).setHasSetDieThisTurn(true);
                table.getDiceArena().getArena().remove(app);
                table.getDiceArena().updateRepresentation();
                patternCard.updateDiceRepresentation();
                String idPL = "" + idPlayer;
                String idPC = "" + patternCard.getId();

                notify(new ModelChangedMessageDiceOnPatternCard(idPL, idPC, patternCard.getDiceRepresentation()));
                notify(new ModelChangedMessageDiceArena(table.getDiceArena().getRepresentation()));
                notify(new ModelChangedMessageRefresh(this.gamePhase, Integer.toString(table.getRoundTrack().getCurrentRound().getIdPlayerPlaying())));
            }
        } else{
            notify(new ModelChangedMessageMoveFailed(Integer.toString(idPlayer), "Already set a Die this turn"));
        }
    }

    public boolean checkDieInPatternCard(int idPlayer, int idDie, int x, int y, boolean ignoreValueConstraint, boolean ignoreColorConstraint) throws DiceContainerUnsupportedIdException {
        PatternCard patternCard = table.getPlayers(idPlayer).getChosenPatternCard();
        boolean moveOk = false;
        Die d = table.getDiceContainer().getDie(idDie);

            try {
                if (patternCard.getPatternCardCell(x, y).checkDieValidity(d.getRolledValue(), d.getColor(), ignoreValueConstraint, ignoreColorConstraint)) {
                    if (patternCard.getPatternCardCell(x, y).isEmpty()) {
                        if (patternCard.isFirstMove()) {
                            if (patternCard.checkFirstMove(x, y)) {
                                moveOk = true;
                            } else {
                                notify(new ModelChangedMessageMoveFailed(Integer.toString(idPlayer), "Did not respect First Move rules"));
                            }
                        } else if (patternCard.checkProximityCellsValidity(idDie, x, y) && patternCard.checkDieInAdjacentCells(x, y)) {
                            moveOk = true;
                        } else {
                            notify(new ModelChangedMessageMoveFailed(Integer.toString(idPlayer), "Did not respect Proximity or Adj"));
                        }
                    } else {
                        notify(new ModelChangedMessageMoveFailed(Integer.toString(idPlayer), "Cell is already occupied"));
                    }
                } else {
                    notify(new ModelChangedMessageMoveFailed(Integer.toString(idPlayer), "Not respetting cell constraint"));
                }
            } catch (DiceContainerUnsupportedIdException e) {

            }

        return moveOk;
    }

    public boolean checkDieInDiceArena(int idPlayer, int idDie) throws DiceContainerUnsupportedIdException {
        boolean moveOk = false;
        Die d = table.getDiceContainer().getDie(idDie);
        if (table.getDiceArena().getArena().contains(idDie)) {
            moveOk = true;
        }else {
            notify(new ModelChangedMessageMoveFailed(Integer.toString(idPlayer), "Die not in Dice Arema"));
        }
        return moveOk;
        }



    public void endTurn(PlayerMessageEndTurn playerMessageEndTurn){
        if(!table.getRoundTrack().getCurrentRound().isRoundOver()) {
            table.getRoundTrack().getCurrentRound().setNextPlayer();
        }
        else {
            Round round = table.getRoundTrack().getCurrentRound();
            table.getRoundTrack().setRolledDiceLeftForCurrentRound(table.getDiceArena().getArena());
            round.updateRepresentation();
            table.getDiceArena().rollDiceIntoArena();
            table.getDiceArena().updateRepresentation();

            notify(new ModelChangedMessageDiceArena(table.getDiceArena().getRepresentation()));
            notify(new ModelChangedMessageRound(Integer.toString(round.getId()), round.getRepresentation()));

            table.getRoundTrack().startNextRound();
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
            notify(new ModelChangedMessageMoveFailed(idPL, "ToolCard is not in Play"));
        }

        return toolCardPresent && enoughTokens;
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
                try {
                    if (checkDieInPatternCard(idPlayer, idDie, x_f, y_f, ignoreValueConstraint, ignoreColorConstraint)) {
                        patternCard.getPatternCardCell(x_f, y_f).setRolledDieId(idDie, ignoreValueConstraint, ignoreColorConstraint);
                        patternCard.getPatternCardCell(x_i, y_i).removeDie();

                        currentPlayer.setTokens(currentPlayer.getTokens() - toolCardContainer.getToolCard(idToolCard).cost());

                        this.getTable().getToolCardContainer().getToolCard(idToolCard).setUsed();
                        this.getTable().getPlayers(idPlayer).setHasUsedToolCardThisTurn(true);

                        patternCard.updateDiceRepresentation();
                        String idPC = "" + patternCard.getId();
                        ToolCard toolCard = table.getToolCardContainer().getToolCard(idToolCard);

                        notify(new ModelChangedMessageDiceOnPatternCard(idPL, idPC, patternCard.getDiceRepresentation()));
                        notify(new ModelChangedMessageToolCard(Integer.toString(idToolCard), toolCard.getName(), toolCard.getDescription(), Integer.toString(toolCard.cost())));
                        notify(new ModelChangedMessageRefresh(gamePhase, idPL));

                        }
                } catch (DiceContainerUnsupportedIdException e) {
                    e.printStackTrace();
                }

            } else {
                notify(new ModelChangedMessageMoveFailed(idPL, "One of the two is position is not available"));
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

