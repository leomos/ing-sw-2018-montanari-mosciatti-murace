package it.polimi.se2018.model;

import it.polimi.se2018.model.container.*;

public class PatternCardCell {

    private DieColor colorConstraint;

    private int valueConstraint;

    private int rolledDieId = -1;

    private DiceContainer diceContainer;

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

    /**
     * @param rolledDieId the id of the Die placed on the cell.
     *
     * @throws DiceContainerUnsupportedIdException if one of rolledDieId
     *                                             or this.rolledDieId
     *                                             is not valid.
     */
    private void setRolledDieId(int rolledDieId) throws DiceContainerUnsupportedIdException {
        if(!diceContainer.isIdValid(rolledDieId)) {
            throw new DiceContainerUnsupportedIdException();
        }
        try {
            diceContainer.getDie(this.rolledDieId).setRolled(false);
            diceContainer.getDie(rolledDieId).setRolled(true);
            this.rolledDieId = rolledDieId;
        } catch (DieRolledStateNotChangedException e) {
            e.printStackTrace();
        }
    }

    /* TODO: tests, docs. Should it throw an exception? */
    public void setRolledDieId(int rolledDieId, boolean ignoreValueConstraint, boolean ignoreColorConstraint) throws DiceContainerUnsupportedIdException {
        Die d = diceContainer.getDie(rolledDieId);
        if(checkDieValidity(d.getRolledValue(), d.getColor(), ignoreValueConstraint, ignoreColorConstraint)) {
           setRolledDieId(rolledDieId);
        }
    }

    /* TODO: tests, docs and refactor with minimization. */
    private boolean checkDieValidity(int valueConstraint, DieColor colorConstraint, boolean ignoreValueConstraint, boolean ignoreColorConstraint) {
        boolean isValueConstraintRespected = (valueConstraint == this.valueConstraint);
        boolean isColorConstraintRespected = (colorConstraint == this.colorConstraint);
        if( (isValueConstraintRespected && isColorConstraintRespected) &&
                (!ignoreValueConstraint && !ignoreColorConstraint)) {
            return true;
        }
        if( isValueConstraintRespected && !ignoreValueConstraint && ignoreColorConstraint) {
            return true;
        }
        if( isColorConstraintRespected && !ignoreColorConstraint && ignoreValueConstraint) {
            return true;
        }
        if( ignoreValueConstraint && ignoreColorConstraint) {
            return true;
        }
        return false;
    }
}
