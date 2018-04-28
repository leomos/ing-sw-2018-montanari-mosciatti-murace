package it.polimi.se2018.model.container;

public class Die {

    private DieColor color;

    private int throwedValue;

    private boolean throwed;

    public Die(DieColor color) {
        this.color = color;
    }

    public DieColor getColor() {
        return color;
    }

    public int getThrowedValue() {
        return throwedValue;
    }

    public boolean isThrowed() {
        return throwed;
    }

    /* TODO: tests and docs */
    public void setThrowed(boolean throwed) throws DieSameThrowedValueException {
        if(this.throwed == throwed) {
            throw new DieSameThrowedValueException();
        } else {
            this.throwed = throwed;
        }
    }

    /* TODO: tests and docs */
    public void setThrowedValue(int throwedValue) throws DieThrowedValueOutOfBoundException {
        if(throwedValue > 0 && throwedValue < 7) {
            this.throwedValue = throwedValue;
        } else {
            throw new DieThrowedValueOutOfBoundException();
        }
    }


}
