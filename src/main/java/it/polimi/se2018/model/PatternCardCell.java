package it.polimi.se2018.model;

import com.sun.xml.internal.messaging.saaj.soap.ver1_1.DetailEntry1_1Impl;
import it.polimi.se2018.model.container.*;

public class PatternCardCell {

    private DieColor colorConstraint;

    private int valueConstraint;

    private int throwedDieId = 0;

    private DiceContainer diceContainer;

    public PatternCardCell(DieColor colorConstraint, int valueConstraint) {
        this.colorConstraint = colorConstraint;
        this.valueConstraint = valueConstraint;
    }

    public boolean isEmpy(){
        return getThrowedDieId() == 0;
    }

    public DieColor getColorConstraint() {
        return colorConstraint;
    }

    public int getValueConstraint() {
        return valueConstraint;
    }

    public int getThrowedDieId() {
        return throwedDieId;
    }

    /* TODO: check usefulness of this method, isn't getThrowedDieId enough?
    /**
     * @return Die object associated with throwedDieId,
     *         null if throwedDieId is unsupported.
     *
    public Die getThrowedDie() {
        try {
            return diceContainer.getDie(throwedDieId);
        } catch (DiceContainerUnsupportedIdException e) {
            return null;
        }
    }*/

    /**
     * @param throwedDieId the id of the Die placed on the cell.
     *
     * @throws DiceContainerUnsupportedIdException if one of throwedDieId
     *                                             or this.throwedDieId
     *                                             is not valid.
     */
    private void setThrowedDieId(int throwedDieId) throws DiceContainerUnsupportedIdException {
        if(!diceContainer.isIdValid(throwedDieId)) {
            throw new DiceContainerUnsupportedIdException();
        }
        try {
            diceContainer.getDie(this.throwedDieId).setThrowed(false);
            diceContainer.getDie(throwedDieId).setThrowed(true);
            this.throwedDieId = throwedDieId;
        } catch (DieSameThrowedValueException e) {
            e.printStackTrace();
        }
    }

    /* TODO: tests, docs. Should it throw an exception? */
    public void setThrowedDieId(int throwedDieId, boolean ignoreValueConstraint, boolean ignoreColorConstraint) throws DiceContainerUnsupportedIdException {
        Die d = diceContainer.getDie(throwedDieId);
        if(checkDieValidity(d.getThrowedValue(), d.getColor(), ignoreValueConstraint, ignoreColorConstraint)) {
           setThrowedDieId(throwedDieId);
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
