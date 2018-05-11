package it.polimi.se2018.model.objectives;

import it.polimi.se2018.model.PatternCard;
import it.polimi.se2018.model.container.DiceContainer;

public class PublicObjective10 extends PublicObjective {

    public PublicObjective10(DiceContainer diceContainer) {
        super(diceContainer);
        this.name = "Varietà di colore";
        this.description = "Set di dadi di ogni colore ovunque";
        this.id = 10;
    }

    @Override
    public int calculateScore(PatternCard patternCard) {
        return 0;
    }
}
