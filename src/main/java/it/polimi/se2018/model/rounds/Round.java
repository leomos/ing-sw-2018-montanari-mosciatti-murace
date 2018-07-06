package it.polimi.se2018.model.rounds;


import it.polimi.se2018.model.Table;
import it.polimi.se2018.model.container.DieColor;
import it.polimi.se2018.model.player.Player;
import it.polimi.se2018.model.player.PlayerHasNotSetDieThisTurnException;
import it.polimi.se2018.model.container.DiceContainer;
import it.polimi.se2018.model.container.DiceContainerUnsupportedIdException;
import it.polimi.se2018.model.container.Die;

import java.util.ArrayList;
import java.util.Collections;


public class Round {

    private int id;

    private int idPlayerPlaying = -1;

    private ArrayList<Integer> rolledDiceLeft = new ArrayList<Integer>();

    private String representation = "";

    private ArrayList<Integer> players;

    private ArrayList<Integer> turns = new ArrayList<>();

    private DiceContainer diceContainer;

    public Round(int id, DiceContainer diceContainer) {
        this.id = id;
        this.diceContainer = diceContainer;
    }

    public int getId() {
        return id;
    }

    public int getIdPlayerPlaying() {
        return idPlayerPlaying;
    }

    public ArrayList<Integer> getRolledDiceLeft() {
        return rolledDiceLeft;
    }

    /**
     * before returning the representation, it updates it
     * @return string containing the representation of the dice left in the round
     */
    public String getRepresentation(){
        updateRepresentation();
        return representation;}

    public void setPlayers(ArrayList<Integer> players) {
        this.players = players;
    }

    public void setRolledDiceLeft(ArrayList<Integer> rolledDiceLeft) {
        this.rolledDiceLeft.addAll(rolledDiceLeft);
    }

    public boolean isSecondPartOfRound(){
        return turns.size() < players.size();
    }


    /**
     * @param idFirstPlayer is the id that should start the round.
     * @throws RoundFirstPlayerAlreadySetException  if the first player
     *                                              is already set.
     */
    public void setFirstPlayer(int idFirstPlayer) throws RoundFirstPlayerAlreadySetException {
        if(idPlayerPlaying != -1) {
            throw new RoundFirstPlayerAlreadySetException();
        }
        if(idFirstPlayer == 0)
            idPlayerPlaying = players.size();
        else
            idPlayerPlaying = idFirstPlayer;
        createTurns();
    }

    /**
     * This method sets idPlayerPlaying taking the first element
     * from the turns array and then removing it. If the player is suspended,
     * it invokes itself again to go to the next player
     * @param table the table is needed to check if the player playing is suspended
     * @throws RoundFinishedException if no more rounds are available.
     */
    public void setNextPlayer(Table table) throws RoundFinishedException {
        if(!turns.isEmpty()) {
            idPlayerPlaying = turns.get(0);
            turns.remove(0);

            if(table.getPlayers(idPlayerPlaying).isSuspended()) {
                this.setNextPlayer(table);
            }

        } else {
            throw new RoundFinishedException();
        }
    }

    /**
     * Populate the turns array in the following way.
     * Given the players array it makes a copy of it
     * and rotate it as many times as necessary to
     * idPlayerPlaying to be in first position,
     * this rotated array is going to be the first half
     * of the turns array. The second half will be
     * the first half reversed. Then it will remove the
     * first element from the two halves combined.
     * Example: players = [2,5,8]; idPlayerPlaying = 5
     * first half is: [5,8,2],
     * second half is: [2,8,5],
     * turns will be: [8,2,2,8,5]
     */
    private void createTurns() {
        ArrayList<Integer> chunk = (ArrayList<Integer>)players.clone();
        Collections.rotate(chunk, players.size() - players.indexOf(idPlayerPlaying));
        turns.addAll(chunk);
        Collections.reverse(chunk);
        turns.addAll(chunk);
        turns.remove(0);
    }

    /**
     * Creates a single string that represents the dice left in this round. This string is gonna be sent to the player.
     * For each die left, it adds to the string the die id, followed by its color, followed by its rolled value
     */
    public void updateRepresentation(){
        representation = "";
        for(int i = 0; i < rolledDiceLeft.size(); i++){
            Die d;
            try {
                d = diceContainer.getDie(rolledDiceLeft.get(i));
            } catch (DiceContainerUnsupportedIdException e) {
                e.printStackTrace();
                return;
            }
            if(rolledDiceLeft.get(i) < 10)
                representation = representation + "0";
            representation = representation + Integer.toString(rolledDiceLeft.get(i)) + d.getColorChar() + d.getRolledValue();
        }
    }

    /**
     * @param playerId id of the player of which we want to know
     *                 how many turns he has played in this round
     * @return  the number of turns that a player has played.
     *          ATTENTION! If the player passed as parameter is
     *          "playing" at the moment of the call, it counts as
     *          a turn played.
     */
    public int turnsPlayedByPlayer(int playerId) {
        int counter = 0;
        for(int t : turns) {
            counter = playerId == t ? counter + 1 : counter;
        }
        return 2 - counter;
    }


    /**
     * Method needed for tool card n.5
     * @param dieIdToRemove die id to remove from the dice left in this round
     * @param dieIdToAdd die id to add to the dice left in this round
     * This method expects that the two params are valid,
     * that is, they need to be present in rolledDiceLeft.
     */
    public int swapDie(int dieIdToRemove, int dieIdToAdd) throws DieNotPresentException {
        if(dieIdToRemove < rolledDiceLeft.size()) {
            int actualIdDieInRoundTrack = rolledDiceLeft.get(dieIdToRemove);
            rolledDiceLeft.set(dieIdToRemove, dieIdToAdd);

            return actualIdDieInRoundTrack;
        } else
            throw new DieNotPresentException();
    }

    /**
     * This method is needed for tool card n.8 where the player gets the possibility to have two round in a tow
     * @param playerId player id using the tool card
     * @param player player needed to check if it already placed a die this turn
     * @throws RoundPlayerAlreadyPlayedSecondTurnException if the player is trying to use this tool card in the second part of the round
     * @throws PlayerHasNotSetDieThisTurnException if the player tries to use this tool card before placing a die
     */
    public void giveConsecutiveTurnsToPlayer(int playerId, Player player)
            throws RoundPlayerAlreadyPlayedSecondTurnException, PlayerHasNotSetDieThisTurnException {

        if(player.hasSetDieThisTurn())
            throw new PlayerHasNotSetDieThisTurnException();
        if(turnsPlayedByPlayer(playerId) != 1) {
            throw new RoundPlayerAlreadyPlayedSecondTurnException();
        }
        turns.remove(turns.indexOf(playerId));

        player.setHasSetDieThisTurn(false);
    }

    /**
     * This method checks whether or not there is a die of the same color as the param color
     * in the dice left behind when this turn was over
     * @param color die color
     * @return true if there is a die with the correct color, false otherwise
     */
    public boolean checkColorIsPresentInDiceLeft(DieColor color){

        for(int i = 0; i < rolledDiceLeft.size(); i++) {
            try {
                Die d = diceContainer.getDie(rolledDiceLeft.get(i));
                if(color == d.getColor())
                    return true;
            } catch (DiceContainerUnsupportedIdException e) {
                e.printStackTrace();
            }
        }

        return false;



    }
}
