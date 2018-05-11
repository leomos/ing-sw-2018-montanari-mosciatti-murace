package it.polimi.se2018.model.objectives;

import it.polimi.se2018.model.PatternCard;
import it.polimi.se2018.model.container.DiceContainer;
import it.polimi.se2018.model.container.DiceContainerUnsupportedIdException;
import it.polimi.se2018.model.container.Die;

public class PublicObjective7 extends PublicObjective{

    public PublicObjective7(DiceContainer diceContainer) {
        super(diceContainer);
        this.name = "Sfumature scure";
        this.description = "Set di 5&6 ovunque";
        this.id = 7;
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
                    //Per ogni dado che trovo, controllo che questo sia 5 o 6
                    Die d = diceContainer.getDie(patternCard.getPatternCardCell(i, j).getRolledDieId());
                    if (d.getRolledValue()>=5)
                        result++;
                } catch (DiceContainerUnsupportedIdException e) {
                    e.printStackTrace();
                }
            }
        }
        return (result/2)*2;
    }

}
