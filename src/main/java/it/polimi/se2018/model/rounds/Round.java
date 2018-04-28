package it.polimi.se2018.model.rounds;

import it.polimi.se2018.model.Die;

public class Round {

    private int id;

    private int idPlayerPlaying;

    private Die[] throwedDiceLeft;

    public int getId() {
        return id;
    }

    public int getIdPlayerPlaying() {
        return idPlayerPlaying;
    }

    public Die[] getThrowedDiceLeft() {
        return throwedDiceLeft;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setThrowedDiceLeft(Die[] throwedDiceLeft) {
        this.throwedDiceLeft = throwedDiceLeft;
    }
}
