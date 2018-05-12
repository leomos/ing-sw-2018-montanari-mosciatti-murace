package it.polimi.se2018.model.container;

import java.util.HashMap;

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
     * @return  an HashMap of all the dice that are not rolled,
     *          with index the id of the die and key the die itself.
     *          This can be considered as the abstraction of
     *          the dice bag.
     */
    public HashMap<Integer, Die> getUnrolledDice() {
        HashMap<Integer, Die> bag = new HashMap<>();

        Die d;
        for (int i = 0; i < NUMBER_OF_DICE; i++) {
            d = dice[i];
            if(!d.isRolled()) {
                bag.put(i,d);
            }
        }

        return bag;
    }

    /**
     * @param   id is the position of the die in the container array.
     * @return  the Die correspondent to the id.
     * @throws  DiceContainerUnsupportedIdException if the id is a negative number
     *                                              or if it is greater than the
     *                                              constant NUMBER_OF_DICE
     */
    public Die getDie(int id) throws DiceContainerUnsupportedIdException {
        if(!isIdValid(id)) {
            throw new DiceContainerUnsupportedIdException();
        } else {
            return dice[id];
        }
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
