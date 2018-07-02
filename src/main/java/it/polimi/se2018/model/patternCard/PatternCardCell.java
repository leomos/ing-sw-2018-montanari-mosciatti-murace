package it.polimi.se2018.model.patternCard;

import it.polimi.se2018.model.container.*;

public class PatternCardCell {

    private DieColor colorConstraint;

    private int valueConstraint;

    private int rolledDieId = -1;

    public PatternCardCell(DieColor colorConstraint, int valueConstraint) {
        this.colorConstraint = colorConstraint;
        this.valueConstraint = valueConstraint;
    }

    public boolean isEmpty(){
        return rolledDieId == -1;
    }

    public DieColor getColorConstraint() {
        return colorConstraint;
    }

    public int getValueConstraint() {
        return valueConstraint;
    }

    public int getRolledDieId() {
        return rolledDieId;
    }

    public void setRolledDieId(int rolledDieId, boolean ignoreValueConstraint, boolean ignoreColorConstraint) throws DiceContainerUnsupportedIdException {
        this.rolledDieId = rolledDieId;

    }


    /**
     * Checks if the value and the color respect the constraint of this cell
     * @param dieValue die's value that the player wants to set in this cell
     * @param dieColor die's color that the player wants to set in this cell
     * @param ignoreValueConstraint boolean whether you can't ignore the value constraint when placing a die in this cell
     * @param ignoreColorConstraint boolean whether you can't ignore the color constraint when placing a die in this cell
     * @return true if the die respects the cell constraints, false otherwise
     */
    public boolean checkDieValidity(int dieValue, DieColor dieColor, boolean ignoreValueConstraint, boolean ignoreColorConstraint) {

        if(this.valueConstraint != 0)
            if(!ignoreValueConstraint)
                if(dieValue != this.valueConstraint)
                    return false;

        if(this.colorConstraint != null)
            if(!ignoreColorConstraint)
                if(dieColor != this.colorConstraint)
                    return false;

        return true;
    }

    /**
     * remove die in this cell by putting id equals to -1
     */
    public void removeDie() throws CellIsEmptyException {
        if(rolledDieId != -1)
            this.rolledDieId = -1;
        else
            throw new CellIsEmptyException();
    }
}
