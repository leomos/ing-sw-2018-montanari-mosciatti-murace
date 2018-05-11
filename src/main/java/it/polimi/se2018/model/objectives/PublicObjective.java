package it.polimi.se2018.model.objectives;

import it.polimi.se2018.model.PatternCard;
import it.polimi.se2018.model.container.DiceContainer;

public abstract class PublicObjective {
    protected String name;

    protected String description;

    protected int id;

    DiceContainer diceContainer;

    public int getId() {
        return this.id;
    }

    public String getDescription() {
        return this.description;
    }

    public abstract int calculateScore(PatternCard patternCard);
}
