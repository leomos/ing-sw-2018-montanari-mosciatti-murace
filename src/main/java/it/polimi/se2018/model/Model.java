package it.polimi.se2018.model;

import it.polimi.se2018.model.container.DiceContainerUnsupportedIdException;
import it.polimi.se2018.model.container.Die;
import it.polimi.se2018.model.container.DieRolledValueOutOfBoundException;
import it.polimi.se2018.model.events.*;
import it.polimi.se2018.model.objectives.PrivateObjective;
import it.polimi.se2018.model.objectives.PublicObjective;
import it.polimi.se2018.model.patternCard.*;
import it.polimi.se2018.model.player.OnlyOnePlayerLeftException;
import it.polimi.se2018.model.player.Player;
import it.polimi.se2018.model.player.PlayerHasAlreadySetDieThisTurnException;
import it.polimi.se2018.model.player.PlayerHasNotSetDieThisTurnException;
import it.polimi.se2018.model.rounds.*;
import it.polimi.se2018.model.toolcards.ToolCard;
import it.polimi.se2018.model.toolcards.ToolCardContainer;
import it.polimi.se2018.utils.Observable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;

public class Model extends Observable<ModelChangedMessage> {

    private GamePhase gamePhase = GamePhase.SETUPPHASE;

    private Table table;

    private HashMap<Integer, String> players;

    private ExecutorService executorService;

    private Timer timer;


    public Model(HashMap<Integer, String> players){
        this.players = players;
        this.timer = new Timer();
    }

    public Table getTable() {
        return table;
    }

    /**
     * This method is called at the start of the game. It's purpose is to initialize 4 different patternCards and
     * a private objective for each player and send them so that they can pick one
     */
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

    /**
     * This methods is called when the main part of the game starts. It creates 3 public objectives, 3 tool cards and it
     * sends the players all the info about the game: their chosen patternCard, the dice on the patternCard (at the
     * beginning it's obviously there are no dice), their private objective, their tokens left, the draft pool.
     * In the case the player managed to choose a patternCard that is not available, it sends them a message.
     * These info are sent and memorized in the ViewClientConsoleGame and
     * It also starts the timer of 90 seconds for the first player playing
     */
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

        for(Integer key : players.keySet()) {
            if(table.getPlayers(key).hasMissBehaved())
                notify(new ModelChangedMessageNewEvent(Integer.toString(key), "IdPatternCard chosen was not present; \nYou are going to automatically get the first one available"));
        }

        timer.setModel(this);

