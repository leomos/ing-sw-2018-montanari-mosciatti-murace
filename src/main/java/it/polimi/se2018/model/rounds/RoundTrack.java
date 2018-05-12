package it.polimi.se2018.model.rounds;

/* TODO: tests and docs*/
public class RoundTrack {

    private static final int NUMBER_OF_ROUNDS = 10;

    private int[] players;

    private int idFirstPlayerPlaying = 0;

    private Round[] rounds = new Round[NUMBER_OF_ROUNDS];

    private int currentRoundId = -1;

    public RoundTrack(int[] players) {
        this.players = players;
    }

    public Round getCurrentRound() {
        return rounds[currentRoundId];
    }

    public void startNextRound() throws RoundTrackNoMoreRounds {
        if(isLastRound()) {
            throw new RoundTrackNoMoreRounds();
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
            newRound.setFirstPlayer( idFirstPlayerPlaying %  players.length );
            rounds[currentRoundId] = newRound;
        } catch (RoundFirstPlayerAlreadySet roundFirstPlayerAlreadySet) {
            roundFirstPlayerAlreadySet.printStackTrace();
        }
    }

}
