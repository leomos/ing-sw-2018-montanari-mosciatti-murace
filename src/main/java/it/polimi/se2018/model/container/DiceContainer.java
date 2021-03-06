package it.polimi.se2018.model.container;

import java.util.ArrayList;

public class DiceContainer {

    private static final int NUMBER_OF_DICE = 90;

    private Die[] dice = new Die[NUMBER_OF_DICE];

    public DiceContainer() {
        int i;
        int j = 0;
        for(DieColor dieColor : DieColor.values()) {
            for(i = 0; i < NUMBER_OF_DICE/DieColor.values().length; i++) {
                dice[(j*NUMBER_OF_DICE/DieColor.values().length)+i] = new Die(dieColor);
            }
            j++;
        }
    }

    /**
     * @return an ArrayList containing the ids of all the unrolled dice.
     */
    public ArrayList<Integer> getUnrolledDice() {
        ArrayList<Integer> bag = new ArrayList<>();

        Die d;
        for (int i = 0; i < NUMBER_OF_DICE; i++) {
            d = dice[i];
            if(!d.isRolled()) {
                bag.add(i);
            }
        }

        return bag;
    }

    /**
     * @param   id is the position of the die in the container array.
     * @return  the Die correspondent to the id.
     */
    public Die getDie(int id) {
        return dice[id];
    }

    /**
     * @param id
     * @return  true if the id is valid, that is,
     *          it's not a negative number and
     *          it's smaller than NUMBER_OF_DICE
     */
    private boolean isIdValid(int id) {
       return (id < NUMBER_OF_DICE && id >= 0);
    }
}
