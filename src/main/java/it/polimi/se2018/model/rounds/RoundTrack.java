package it.polimi.se2018.model.rounds;

import it.polimi.se2018.model.DieNotPresentException;
import it.polimi.se2018.model.Player;

import java.util.ArrayList;

/* TODO: test for swapDieInRound */
public class RoundTrack {

    public static final int NUMBER_OF_ROUNDS = 10;

    private ArrayList<Integer> players = new ArrayList<>();

    private int idFirstPlayerPlaying = 0;

    private Round[] rounds = new Round[NUMBER_OF_ROUNDS];

    private int currentRoundId = -1;

    public RoundTrack(ArrayList<Player> players) {
        for(Player player : players) {
            this.players.add(player.getId());
        }
    }

    public Round getCurrentRound() {
        return rounds[currentRoundId];
    }

    /**
     * Increments currentRoundId and calls initNewRound after that.
     * @throws  RoundTrackNoMoreRoundsException if this is
     *          called more than NUMBER_OF_ROUNDS times. This means
     *          that more than NUMBER_OF_ROUNDS rounds are trying to be
     *          played.
     */
    public void startNextRound() throws RoundTrackNoMoreRoundsException {
        if(isLastRound()) {
            throw new RoundTrackNoMoreRoundsException();
        }
        currentRoundId++;
        initNewRound();
    }

    /**
     * Set the dice left in the
     * current round's rolledDiceLeft
     * @throws  RoundTrackTooManyDiceForCurrentPlayers if rolledDiceLeft
     *          parameter' size is bigger than (players.size()*2 + 1)
     */
    public void setRolledDiceLeftForCurrentRound(int[] rolledDiceLeft) throws RoundTrackTooManyDiceForCurrentPlayers {
        if(rolledDiceLeft.length > (players.size()*2 + 1)) {
            throw new RoundTrackTooManyDiceForCurrentPlayers();
        }
        rounds[currentRoundId].setRolledDiceLeft(rolledDiceLeft);
    }

    public Round getRound(int x) {
        return rounds[x];
    }

    public boolean isLastRound(){
        return currentRoundId == NUMBER_OF_ROUNDS - 1;
    }

    /**
     * Initialize a new Round object with currentRoundId as it's id.
     * Increments idFirstPlayerPlaying and sets the players for
     * the round.
     * Fills the rounds array with the newly created Round object.
     */
    private void initNewRound() {
        Round newRound = new Round(currentRoundId);
        idFirstPlayerPlaying++;
        try {
            newRound.setPlayers(players);
            newRound.setFirstPlayer( idFirstPlayerPlaying %  players.size() );
            rounds[currentRoundId] = newRound;
        } catch (RoundFirstPlayerAlreadySetException roundFirstPlayerAlreadySet) {
            roundFirstPlayerAlreadySet.printStackTrace();
        }
    }

    /**
     * @param dieId
     * @return the id of the Round to which dieId belongs
     * @throws DieNotPresentException if no Round has dieId in its rolledDiceLeft
     */
    private int getRoundIdForDieId(int dieId) throws DieNotPresentException {
        for (int i = 0; i < rounds.length; i++) {
            if(rounds[i].isDiePresentInLeftDice(dieId)) return i;
        }
        throw new DieNotPresentException();
    }

    /**
     * @param dieIdToRemove
     * @param dieIdToAdd
     * @throws DieNotPresentException   if dieIdToRemove is not present
     *          in any of round's rolledDiceLeft.
     */
    public void swapDieInRound(int dieIdToRemove, int dieIdToAdd) throws DieNotPresentException {
        rounds[getRoundIdForDieId(dieIdToRemove)].swapDie(dieIdToRemove, dieIdToAdd);
    }
}
