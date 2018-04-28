package it.polimi.se2018.model.rounds;

public class RoundTrack {

    private Round[] rounds;

    public Round getRound(int x) {
        return rounds[x];
    }

    public boolean isFinished(){
        return (getRound(9) != null);
    }
}
