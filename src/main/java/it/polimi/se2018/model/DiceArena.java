package it.polimi.se2018.model;

import it.polimi.se2018.model.container.DiceContainer;
import it.polimi.se2018.model.container.DiceContainerUnsupportedIdException;

import java.util.ArrayList;
import java.util.Collections;

/* TODO: aggiungere metodo per settare singolo dado */
/* TODO: aggiungere metodo per rollare tutti i dadi rimasti */
/* TODO: aggiungere metodo per eliminare dado dall'arena */
public class DiceArena {

    private ArrayList<Integer> arena = new ArrayList<Integer>();

    private DiceContainer diceContainer;

    private int numberOfDice;

    public DiceArena(int numberOfDice , DiceContainer diceContainer) {
        this.diceContainer = diceContainer;
        this.numberOfDice = numberOfDice;
    }

    public ArrayList<Integer> getArena() {
        return arena;
    }

    public void rollDiceIntoArena() throws DiceContainerUnsupportedIdException {
        ArrayList<Integer> diceToRoll = diceContainer.getUnrolledDice();
        Collections.shuffle(diceToRoll);
        for(int i = 0; i < numberOfDice; i++){
            diceContainer.getDie(diceToRoll.get(i)).roll();
            arena.add(diceToRoll.get(i));
        }
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