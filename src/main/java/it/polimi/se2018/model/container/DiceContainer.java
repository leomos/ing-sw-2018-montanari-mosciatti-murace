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

    /* TODO: docs */
    public ArrayList<Die> getUnrolledDice() {
        ArrayList<Die> bag = new ArrayList<>();

        for(Die d : dice) {
            if(!d.isRolled()) {
                bag.add(d);
            }
        }

        return bag;
    }

    /* TODO: docs */
    public Die getDie(int id) throws DiceContainerUnsupportedIdException {
        if(!isIdValid(id)) {
            throw new DiceContainerUnsupportedIdException();
        } else {
            return dice[id];
        }
    }

    /* TODO: docs and change public to protected/private? */
    public boolean isIdValid(int id) {
       return (id < NUMBER_OF_DICE && id >= 0);
    }
}
