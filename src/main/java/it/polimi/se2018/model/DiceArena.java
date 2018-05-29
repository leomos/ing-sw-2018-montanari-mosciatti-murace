package it.polimi.se2018.model;

import it.polimi.se2018.model.container.DiceContainer;
import it.polimi.se2018.model.container.Die;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/* TODO: aggiungere metodo per settare singolo dado */
/* TODO: aggiungere metodo per rollare tutti i dadi rimasti */
/* TODO: aggiungere metodo per eliminare dado dall'arena */
public class DiceArena {

    private ArrayList<Integer> arena;

    private DiceContainer diceContainer;

    public DiceArena(int numberOfDice , DiceContainer diceContainer) {
        this.diceContainer = diceContainer;

        // should I create a shallow copy instead of using a reference?
        ArrayList<Integer> unrolledDice = diceContainer.getUnrolledDice();

        Collections.shuffle(unrolledDice);
        this.arena = (ArrayList<Integer>)unrolledDice.subList(0, numberOfDice);
    }

    /**
     *
     * @param arena list of dice Id to throw in the arena
     */
    public void setArena(ArrayList<Integer> arena) {
        this.arena = arena;
    }

    public ArrayList<Integer> getArena() {
        return arena;
    }

    /**
     * @param dieIdToRemove
     * @param dieIdToAdd
     * @throws DieNotPresentException if dieIdToRemove is not present arena
     */
    public void swapDie(int dieIdToRemove, int dieIdToAdd) throws DieNotPresentException {
        if(!arena.contains(dieIdToRemove)) throw new DieNotPresentException();
        arena.set(arena.indexOf(dieIdToRemove), dieIdToAdd);
    }

}