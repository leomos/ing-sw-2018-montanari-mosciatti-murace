package it.polimi.se2018.model.container;

import java.util.ArrayList;
import java.util.Collections;

import static it.polimi.se2018.model.container.DieColor.*;

public class Die {

    private DieColor color;

    private int rolledValue = 0;

    private boolean rolled = false;

    public Die(DieColor color) {
        this.color = color;
    }

    public DieColor getColor() {
        return color;
    }

    public int getRolledValue() {
        return rolledValue;
    }

    public boolean isRolled() {
        return rolled;
    }

    /**
     * @param rolled
     * @throws DieRolledStateNotChangedException if rolled parameter is equal to rolled property
     */
    public void setRolled(boolean rolled) throws DieRolledStateNotChangedException {
        if(this.rolled == rolled) {
            throw new DieRolledStateNotChangedException();
        } else {
            this.rolled = rolled;
        }
    }

    /**
     * @param rolledValue
     * @throws DieRolledValueOutOfBoundException if rolledValue > 6 or rolledValue < 1
     */
    public void setRolledValue(int rolledValue) throws DieRolledValueOutOfBoundException {
        if(rolledValue > 0 && rolledValue < 7) {
            this.rolledValue = rolledValue;
        } else {
            throw new DieRolledValueOutOfBoundException();
        }
    }

    /**
     * Rolls a die setting rolledValue to a random value ranging
     * from 0 to 6 included.
     */
    public void roll() {
        ArrayList<Integer> possibleValues = new ArrayList<>();
        for (Integer i = 1; i < 7; i++)
            possibleValues.add(i);
        Collections.shuffle(possibleValues);

        this.rolledValue = possibleValues.get(0);
        this.rolled = true;
    }

    public String getColorChar(){
        if(color == RED)
            return "r";
        if(color == YELLOW)
            return "y";
        if(color == BLUE)
            return "b";
        if(color == PURPLE)
            return "p";
        if(color == GREEN)
            return "g";
        return null;
    }

}
