package it.polimi.se2018.model.objectives;

import it.polimi.se2018.model.PatternCard;
import it.polimi.se2018.model.container.DiceContainer;

public class PublicObjective9 extends PublicObjective {

    public PublicObjective9(DiceContainer diceContainer) {
        super(diceContainer);
        this.name = "Diagonali colorate";
        this.description = "Numero di dadi dello stesso colore diagonalmente adiacenti";
        this.id = 9;
    }

    @Override
    public int calculateScore(PatternCard patternCard) {
        return 0;
    }

}