        timer.startTimer();

    }

    /**
     * setChosenPatternCard sets the patternCard chosen by the player. If the player chooses somehow a patternCard that
     * is not available, it selects the first out of the four given to the player
     * @param idPatternCard id selected by the player
     * @param idPlayer id that represents the player view
     */
    public void setChosenPatternCard(int idPatternCard, int idPlayer){

        boolean moveSucceed = false;

        for(PatternCard patternCard : table.getPlayers(idPlayer).getPatternCards())
            if(idPatternCard == patternCard.getId()) {
                table.getPlayers(idPlayer).setChosenPatternCard(patternCard);
                table.getPlayers(idPlayer).setTokens(patternCard.getDifficulty());
                moveSucceed = true;
                }

        if(!moveSucceed) {
            PatternCard patternCard = table.getPlayers(idPlayer).getPatternCards().get(0);
            table.getPlayers(idPlayer).setChosenPatternCard(patternCard);
            table.getPlayers(idPlayer).setTokens(patternCard.getDifficulty());
            table.getPlayers(idPlayer).setHasMissBehaved(true);
        }

    }

    /**
     * This method gives the opportunity to the controller to set a die in the chosen patternCard of the player. It is
     * used both during a normal positioning of a die but it is also used by many tool cards. It checks everything
     * before placing the die and in case something goes wrong, it generates a notify to tell the player what went wrong
     * @param idPlayer player id that made the set of the die in the patternCard
     * @param idDie die id chosen by the player
     * @param x abscissa chosen by the player
     * @param y ordinate chosen by the player
     * @param ignoreAdjacency it is used for the tool card number 9 so that you can place a die away from other dice
     * @param idToolCard in case you are using this method with a tool card. It is needed to update tool card cost and
     *                   the player's number of tokens
     */
    public void setDieInPatternCardFromDiceArena(int idPlayer, int idDie, int x, int y, boolean ignoreAdjacency, int idToolCard) {
        PatternCard patternCard = table.getPlayers(idPlayer).getChosenPatternCard();

        if(table.getDiceArena().getArena().size() > idDie) {
            int actualIdDie = table.getDiceArena().getArena().get(idDie);

            try {
                table.getPlayers(idPlayer).setDieInPatternCard(actualIdDie, x, y, false, false, ignoreAdjacency);

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

            } catch (PlayerHasAlreadySetDieThisTurnException e) {
                notify(new ModelChangedMessageMoveFailed(Integer.toString(idPlayer), "Already set a Die this turn"));
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
            }  catch (PatternCardAdjacentDieException e) {
                notify(new ModelChangedMessageMoveFailed(Integer.toString(idPlayer), "There can't be a die close to the selected cell"));
            } catch (DiceContainerUnsupportedIdException e) {
                e.printStackTrace();
            }
        } else
            notify(new ModelChangedMessageMoveFailed(Integer.toString(idPlayer), "Die id was invalid"));

    }

    /**
     * Method to create the end of a turn. In case it was the last turn of the round, it's gonna start a new round
     * and put the dice left on the draft pool in the round finished which is gonna be sent to the player. It then
     * sends the player of this game a ModelChangedMessageRefresh which contains the player id who will be playing now.
     * It also stops the current timer and stars a new one. It resets to false the setHasSetDieThisTurn and
     * setHasUsedToolCardThisTurn of the player who is about to start his turn
     * If it is the last turn, it changes the game phase to ENDGAME
     * @param playerMessageEndTurn it's the message generated by the player; it contains the id that represents the
     *                             player who just finished its turn
     */
    public void endTurn(PlayerMessageEndTurn playerMessageEndTurn){

        //todo: problema se il giocatore ha due turni consecutivi e si disconnette al primo
        int idPlayerMessage = playerMessageEndTurn.getPlayer();


        if(idPlayerMessage == table.getRoundTrack().getCurrentRound().getIdPlayerPlaying()) {
            System.out.println("Il giocatore " + idPlayerMessage + " ha mandato l'end turn");
            timer.reStartTimer();


                try {
                    table.getRoundTrack().getCurrentRound().setNextPlayer(table);
                } catch (RoundFinishedException e) {

                Round round = table.getRoundTrack().getCurrentRound();
                table.getRoundTrack().setRolledDiceLeftForCurrentRound(table.getDiceArena().getArena());
                round.updateRepresentation();
                table.getDiceArena().rollDiceIntoArena();

                notify(new ModelChangedMessageDiceArena(table.getDiceArena().getRepresentation()));
                notify(new ModelChangedMessageRound(Integer.toString(round.getId()), round.getRepresentation()));

                try {
                    table.getRoundTrack().startNextRound(table);
                } catch (RoundTrackNoMoreRoundsException i) {
                    table.calculateScores();

                    notify(new ModelChangedMessageRefresh(GamePhase.ENDGAMEPHASE, null));
                    notify(new ModelChangedMessageEndGame(table.getScoreboard().getRepresentation()));

                    timer.stopTimer();
                }
            }
            table.getPlayers(playerMessageEndTurn.getPlayer()).setHasSetDieThisTurn(false);
            table.getPlayers(playerMessageEndTurn.getPlayer()).setHasUsedToolCardThisTurn(false);

            notify(new ModelChangedMessageRefresh(this.gamePhase, Integer.toString((table.getRoundTrack().getCurrentRound().getIdPlayerPlaying()))));

        }
    }

    /**
     * This method is called by the thread timer only if the player doesn't end the turn before the 90 seconds timer ends.
     * It invokes the method endTurn() and sends a message to the current player playing saying that he run out of time
     * and he is now considered AFK.
     */
    public void timesUp(){

        int idPlayerPlaying = table.getRoundTrack().getCurrentRound().getIdPlayerPlaying();
        this.setPlayerSuspended(idPlayerPlaying,true);

        notify(new ModelChangedMessagePlayerAFK(Integer.toString(idPlayerPlaying), "You run out of time. You are now suspended. Type anything to get back into the game"));

        endTurn(new PlayerMessageEndTurn(idPlayerPlaying));

    }

    /**
     * checkToolCard controls if the tool card chosen by the player is actually "on the table" and whether or not the
     * player has used a tool card this turn or has not enough tokens. In case of negative response, it generates
     * a error message that is gonna be sent to the player that was trying to use the tool card.
     * @param idPlayer player id that tried to use the tool card
     * @param idToolCard tool card id chosen by the player
     * @return true if the player can actually use the tool card, false otherwise
     */
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


    /**
     * this method is invoked when the player tries to use tool card number 1. it checks whether or not die id is
     * acceptable and if the player is trying to turn a 6 into a 1 or a 1 into a six. In case something goes wrong, it
     * generates a error message, otherwise it changes the value of the die by one and then sends the updated draft
     * pool to all the players in this game. It also sets that the player has used a tool card this turn and it
     * removes the tokens from the player equal to the cost of the tool card.
     * @param idPlayer player id using this tool card
     * @param idDie die id that the player wants to change
     * @param changeValue 0 if the player want to decrease the value of the die, 1 if the player wants to
     *                    increase the value of the die
     * @param idToolCard tool card id necessary to update the player's tokens and the tool card cost
     */
    public void incrementOrDecrementDieValue(int idPlayer, int idDie, int changeValue, int idToolCard) {

        if(table.getDiceArena().getArena().size() > idDie) {
            try {
                Die d = table.getDiceContainer().getDie(table.getDiceArena().getArena().get(idDie));



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

            } catch (DiceContainerUnsupportedIdException e) {
                e.printStackTrace();
            }
        } else
            notify(new ModelChangedMessageMoveFailed(Integer.toString(idPlayer), "Die id was invalid"));



    }

    /**
     * This method is used by all the tool card that want to move only one die inside a pattern card. if the moves is
     * fine, it updates the tool card cost and the player's tokens. In case performMoveDieInsidePatternCard doesn't
     * end well and it catches a PatternCardMoveFailedException, there is no need to do anything because the player
     * has been notified of his mistake by setDieInPatternCard.
     * @param idPlayer player id that used the tool card
     * @param positions list of integer containing the starting and final position of the die movement, both
     *                  characterized by a abscissa and a ordinate
     * @param ignoreValueConstraint needed for tool card number 3 so that you can ignore value constraints on the cell
     * @param ignoreColorConstraint needed for tool card number 2 so that you can ignore color constraints on the cell
     * @param idToolCard tool card id necessary to update tool card cost and player's tokens
     */
    public void moveDieInsidePatternCard(int idPlayer, ArrayList<Integer> positions, boolean ignoreValueConstraint, boolean ignoreColorConstraint, int idToolCard){

        try {

            performMoveDieInsidePatternCard(idPlayer, positions.get(0), positions.get(1), positions.get(2), positions.get(3), ignoreValueConstraint, ignoreColorConstraint);

            updateToolCard(idPlayer, idToolCard);
            notify(new ModelChangedMessageRefresh(gamePhase, Integer.toString(idPlayer)));


        } catch (PatternCardMoveFailedException e) {
            //
        }

    }

    /**
     * This method is needed when you need to make two dice movements inside a pattern card. The way it works is really
     * simple: it performs the first movement: if there is a problem, the movement doesn't go trough and the method
     * ends; if it does go through, it performs the second movement: if the is no problem, it updates the tool card cost
     * and the player's tokens. If the second movements doesn't go trough, the first movement is reversed and to make
     * sure it goes trough, both ignoreValueConstraint and ignoreColorConstraint are set to true
     * @param idPlayer player id using the tool card
     * @param positions1 positions relative to the firm movement: list of integer containing the starting and final
     *                   position of the die movement, both characterized by a abscissa and a ordinate
     * @param positions2 positions relative to the second movement: list of integer containing the starting and final
     *                  position of the die movement, both characterized by a abscissa and a ordinate
     * @param idToolCard tool card id necessary to update tool card cost and player's tokens
     */
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
     * This method performs the movement of the die inside the pattern card. First it checks that the starting and final
     * position are different. Then it invokes the method setDieInPatternCard and catches all the possible exceptions
     * called by that method. In case the movement doesn't go trough, it throw a PatternCardMoveFailedException
     * @param idPlayer player id that wants to perform the die movement
     * @param x_i starting ordinate of the movement: ordinate where the die is before the movement
     * @param y_i starting abscissa of the movement: abscissa where the die is before the movement
     * @param x_f final ordiante of the movement: ordinate where the die is after the movement
     * @param y_f final abscissa of the movement: abscissa where the die is after the movement
     * @param ignoreValueConstraint this parameter is needed to know whether or not i can ignore the cell value constraint
     * @param ignoreColorConstraint this parameter is needed to know whether or not i can ignore the cell color constraint
     * @throws PatternCardMoveFailedException in case the movement doesn't go through
     */
    private void performMoveDieInsidePatternCard(int idPlayer,
                                         int x_i,
                                         int y_i,
                                         int x_f,
                                         int y_f,
                                         boolean ignoreValueConstraint,
                                         boolean ignoreColorConstraint) throws PatternCardMoveFailedException {


        if(x_i != x_f || y_i != y_f) {

           Player currentPlayer = this.table.getPlayers(idPlayer);
           PatternCard patternCard = currentPlayer.getChosenPatternCard();
           int idDie = patternCard.getPatternCardCell(x_i, y_i).getRolledDieId();

           try{
               boolean moveFailed = true;
               patternCard.getPatternCardCell(x_i, y_i).removeDie();



               try{


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
               } catch (DiceContainerUnsupportedIdException e) {
                   e.printStackTrace();
               } catch (PatternCardAdjacentDieException e) {
                   notify(new ModelChangedMessageMoveFailed(Integer.toString(idPlayer), "There can't be a die close to the selected cell"));
               }

               if (moveFailed) {
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
        } else {
            notify(new ModelChangedMessageMoveFailed(Integer.toString(idPlayer), "Initial and final positions must be different!"));
            throw new PatternCardMoveFailedException();
        }

    }

    /**
     * This method is needed for tool card number 10. It turn around the die. It check whether or not the die id is
     * accepted and in case it's not, it send a notify with ModelChangedMessageMoveFailed as parameter.
     * @param idPlayer player id using this tool card
     * @param idDie die id that the player want to turn around
     * @param idToolCard tool card id needed to update tool card cost and player's tokens
     */
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
            notify(new ModelChangedMessageMoveFailed(Integer.toString(idPlayer), "Die id was invalid"));


    }

    /**
     * Method needed for tool card 7. Rerolls all of the dice inside the draft pool. Can only be used during second
     * turn of round and before setting a die.
     * @param idPlayer player id using this tool card
     * @param idToolCard tool card id needed to update tool card cost and player's tokens
     */
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

    /**
     * Method needed for tool card 9. Can only use this card after setting a die and during first turn of a round
     * @param idPlayer player id using this tool card
     * @param idToolCard tool card id needed to update tool card cost and player's tokens
     */
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

    /**
     * Method needed for tool card 6. First it checks whether or not idDie is accepted for the draft pool. After that
     * it rolls the die again and creates ModelChangedMessageNewEvent telling the player using the tool card the new
     * value of the die
     * @param idPlayer player id using the tool card
     * @param idDie die id that the player want to roll again
     * @param idToolCard tool card id needed to update tool card cost and player's tokens
     */
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
            notify(new ModelChangedMessageMoveFailed(Integer.toString(idPlayer), "Die id was invalid"));

    }

    /**
     * Method needed for tool card 5.
     * @param idPlayer
     * @param dieIdInRoundTrack
     * @param idRound
     * @param dieIdInDiceArena
     * @param idToolCard
     */
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
            notify(new ModelChangedMessageMoveFailed(Integer.toString(idPlayer), "Die id was invalid"));

    }

    public Integer swapDieWithDieFromDiceBag(int idPlayer, int idDie){
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
            notify(new ModelChangedMessageMoveFailed(Integer.toString(idPlayer), "Die Id was incorrect"));

        return -1;
    }

    public void giveValueToDie(int positionInDiceArena, int actualIdDie, int value){

        table.getDiceArena().rollOneDieIntoDiceArena(positionInDiceArena, actualIdDie, value);

        notify(new ModelChangedMessageDiceArena(table.getDiceArena().getRepresentation()));
        notify(new ModelChangedMessageRefresh(this.gamePhase, Integer.toString(table.getRoundTrack().getCurrentRound().getIdPlayerPlaying())));
    }

    public ArrayList<Integer> checkAvailablePositions(int idPlayer, int idDie){

        if(table.getDiceArena().getArena().size() > idDie) {
            try {
                return table.getPlayers(idPlayer).getChosenPatternCard().getAvailablePositions(table.getDiceArena().getArena().get(idDie));
            } catch (DiceContainerUnsupportedIdException e) {
                //niente da fare qui
            }
        }

        return new ArrayList<>();

    }

    public boolean checkDiceColor(int idPlayer, ArrayList<Integer> positions1, ArrayList<Integer> positions2){

        PatternCard patternCard = table.getPlayers(idPlayer).getChosenPatternCard();
        Die d1;
        Die d2;

        if(!patternCard.getPatternCardCell(positions1.get(0), positions1.get(1)).isEmpty() && !patternCard.getPatternCardCell(positions1.get(0), positions1.get(1)).isEmpty())
        try {
            d1 = table.getDiceContainer().getDie(patternCard.getPatternCardCell(positions1.get(0), positions1.get(1)).getRolledDieId());
            d2 = table.getDiceContainer().getDie(patternCard.getPatternCardCell(positions2.get(0), positions2.get(1)).getRolledDieId());

            if( d1.getColor() == d2.getColor() )
                return true;
            else {
                notify(new ModelChangedMessageMoveFailed(Integer.toString(idPlayer), "The Dice have different colors"));
                return false;
            }

        } catch (DiceContainerUnsupportedIdException e) {
            e.printStackTrace();
        }

        return false;



    }

    public boolean checkMovementPossibility(int idPlayer) {

        if (table.getPlayers(idPlayer).getChosenPatternCard().getNumberOfDiceInThePatternCard() > 1)
            return true;
        else{
            notify(new ModelChangedMessageMoveFailed(Integer.toString(idPlayer), "You need at least 2 dice on the pattern card to perform a die movement"));
            return false;
        }

    }

    public boolean checkPlayerCanPlaceDie(int idPlayer){

        if(!table.getPlayers(idPlayer).hasSetDieThisTurn())
            return true;
        else {
            notify(new ModelChangedMessageMoveFailed(Integer.toString(idPlayer), "You have already placed a Die this turn"));
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

        if(!table.getDiceContainer().getUnrolledDice().isEmpty())
            return true;
        else{
            notify(new ModelChangedMessageMoveFailed(Integer.toString(idPlayer), "There are no more dice in the dice bag"));
            return false;
        }

    }

    public int currentPlayerPlaying() {
        return table.getRoundTrack().getCurrentRound().getIdPlayerPlaying();
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

    public void setPlayerSuspended(int idPlayer, boolean afk){
        try {
            table.getPlayers(idPlayer).setSuspended(afk);
            table.checkActivePlayers();
        } catch (OnlyOnePlayerLeftException e) {
            table.calculateScores();
            notify(new ModelChangedMessageRefresh(GamePhase.ENDGAMEPHASE, null));
            notify(new ModelChangedMessageEndGame(table.getScoreboard().getRepresentation()));

            timer.stopTimer();
        }


    }

    public void updatePlayerThatCameBackIntoTheGame(int idPlayer){

        for(Integer key : players.keySet()) {
            PatternCard patternCard = table.getPlayers(key).getChosenPatternCard();

            notify(new ModelChangedMessageDiceOnPatternCard(Integer.toString(key),
                    Integer.toString(patternCard.getId()),
                    patternCard.getDiceRepresentation()));


            notify(new ModelChangedMessageTokensLeft(Integer.toString(key),
                    Integer.toString(table.getPlayers(key).getTokens())));

        }

        for(int j = 0; j < 3; j ++) {

            ToolCard toolCard = table.getToolCardContainer().getToolCardInPlay().get(j);
            notify(new ModelChangedMessageToolCard(Integer.toString(toolCard.getToolCardId()),
                    toolCard.getName(),
                    toolCard.getDescription(),
                    Integer.toString(toolCard.cost())));
        }

        notify(new ModelChangedMessageDiceArena(table.getDiceArena().getRepresentation()));

        //todo: mancano i round

        ModelChangedMessageRefresh modelChangedMessageRefresh = new ModelChangedMessageRefresh(gamePhase, Integer.toString(table.getRoundTrack().getCurrentRound().getIdPlayerPlaying()));
        notify(modelChangedMessageRefresh);

        notify(new ModelChangedMessageNewEvent(Integer.toString(idPlayer), "You are back in the game!"));
    }
}

