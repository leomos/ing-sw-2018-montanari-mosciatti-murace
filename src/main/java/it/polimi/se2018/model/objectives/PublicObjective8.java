package it.polimi.se2018.model.objectives;

import it.polimi.se2018.model.PatternCard;
import it.polimi.se2018.model.container.DiceContainer;

public class PublicObjective8 extends PublicObjective{

    public PublicObjective8(DiceContainer diceContainer) {
        super(diceContainer);
        this.name = "Sfumature diverse";
        this.description = "Set di dadi di ogni valore ovunque";
        this.id = 8;
    }

    @Override
    public int calculateScore(PatternCard patternCard) {
        return 0;
    }

}
