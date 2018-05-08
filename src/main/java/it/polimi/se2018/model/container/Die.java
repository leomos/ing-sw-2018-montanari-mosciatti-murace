package it.polimi.se2018.model.container;

public class Die {

    private DieColor color;

    private int rolledValue;

    private boolean rolled;

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

    /* TODO: tests and docs */
    public void setRolled(boolean rolled) throws DieSameRolledValueException {
        if(this.rolled == rolled) {
            throw new DieSameRolledValueException();
        } else {
            this.rolled = rolled;
        }
    }

    /* TODO: tests and docs */
    public void setRolledValue(int rolledValue) throws DieRolledValueOutOfBoundException {
        if(rolledValue > 0 && rolledValue < 7) {
            this.rolledValue = rolledValue;
        } else {
            throw new DieRolledValueOutOfBoundException();
        }
    }


}
