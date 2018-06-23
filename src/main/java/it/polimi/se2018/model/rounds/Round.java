package it.polimi.se2018.model.rounds;


import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.PlayerHasNotSetDieThisTurnException;
import it.polimi.se2018.model.container.DiceContainer;
import it.polimi.se2018.model.container.DiceContainerUnsupportedIdException;
import it.polimi.se2018.model.container.Die;

import java.util.ArrayList;
import java.util.Collections;

/* TODO: test swapDie */
/* TODO: test isDiePresentInLeftDice */
/* TODO: test giveConsecutiveTurnsToPlayer */
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
     * from the turns array and then removing it.
     * @throws RoundFinishedException if no more rounds are available.
     */
    public void setNextPlayer() throws RoundFinishedException {
        if(!turns.isEmpty()) {
            idPlayerPlaying = turns.get(0);
            turns.remove(0);
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

    public void updateRepresentation(){
        representation = "";
        for(int i = 0; i < rolledDiceLeft.size(); i++){
            Die d = null;
            try {
                d = diceContainer.getDie(rolledDiceLeft.get(i));
            } catch (DiceContainerUnsupportedIdException e) {
                e.printStackTrace();
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
     * @param dieIdToRemove
     * @param dieIdToAdd
     * This method expects that the two params are valid,
     * that is, they need to be present in rolledDiceLeft.
     */
    public void swapDie(int dieIdToRemove, int dieIdToAdd) {
        for (int i = 0; i < rolledDiceLeft.size(); i++) {
            if(dieIdToRemove == rolledDiceLeft.get(i)) {
                rolledDiceLeft.add(i, dieIdToAdd);
                return;
            }
        }
    }

    /**
     * @param idDie the id of the die to be searched
     * @return      true if rolledDiceLeft contains dieId,
     *              false otherwise
     */
    public boolean isDiePresentInDiceLeft(int idDie) {
        for (int id : rolledDiceLeft) {
            System.out.println(id);
            if(id == idDie) return true;
        }
        return false;
    }

    public boolean isRoundOver(){
        return turns.isEmpty();
    }

    public void giveConsecutiveTurnsToPlayer(int playerId, Player player)
            throws RoundPlayerAlreadyPlayedSecondTurnException, PlayerHasNotSetDieThisTurnException {

        if(player.hasSetDieThisTurn())
            throw new PlayerHasNotSetDieThisTurnException();
        if(turnsPlayedByPlayer(playerId) != 1) {
            throw new RoundPlayerAlreadyPlayedSecondTurnException();
        }

        /* remove the player from turns array.
         * Its id value should be contained
         * in the array only once.
         */
        turns.remove(turns.indexOf(playerId));

        player.setHasSetDieThisTurn(false);
    }
}
