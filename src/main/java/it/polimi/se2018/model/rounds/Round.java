package it.polimi.se2018.model.rounds;

import it.polimi.se2018.model.container.*;

public class Round {

    private int id;

    private int idPlayerPlaying;

    private int[] rolledDiceLeft;

    private DiceContainer diceContainer;

    public int getId() {
        return id;
    }

    public int getIdPlayerPlaying() {
        return idPlayerPlaying;
    }

    public int[] getRolledDiceLeft() {
        return rolledDiceLeft;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRolledDiceLeft(int[] rolledDiceLeft) {
        this.rolledDiceLeft = rolledDiceLeft;
    }

    public DiceContainer getDiceContainer() { return diceContainer;}
}
