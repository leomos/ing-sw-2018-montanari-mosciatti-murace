package it.polimi.se2018.model;

import it.polimi.se2018.model.container.*;

public class PatternCardCell {

    private DieColor colorConstraint;

    private int valueConstraint;

    private int rolledDieId = -1;

    private DiceContainer diceContainer;

    public PatternCardCell(DiceContainer diceContainer, DieColor colorConstraint, int valueConstraint) {
        this.colorConstraint = colorConstraint;
        this.valueConstraint = valueConstraint;
        this.diceContainer = diceContainer;
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

    /* TODO: tests, docs. Void to boolean!!!! */
    public void setRolledDieId(int rolledDieId, boolean ignoreValueConstraint, boolean ignoreColorConstraint) throws DiceContainerUnsupportedIdException {
        Die d = diceContainer.getDie(rolledDieId);
        if(checkDieValidity(d.getRolledValue(), d.getColor(), ignoreValueConstraint, ignoreColorConstraint)) {
            this.rolledDieId = rolledDieId;
        }
    }

    /* TODO: tests, docs and refactor with minimization. */
    private boolean checkDieValidity(int dieValue, DieColor dieColor, boolean ignoreValueConstraint, boolean ignoreColorConstraint) {

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


}
