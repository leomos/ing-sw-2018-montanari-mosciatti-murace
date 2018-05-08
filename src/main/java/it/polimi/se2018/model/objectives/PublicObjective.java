package it.polimi.se2018.model.objectives;

import it.polimi.se2018.model.PatternCard;

public abstract class PublicObjective {
    protected String description;

    protected int id;

    public int getId() {
        return this.id;
    }

    public String getDescription() {
        return this.description;
    }

    public abstract int calculateScore(PatternCard patternCard);
}
