package it.polimi.se2018.model.rounds;

import java.util.ArrayList;

/* TODO: tests and docs*/
public class RoundTrack {

    private static final int NUMBER_OF_ROUNDS = 10;

    private ArrayList<Integer> players;

    private int idFirstPlayerPlaying = 0;

    private Round[] rounds = new Round[NUMBER_OF_ROUNDS];

    private int currentRoundId = -1;

    public RoundTrack(ArrayList<Integer> players) {
        this.players = players;
    }

    public Round getCurrentRound() {
        return rounds[currentRoundId];
    }

    public void startNextRound() throws RoundTrackNoMoreRoundsException {
        if(isLastRound()) {
            throw new RoundTrackNoMoreRoundsException();
        }
        currentRoundId++;
        initNewRound();
    }

    public void endCurrentRound(int[] rolledDiceLeft) {
        rounds[currentRoundId].setRolledDiceLeft(rolledDiceLeft);
    }

    public Round getRound(int x) {
        return rounds[x];
    }

    public boolean isLastRound(){
        return currentRoundId == NUMBER_OF_ROUNDS - 1;
    }

    private void initNewRound() {
        Round newRound = new Round(currentRoundId);
        idFirstPlayerPlaying++;
        try {
            newRound.setPlayers(players);
            newRound.setFirstPlayer( idFirstPlayerPlaying %  players.size() );
            rounds[currentRoundId] = newRound;
        } catch (RoundFirstPlayerAlreadySet roundFirstPlayerAlreadySet) {
            roundFirstPlayerAlreadySet.printStackTrace();
        }
    }

}
