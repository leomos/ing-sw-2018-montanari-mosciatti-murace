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
     * from 1 to 6 included.
     */
    public void roll() {
        ArrayList<Integer> possibleValues = new ArrayList<>();
        for (Integer i = 1; i < 7; i++)
            possibleValues.add(i);
        Collections.shuffle(possibleValues);

        this.rolledValue = possibleValues.get(0);
        this.rolled = true;
    }

    /**
     * Turns around the die, changing it's rolled value
     */
    public void turnAround()  {
        if(this.rolled){
            switch (this.rolledValue){
                case 1: this.rolledValue = 6; break;
                case 2: this.rolledValue = 5; break;
                case 3: this.rolledValue = 4; break;
                case 4: this.rolledValue = 3; break;
                case 5: this.rolledValue = 2; break;
                case 6: this.rolledValue = 1; break;
                default: this.rolledValue = 0; break;
            }
        }
    }

    /**
     * Set the die back to the primary state, with rolled set to false and rolled value set to 0
     */
    public void unroll() {
        this.rolled = false;
        this.rolledValue = 0;
    }

    /**
     * @return the char corresponding to the die color
     */
    public String getColorChar(){
        if(color == RED)
            return "r";
        if(color == YELLOW)
            return "y";
        if(color == BLUE)
            return "b";
        if(color == PURPLE)
            return "p";
        return "g";
    }

}
