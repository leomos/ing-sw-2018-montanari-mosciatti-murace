package it.polimi.se2018.model.rounds;

import java.util.ArrayList;
import java.util.Collections;

/*TODO: metodo per toolcard */

public class Round {

    private int id;

    private int idPlayerPlaying = -1;

    private int[] rolledDiceLeft;

    private ArrayList<Integer> players;

    private ArrayList<Integer> turns = new ArrayList<>();

    public Round(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getIdPlayerPlaying() {
        return idPlayerPlaying;
    }

    public int[] getRolledDiceLeft() {
        return rolledDiceLeft;
    }

    public void setPlayers(ArrayList<Integer> players) {
        this.players = players;
    }

    public void setRolledDiceLeft(int[] rolledDiceLeft) {
        this.rolledDiceLeft = rolledDiceLeft;
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


}
