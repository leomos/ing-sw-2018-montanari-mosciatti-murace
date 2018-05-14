package it.polimi.se2018.model;

public class DiceArena {

    private int[] arena;

    /**
     *
     * @param arena list of dice Id to trhwo in the arena
     */
    public void setArena(int[] arena) {
        this.arena = arena;
    }

    public int[] getArena() {
        return arena;
    }
}
