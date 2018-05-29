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

    private HashMap<Integer, Die> arena;

    private DiceContainer diceContainer;

    public DiceArena(int numberOfDice , DiceContainer diceContainer) {
        this.diceContainer = diceContainer;
        HashMap<Integer, Die> unrolledDice = diceContainer.getUnrolledDice();
        ArrayList<Integer> unrolledDiceKeys = new ArrayList<>(unrolledDice.keySet());
        Collections.shuffle(unrolledDiceKeys);
        unrolledDiceKeys.subList(0, numberOfDice);
    }

    /**
     *
     * @param arena list of dice Id to throw in the arena
     */
    public void setArena(HashMap<Integer, Die> arena) {
        this.arena = arena;
    }

    public HashMap<Integer, Die> getArena() {
        return arena;
    }
}