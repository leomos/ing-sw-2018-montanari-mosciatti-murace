package it.polimi.se2018.model.objectives;

import it.polimi.se2018.model.PatternCard;
import it.polimi.se2018.model.container.DiceContainer;
import it.polimi.se2018.model.container.DiceContainerUnsupportedIdException;
import it.polimi.se2018.model.container.Die;

public class PublicObjective5 extends PublicObjective{

    public PublicObjective5(DiceContainer diceContainer) {
        super(diceContainer);
        this.name = "Sfumature chiare";
        this.description = "Set di 1&2 ovunque";
        this.id = 5;
    }

    @Override
    public int calculateScore(PatternCard patternCard) {
        int result = 0;

        for (int i=0; i<5; i++) {
            for (int j=0; j<4; j++) {
                // Comincio controllando che la cella che sto esaminando non sia vuota
                if (patternCard.getPatternCardCell(i, j).isEmpty())
                    break;
                try {
                    //Per ogni dado che trovo, controllo che questo sia 1 o 2
                    Die d = diceContainer.getDie(patternCard.getPatternCardCell(i, j).getRolledDieId());
                    if (d.getRolledValue()<=2)
                        result++;
                } catch (DiceContainerUnsupportedIdException e) {
                    e.printStackTrace();
                }
            }
        }
        return (result/2)*2;
    }
}
