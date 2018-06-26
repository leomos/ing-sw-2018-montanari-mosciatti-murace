package it.polimi.se2018.model.objectives;

import it.polimi.se2018.model.patternCard.PatternCard;
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

    public String getName() {
        return name;
    }

    public PublicObjective(DiceContainer diceContainer) {
        this.diceContainer = diceContainer;
    }

    public abstract int calculateScore(PatternCard patternCard);
}
