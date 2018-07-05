package it.polimi.se2018.model;

import it.polimi.se2018.model.container.DiceContainerUnsupportedIdException;
import it.polimi.se2018.model.container.Die;
import it.polimi.se2018.model.container.DieRolledValueOutOfBoundException;
import it.polimi.se2018.model.events.*;
import it.polimi.se2018.model.objectives.PrivateObjective;
import it.polimi.se2018.model.objectives.PublicObjective;
import it.polimi.se2018.model.patternCard.*;
import it.polimi.se2018.model.player.*;
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

    private Timer timer;


    public Model(HashMap<Integer, String> players, int turnCountdownLength){
        this.players = players;
        this.timer = new Timer(turnCountdownLength);
    }



    /**
     * This method is called at the start of the game. It's purpose is to initialize 4 different patternCards and
     * a private objective for each player and send them so that they can pick one
     */
    public void initSetup() {
        table = new Table(players);
        this.gamePhase = GamePhase.SETUPPHASE;
        ModelChangedMessageChangeGamePhase modelChangedMessageChangeGamePhase = new ModelChangedMessageChangeGamePhase(gamePhase);
        notify(modelChangedMessageChangeGamePhase);

        for(Integer key : players.keySet()) {
            for (int j = 0; j < 4; j++) {
                PatternCard patternCard = table.getPlayers(key).getPatternCards().get(j);

                notify(new ModelChangedMessagePatternCard(key,
                        players.get(key),
                        patternCard.getId(),
                        patternCard.getName(),
                        patternCard.getDifficulty(),
                        patternCard.getPatternCardRepresentation()));
            }

            PrivateObjective privateObjective = table.getPlayers(key).getPrivateObjective();
            notify(new ModelChangedMessagePrivateObjective(key,
                    privateObjective.getId(),
                    privateObjective.getName(),
                    privateObjective.getDescription()));

        }
        notify(new ModelChangedMessageRefresh(null, null));

        timer.setModel(this);
        timer.startTimer();
    }

    /**
     * This methods is called when the main part of the game starts. It creates 3 public objectives, 3 tool cards and it
     * sends the players all the info about the game: their chosen patternCard, the dice on the patternCard (at the
     * beginning it's obviously there are no dice), their private objective, their tokens left, the draft pool.
     * In the case the player managed to choose a patternCard that is not available, it sends them a message.
     * These info are sent and memorized in the ViewClientConsoleGame and
     * It also starts the timer of 90 seconds for the first player playing
     */
    private void initGame() {
        timer.reStartTimer();
        table.checkPlayerDidNotDisconnectDuringPatternCardSelection();

        ModelChangedMessageRefresh modelChangedMessageRefresh;
        this.gamePhase = GamePhase.GAMEPHASE;
        ModelChangedMessageChangeGamePhase modelChangedMessageChangeGamePhase = new ModelChangedMessageChangeGamePhase(gamePhase);
        notify(modelChangedMessageChangeGamePhase);

        for(Integer key : players.keySet()) {
            PatternCard patternCard = table.getPlayers(key).getChosenPatternCard();

            notify(new ModelChangedMessagePatternCard(key,
                    players.get(key),
                    patternCard.getId(),
                    patternCard.getName(),
                    patternCard.getDifficulty(),
                    patternCard.getPatternCardRepresentation()));

            PrivateObjective privateObjective = table.getPlayers(key).getPrivateObjective();
            notify(new ModelChangedMessagePrivateObjective(key,
                    privateObjective.getId(),
                    privateObjective.getName(),
                    privateObjective.getDescription()));

            notify(new ModelChangedMessageTokensLeft(key,
                    table.getPlayers(key).getTokens()));

            notify(new ModelChangedMessageDiceOnPatternCard(key,
                    patternCard.getId(),
                    patternCard.getDiceRepresentation()));

        }

        for(int j = 0; j < 3; j ++) {
            PublicObjective publicObjective = table.getPublicObjective(j);
            notify(new ModelChangedMessagePublicObjective(publicObjective.getId(),
                    publicObjective.getName(),
                    publicObjective.getDescription()));

            ToolCard toolCard = table.getToolCardContainer().getToolCardInPlay().get(j);
            notify(new ModelChangedMessageToolCard(toolCard.getToolCardId(),
                    toolCard.getName(),
                    toolCard.getDescription(),
                    toolCard.cost()));
        }

        notify(new ModelChangedMessageDiceArena(table.getDiceArena().getRepresentation()));

        int playerIdPlaying = table.getRoundTrack().getCurrentRound().getIdPlayerPlaying();

        modelChangedMessageRefresh = new ModelChangedMessageRefresh(playerIdPlaying, players.get(playerIdPlaying));
        notify(modelChangedMessageRefresh);

    }

    /**
     * setChosenPatternCard sets the patternCard chosen by the player. If the player chooses somehow a patternCard that
     * is not available, it selects the first out of the four given to the player
     * @param idPatternCard id selected by the player
     * @param idPlayer id that represents the player view
     */
    public void setChosenPatternCard(int idPatternCard, int idPlayer){

        for(PatternCard patternCard : table.getPlayers(idPlayer).getPatternCards())
            if(idPatternCard == patternCard.getId()) {
                table.getPlayers(idPlayer).setChosenPatternCard(patternCard);
                table.getPlayers(idPlayer).setTokens(patternCard.getDifficulty());

                try {
                    table.checkAllPlayerHasChosenAPatternCard();
                } catch (PlayersHaveAllChosenAPatternCard e) {
                    this.initGame();
                }

                }

        if(!table.getPlayers(idPlayer).hasChosenPatternCard()) {
            PatternCard patternCard = table.getPlayers(idPlayer).getPatternCards().get(0);
            table.getPlayers(idPlayer).setChosenPatternCard(patternCard);
            table.getPlayers(idPlayer).setTokens(patternCard.getDifficulty());
            setPlayerSuspended(idPlayer, true);

            try {
                table.checkAllPlayerHasChosenAPatternCard();
            } catch (PlayersHaveAllChosenAPatternCard e) {
                if(this.gamePhase == GamePhase.SETUPPHASE)
                    this.initGame();
            }

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


                notify(new ModelChangedMessageDiceOnPatternCard(idPlayer, patternCard.getId(), patternCard.getDiceRepresentation()));
                notify(new ModelChangedMessageDiceArena(table.getDiceArena().getRepresentation()));
                int playerIdPlaying = table.getRoundTrack().getCurrentRound().getIdPlayerPlaying();
                notify(new ModelChangedMessageRefresh(playerIdPlaying, players.get(playerIdPlaying)));

            } catch (PlayerHasAlreadySetDieThisTurnException e) {
                notify(new ModelChangedMessageMoveFailed(idPlayer, "Already set a Die this turn"));
            } catch (PatternCardDidNotRespectFirstMoveException e) {
                notify(new ModelChangedMessageMoveFailed(idPlayer, "Did not respect first move constraint"));
            } catch (PatternCardNoAdjacentDieException e) {
                notify(new ModelChangedMessageMoveFailed(idPlayer, "No die adjacent to the cell selected"));
            } catch (PatternCardCellIsOccupiedException e) {
                notify(new ModelChangedMessageMoveFailed(idPlayer, "Already a die in that position"));
            } catch (PatternCardNotRespectingCellConstraintException e) {
                notify(new ModelChangedMessageMoveFailed(idPlayer, "Did not respect cell constraint"));
            } catch (PatternCardNotRespectingNearbyDieExpection patternCardNotRespectingNearbyDieExpection) {
                notify(new ModelChangedMessageMoveFailed(idPlayer, "Not respecting nearby dice colors or values"));
            }  catch (PatternCardAdjacentDieException e) {
                notify(new ModelChangedMessageMoveFailed(idPlayer, "There can't be a die close to the selected cell"));
            } catch (DiceContainerUnsupportedIdException e) {
                e.printStackTrace();
            }
        } else
            notify(new ModelChangedMessageMoveFailed(idPlayer, "Die id was invalid"));

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
        int idPlayerMessage = playerMessageEndTurn.getPlayerId();


        if(isMyTurn(idPlayerMessage) && gamePhase==GamePhase.GAMEPHASE) {
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
                notify(new ModelChangedMessageRound(round.getId(), round.getRepresentation()));

                try {
                    table.getRoundTrack().startNextRound(table);
                } catch (RoundTrackNoMoreRoundsException i) {

                    gamePhase = GamePhase.ENDGAMEPHASE;

                    table.calculateScores();

                    notify(new ModelChangedMessageChangeGamePhase(GamePhase.ENDGAMEPHASE));
                    notify(new ModelChangedMessageEndGame(table.getScoreboard().getRepresentation(), players));

                    timer.stopTimer();
                }
            }
            table.getPlayers(playerMessageEndTurn.getPlayerId()).setHasSetDieThisTurn(false);
            table.getPlayers(playerMessageEndTurn.getPlayerId()).setHasUsedToolCardThisTurn(false);

            int playerIdPlaying = table.getRoundTrack().getCurrentRound().getIdPlayerPlaying();
            notify(new ModelChangedMessageRefresh(playerIdPlaying, players.get(playerIdPlaying)));
        }
    }

    /**
     * This method is called by the thread timer only if the player doesn't end the turn before the 90 seconds timer ends.
     * If it happens during the choosing of the pattern card, it gives the player the first available pattern card.
     * If it happens during the GAMEPHASE, it invokes the method endTurn() and sends a message to the current
     * player playing saying that he run out of time and he is now considered AFK.
     */
    public void timesUp(){

        if(gamePhase == gamePhase.GAMEPHASE) {
            int idPlayerPlaying = table.getRoundTrack().getCurrentRound().getIdPlayerPlaying();
            this.setPlayerSuspended(idPlayerPlaying, true);
        }

        if(gamePhase == GamePhase.SETUPPHASE)
        {
            for(Integer key : players.keySet()) {
                setChosenPatternCard(-1 , key);
            }

        }



    }

    /**
     * @param idPlayer player attempting to make a move
     * @return true if it's player's turn
     */
    public boolean isMyTurn(int idPlayer){
        return  idPlayer == table.getRoundTrack().getCurrentRound().getIdPlayerPlaying();
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
                    notify(new ModelChangedMessageMoveFailed(idPlayer, "Not enough tokens"));
                }
            } else {
                notify(new ModelChangedMessageMoveFailed(idPlayer, "Already used a toolCard this turn"));
            }
        } else {
            notify(new ModelChangedMessageMoveFailed(idPlayer, "ToolCard selected is not in Play"));
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

                    int playerIdPlaying = table.getRoundTrack().getCurrentRound().getIdPlayerPlaying();
                    notify(new ModelChangedMessageRefresh(playerIdPlaying, players.get(playerIdPlaying)));

                } catch (DieRolledValueOutOfBoundException e) {
                    notify(new ModelChangedMessageMoveFailed(idPlayer, "Can't turn a 6 into a 1 or a 6 into a 1!"));
                }

            } catch (DiceContainerUnsupportedIdException e) {
                e.printStackTrace();
            }
        } else
            notify(new ModelChangedMessageMoveFailed(idPlayer, "Die id was invalid"));



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
            notify(new ModelChangedMessageRefresh(idPlayer, players.get(idPlayer)));


        } catch (PatternCardMoveFailedException e) {
            //
        }

    }

    /**
     * This method is needed when you need to make two dice movements inside a pattern card. The way it works is really
     * simple: it performs the first movement: if there is a problem, the movement doesn't go trough and the me thod
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
                notify(new ModelChangedMessageRefresh(idPlayer, players.get(idPlayer)));

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

                   notify(new ModelChangedMessageDiceOnPatternCard(idPlayer, patternCard.getId(), patternCard.getDiceRepresentation()));


               } catch (PatternCardDidNotRespectFirstMoveException e) {
                   notify(new ModelChangedMessageMoveFailed(idPlayer, "Did not respect first move constraint"));
               } catch (PatternCardNoAdjacentDieException e) {
                   notify(new ModelChangedMessageMoveFailed(idPlayer, "No die adjacent to the cell selected"));
               } catch (PatternCardCellIsOccupiedException e) {
                   notify(new ModelChangedMessageMoveFailed(idPlayer, "Already a die in that position"));
               } catch (PatternCardNotRespectingCellConstraintException e) {
                   notify(new ModelChangedMessageMoveFailed(idPlayer, "Did not respect cell constraint"));
               } catch (PatternCardNotRespectingNearbyDieExpection patternCardNotRespectingNearbyDieExpection) {
                   notify(new ModelChangedMessageMoveFailed(idPlayer, "Not respecting nearby dice colors or values"));
               } catch (DiceContainerUnsupportedIdException e) {
                   e.printStackTrace();
               } catch (PatternCardAdjacentDieException e) {
                   notify(new ModelChangedMessageMoveFailed(idPlayer, "There can't be a die close to the selected cell"));
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
               notify(new ModelChangedMessageMoveFailed(idPlayer, "Starting cell is empty"));
           }
        } else {
            notify(new ModelChangedMessageMoveFailed(idPlayer, "Initial and final positions must be different!"));
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

                notify(new ModelChangedMessageRefresh(idPlayer, players.get(idPlayer)));

            } catch (DiceContainerUnsupportedIdException e) {

            }
        } else
            notify(new ModelChangedMessageMoveFailed(idPlayer, "Die id was invalid"));


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

            int playerIdPlaying = table.getRoundTrack().getCurrentRound().getIdPlayerPlaying();
            notify(new ModelChangedMessageRefresh(playerIdPlaying, players.get(playerIdPlaying)));
        } catch (RoundTrackNotInSecondPartOfRoundException e) {
            notify(new ModelChangedMessageMoveFailed(idPlayer, "Can only be used during second turn of round"));
        } catch (PlayerHasAlreadySetDieThisTurnException e) {
            notify(new ModelChangedMessageMoveFailed(idPlayer, "Can only use this card before setting a die"));
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
            notify(new ModelChangedMessageMoveFailed(idPlayer, "Can only use this card during first turn"));
        } catch (PlayerHasNotSetDieThisTurnException playerHasNotSetDieThisTurnException) {
            notify(new ModelChangedMessageMoveFailed(idPlayer, "Can only use this card after setting a die"));
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
                int playerIdPlaying = table.getRoundTrack().getCurrentRound().getIdPlayerPlaying();
                notify(new ModelChangedMessageRefresh(playerIdPlaying, players.get(playerIdPlaying)));

                notify(new ModelChangedMessageNewEvent(idPlayer, "The new value for the die is " + d.getRolledValue()));

            } catch (DiceContainerUnsupportedIdException e) {
                e.printStackTrace();
            }
        } else
            notify(new ModelChangedMessageMoveFailed(idPlayer, "Die id was invalid"));

    }

    /**
     * Method needed for tool card 5. The method first checks if the die di in draft pool is present or not. After that
     * the method gets the actualIdDie (the player will have selected a die id that goes from 0 to 8, while the actual die id
     * can go from 0 to 89) and it puts it in the round selected by the player while it removes the die id in round track
     * selected by the player. If the die is not present in the round track, it catches a DieNotPresentException.
     * After that the method swaps the die in the draft pool: if everything goes fine, the tool card cost and the
     * player tokens gets updated and the method ends. If the die id in dice arena chosen by the player does not exist,
     * the method catches another DieNotPresentException and swaps back the dice in the round track. Whenever it catches
     * and exception, the method sends a notify with a ModelChangedMessageMoveFailed to the player currently playing
     * explaining what went wrong
     * @param idPlayer player id that wants to perform the swap
     * @param dieIdInRoundTrack die id selected by the player on the roundTrack
     * @param idRound round it selected by the player where the dieIdInRoundTrack should be present
     * @param dieIdInDiceArena die id selected by the player in the draft pool
     * @param idToolCard tool card id needed to update tool card cost and player's tokens
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
                    notify(new ModelChangedMessageRound(round.getId(), round.getRepresentation()));

                    updateToolCard(idPlayer, idToolCard);

                    notify(new ModelChangedMessageDiceArena(table.getDiceArena().getRepresentation()));

                    int playerIdPlaying = table.getRoundTrack().getCurrentRound().getIdPlayerPlaying();
                    notify(new ModelChangedMessageRefresh(playerIdPlaying, players.get(playerIdPlaying)));
                } catch (DieNotPresentException e) {
                    roundTrack.swapDieInRound(actualIdDieInDiceArena, idRound, actualIdDieInRoundTrack);
                    notify(new ModelChangedMessageMoveFailed(idPlayer, "Die not Present in DiceArena"));
                }


            } catch (DieNotPresentException e) {
                notify(new ModelChangedMessageMoveFailed(idPlayer, "Die not present in RoundTrack"));
            } catch (RoundHasNotBeenInitializedYetException e) {
                notify(new ModelChangedMessageMoveFailed(idPlayer, "Round selected has not been completed yet"));
            }
        } else
            notify(new ModelChangedMessageMoveFailed(idPlayer, "Die id was invalid"));

    }

    /**
     * This methos serves for tool card number 11.
     * This method removes a die from the draft pool and it sets it back to unrolled and extract a new die, sending the
     * player a ModelChangedMessageNewEvent containing the color of the new die extracted. If the die id choses by the
     * player is not acceptable, the method notifies the player with a ModelChangedMessageMoveFailed
     * @param idPlayer player id choosing the die
     * @param idDie die id chosen by the player to remove from the draft pool
     * @return idNewDie that contains the id of the new die extracted from the dice bag. If die id chosen by the player
     * was not acceptable, it return -1 so that the following steps of the tool card are not executed
     */
    public Integer swapDieWithDieFromDiceBag(int idPlayer, int idDie){
        DiceArena diceArena = table.getDiceArena();

        if(diceArena.getArena().size() > idDie) {

            diceArena.removeDieFromDiceArena(table.getDiceArena().getArena().get(idDie));
            ArrayList<Integer> diceToRoll = table.getDiceContainer().getUnrolledDice();
            Collections.shuffle(diceToRoll);



            int idNewDie = diceToRoll.get(0);
            try {
                Die d;
                d = table.getDiceContainer().getDie(idNewDie);
                notify(new ModelChangedMessageNewEvent(idPlayer, "\nThe new die has the color " + d.getColor()));
                return idNewDie;

            } catch (DiceContainerUnsupportedIdException e) {
                e.printStackTrace();
            }

        } else
            notify(new ModelChangedMessageMoveFailed(idPlayer, "Die Id was incorrect"));

        return -1;
    }

    /**
     * This method purpose is to give the value chosen by the player to the new die extracted from the dice bag during
     * tool card number 11.
     * @param positionInDiceArena the new die is put in the same position of the die chosen by the player to remove
     * @param actualIdDie actual die id (the one that goes from 0 to 89)
     * @param value value (from 1 to 6) chosen by the player to assign to the die extracted
     */
    public void giveValueToDie(int positionInDiceArena, int actualIdDie, int value){

        table.getDiceArena().rollOneDieIntoDiceArena(positionInDiceArena, actualIdDie, value);

        notify(new ModelChangedMessageDiceArena(table.getDiceArena().getRepresentation()));
        int playerIdPlaying = table.getRoundTrack().getCurrentRound().getIdPlayerPlaying();
        notify(new ModelChangedMessageRefresh(playerIdPlaying, players.get(playerIdPlaying)));    }

    /**
     * this method returns a list of available position given a die. It serves for tool card 11 and tool card 6, where
     * the player has to put down the die if possible
     * @param idPlayer player id using the tool card 6 or 11
     * @param idDie die id that the player has to place in its pattern card
     * @return array list containing all the available positions in the player's pattern card for the die
     */
    public ArrayList<Integer> checkAvailablePositions(int idPlayer, int idDie){

        if(table.getDiceArena().getArena().size() > idDie) {
            try {
                return table.getPlayers(idPlayer).getChosenPatternCard().getAvailablePositions(table.getDiceArena().getArena().get(idDie));
            } catch (DiceContainerUnsupportedIdException e) {

            }
        }

        return new ArrayList<>();

    }

    /**
     * this method servers for tool card 12. it checks if the two dice chosen by the player are actually the same color
     * if he chose to move two dice. If the check does not go through, it
     * notify the player with a ModelChangedMessageMoveFailed
     * @param idPlayer player id using tool card 12
     * @param positions1 starting position of movement 1 ( x and y )
     * @param positions2 starting position of movement 2 ( x and y )
     * @return boolean representing whether or not the two dice had the same color
     */
    public boolean checkDiceColor(int idPlayer, ArrayList<Integer> positions1, ArrayList<Integer> positions2){
        PatternCard patternCard = table.getPlayers(idPlayer).getChosenPatternCard();;
        try {
            Die d1;
            d1 = table.getDiceContainer().getDie(patternCard.getPatternCardCell(positions1.get(0), positions1.get(1)).getRolledDieId());

                table.getRoundTrack().checkColorIsPresentInRoundTrack(d1.getColor());


            if(!positions2.isEmpty()) {
                if (!patternCard.getPatternCardCell(positions1.get(0), positions1.get(1)).isEmpty() && !patternCard.getPatternCardCell(positions2.get(0), positions2.get(1)).isEmpty())
                    try {
                        Die d2;
                        d2 = table.getDiceContainer().getDie(patternCard.getPatternCardCell(positions2.get(0), positions2.get(1)).getRolledDieId());

                        if (d1.getColor() == d2.getColor())
                            return true;
                        else {
                            notify(new ModelChangedMessageMoveFailed(idPlayer, "The Dice have different colors"));
                            return false;
                        }

                    } catch (DiceContainerUnsupportedIdException e) {
                        e.printStackTrace();
                    }

                return false;

            }
        } catch (DieNotPresentException e) {
            notify(new ModelChangedMessageMoveFailed(idPlayer, "There is not a die in the round track with the color of the dice you want to move"));
            return false;
        } catch (DiceContainerUnsupportedIdException e) {
            e.printStackTrace();
        }


        return true;

    }

    /**
     * This methods check if there are at least 2 dice in the pattern card so that the the player can use one of the
     * tool card that give the possibility to move dice inside the pattern card. If the check does not go through, it
     * notify the player with a ModelChangedMessageMoveFailed
     * @param idPlayer player id wanting to use one of the tool cards
     * @return boolean representing whether or not there are at least two dice on the player's pattern card
     */
    public boolean checkMovementPossibility(int idPlayer) {

        if (table.getPlayers(idPlayer).getChosenPatternCard().getNumberOfDiceInThePatternCard() > 1)
            return true;
        else{
            notify(new ModelChangedMessageMoveFailed(idPlayer, "You need at least 2 dice on the pattern card to perform a die movement"));
            return false;
        }

    }

    /**
     * This method checks whether the player has already set a die this turn. If the check does not go through, it
     * notify the player with a ModelChangedMessageMoveFailed
     * @param idPlayer player id wanting to use one of the tool cards
     * @return boolean representing whether or not the player has already set a die this turn
     */
    public boolean checkPlayerCanPlaceDie(int idPlayer){

        if(!table.getPlayers(idPlayer).hasSetDieThisTurn())
            return true;
        else {
            notify(new ModelChangedMessageMoveFailed(idPlayer, "You have already placed a Die this turn"));
            return false;
        }

    }

    /**
     * This method checks whether we are at least at round 2. It is needed to use tool card 5. If the check does not go through, it
     * notify the player with a ModelChangedMessageMoveFailed
     * @param idPlayer player id wanting to use one of the tool cards
     * @return boolean representing whether or not the game is past round 1
     */
    public boolean checkRoundIsPastSecond(int idPlayer){

        if(table.getRoundTrack().getCurrentRound().getId() > 0)
            return true;
        else{
            notify(new ModelChangedMessageMoveFailed(idPlayer, "You can only use this card after round 2"));
            return false;
        }

    }

    /**
     * This method checks if there are any more dice in the dice bag. This can happen if a player uses tool card 11 at
     * the last round in a game of 4 people because all the dice have already been used. If the check does not go through, it
     * notify the player with a ModelChangedMessageMoveFailed
     * @param idPlayer player id wanting to use one of the tool cards
     * @return boolean representing whether or not there are any more dice in the dice bag
     */
    public boolean checkEnoughDiceInDiceBag(int idPlayer){

        if(!table.getDiceContainer().getUnrolledDice().isEmpty())
            return true;
        else{
            notify(new ModelChangedMessageMoveFailed(idPlayer, "There are no more dice in the dice bag"));
            return false;
        }

    }

    /**
     * @return player id currently playing
     */
    public int currentPlayerPlaying() {
        return table.getRoundTrack().getCurrentRound().getIdPlayerPlaying();
    }

    /**
     * This method updates the tool card cost and the player remaining tokens after the use of a tool card. After that,
     * it notifies all the players with the change of the tool card cost and the player who just has used the tool card
     * with the number of tokens left
     * @param idPlayer
     * @param idToolCard
     */
    private void updateToolCard(int idPlayer, int idToolCard){

        int actualIdToolCard = idToolCard - 1;
        ToolCardContainer toolCardContainer = this.table.getToolCardContainer();
        table.getPlayers(idPlayer).setTokens(table.getPlayers(idPlayer).getTokens() - toolCardContainer.getToolCard(actualIdToolCard).cost());

        this.table.getToolCardContainer().getToolCard(actualIdToolCard).setUsed();
        this.table.getPlayers(idPlayer).setHasUsedToolCardThisTurn(true);

        ToolCard toolCard = table.getToolCardContainer().getToolCard(actualIdToolCard);

        notify(new ModelChangedMessageDiceArena(table.getDiceArena().getRepresentation()));

        notify(new ModelChangedMessageTokensLeft(idPlayer, table.getPlayers(idPlayer).getTokens()));
        notify(new ModelChangedMessageToolCard(idToolCard, toolCard.getName(), toolCard.getDescription(), toolCard.cost()));
    }

    /**
     * This method puts a certain player suspended or un-suspended, whether he didn't finish his turn or whether he disconnected. If
     * the number of players remaining is only 1, it finishes the game.
     * @param idPlayer player id that just left or got back in game
     * @param afk boolean representing whether you have to put the player suspended or un-suspended
     */
    public void setPlayerSuspended(int idPlayer, boolean afk){
        try {
            this.table.getPlayers(idPlayer).setSuspended(afk);
            if(afk) {
                this.table.checkActivePlayers();
                if(gamePhase == GamePhase.GAMEPHASE)
                    endTurn(new PlayerMessageEndTurn(idPlayer));
                notify(new ModelChangedMessagePlayerAFK(idPlayer, "\nYou run out of time. You are now suspended. Type anything to get back into the game\n"));
            } else
                this.updatePlayerThatCameBackIntoTheGame(idPlayer);
        } catch (OnlyOnePlayerLeftException e) {

            this.gamePhase = GamePhase.ENDGAMEPHASE;

            notify(new ModelChangedMessagePlayerAFK(idPlayer, "\nYou run out of time. You are now suspended. Type anything to get back into the game\n"));

            notify(new ModelChangedMessageChangeGamePhase(GamePhase.ENDGAMEPHASE));


            for (Integer key : players.keySet()) {
                if(!table.getPlayers(key).isSuspended()) {
                    table.setWinner(key);
                    notify(new ModelChangedMessageOnlyOnePlayerLeft(key, players));
                }
            }

            timer.stopTimer();
        }


    }

    /**
     * When a player comes back into the game after being suspended, it need to get back all the info that he lost.
     * This method sends that player all the info back through notifies necessary to start playing the game again.
     * @param idPlayer player id that got back into the game
     */
    public void updatePlayerThatCameBackIntoTheGame(int idPlayer){

        if(this.gamePhase == GamePhase.GAMEPHASE) {

            notify(new ModelChangedMessageConnected(idPlayer));
            notify(new ModelChangedMessageChangeGamePhase(gamePhase));

            for (Integer key : players.keySet()) {
                PatternCard patternCard = table.getPlayers(key).getChosenPatternCard();

                notify(new ModelChangedMessagePatternCard(key,
                        players.get(key),
                        patternCard.getId(),
                        patternCard.getName(),
                        patternCard.getDifficulty(),
                        patternCard.getPatternCardRepresentation()));

                PrivateObjective privateObjective = table.getPlayers(key).getPrivateObjective();
                notify(new ModelChangedMessagePrivateObjective(key,
                        privateObjective.getId(),
                        privateObjective.getName(),
                        privateObjective.getDescription()));

                notify(new ModelChangedMessageTokensLeft(key,
                        table.getPlayers(key).getTokens()));

                notify(new ModelChangedMessageDiceOnPatternCard(key,
                        patternCard.getId(),
                        patternCard.getDiceRepresentation()));

            }

            for (int j = 0; j < 3; j++) {
                PublicObjective publicObjective = table.getPublicObjective(j);
                notify(new ModelChangedMessagePublicObjective(publicObjective.getId(),
                        publicObjective.getName(),
                        publicObjective.getDescription()));

                ToolCard toolCard = table.getToolCardContainer().getToolCardInPlay().get(j);
                notify(new ModelChangedMessageToolCard(toolCard.getToolCardId(),
                        toolCard.getName(),
                        toolCard.getDescription(),
                        toolCard.cost()));
            }

            notify(new ModelChangedMessageDiceArena(table.getDiceArena().getRepresentation()));

            for (int i = 0; i < table.getRoundTrack().getCurrentRound().getId(); i++) {
                notify(new ModelChangedMessageRound(i, table.getRoundTrack().getRound(i).getRepresentation()));
            }

            int playerIdPlaying = table.getRoundTrack().getCurrentRound().getIdPlayerPlaying();
            notify(new ModelChangedMessageRefresh(playerIdPlaying, players.get(playerIdPlaying)));

            notify(new ModelChangedMessageNewEvent(idPlayer, "You are back in the game!"));

        } else {

            notify(new ModelChangedMessageChangeGamePhase(GamePhase.ENDGAMEPHASE));

            if(table.getRoundTrack().getCurrentRound().getId() == 10)
                notify(new ModelChangedMessageEndGame(table.getScoreboard().getRepresentation(), players));
            else
                notify(new ModelChangedMessageOnlyOnePlayerLeft(table.getWinner(), players));
        }
    }
}

