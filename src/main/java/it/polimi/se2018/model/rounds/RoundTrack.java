package it.polimi.se2018.model.rounds;

public class RoundTrack {

    private Round[] rounds;

    public Round getRounds(int x) {
        return rounds[x];
    }

    public boolean isFinished(){
        return (getRounds(9) != null);
    }
}
