package it.polimi.se2018.model;

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

    public boolean isThrowed() {
        return throwed;
    }

    public void setThrowed(boolean throwed) {
        this.throwed = throwed;
    }

    public void setThrowedValue(int throwedValue) {
        this.throwedValue = throwedValue;
    }

    public int getThrowedValue() {
        return throwedValue;
    }

}
