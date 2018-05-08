package it.polimi.se2018.model.rounds;

import it.polimi.se2018.model.container.DiceContainer;
import it.polimi.se2018.model.container.Die;

public class Round {

    private int id;

    private int idPlayerPlaying;

    private int[] throwedDiceLeft;

    private DiceContainer diceContainer;

    public int getId() {
        return id;
    }

    public int getIdPlayerPlaying() {
        return idPlayerPlaying;
    }

    public int[] getThrowedDiceLeft() {
        return throwedDiceLeft;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setThrowedDiceLeft(int[] throwedDiceLeft) {
        this.throwedDiceLeft = throwedDiceLeft;
    }

    public DiceContainer getDiceContainer() { return diceContainer;}
}
