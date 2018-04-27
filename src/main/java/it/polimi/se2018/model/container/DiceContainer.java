package it.polimi.se2018.model.container;

import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.DieColor;

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

    public ArrayList<Die> getUnthrowedDice() {
        ArrayList<Die> bag = new ArrayList<>();

        for(Die d : dice) {
            if(!d.isThrowed()) {
                bag.add(d);
            }
        }

        return bag;
    }

    public Die getDie(int id) throws DiceContainerUnsupportedIdException {
        if(id >= NUMBER_OF_DICE || id < 0) {
            throw new DiceContainerUnsupportedIdException();
        } else {
            return dice[id];
        }
    }
}
