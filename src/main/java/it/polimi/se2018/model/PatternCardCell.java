package it.polimi.se2018.model;

public class PatternCardCell {

    private DieColor colorConstraint;

    private int valueContraint;

    private Die throwedDie;

    public DieColor getColorConstraint() {
        return colorConstraint;
    }

    public int getValueContraint() {
        return valueContraint;
    }

    public void setThrowedDie(Die throwedDie) {
        this.throwedDie = throwedDie;
    }

    public Die getThrowedDie(){
        return throwedDie;
    }
}
