package it.polimi.se2018.model.container;

public class Die {

    private DieColor color;

    private int rolledValue;

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


}
