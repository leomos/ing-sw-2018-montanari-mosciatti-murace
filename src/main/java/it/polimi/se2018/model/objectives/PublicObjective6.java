package it.polimi.se2018.model.objectives;

import it.polimi.se2018.model.PatternCard;
import it.polimi.se2018.model.container.DiceContainer;
import it.polimi.se2018.model.container.DiceContainerUnsupportedIdException;
import it.polimi.se2018.model.container.Die;

public class PublicObjective6 extends PublicObjective{

    public PublicObjective6(DiceContainer diceContainer) {
        super(diceContainer);
        this.name = "Sfumature medie";
        this.description = "Set di 3&4 ovunque";
        this.id = 6;
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
                    //Per ogni dado che trovo, controllo che questo sia 3 o 4
                    Die d = diceContainer.getDie(patternCard.getPatternCardCell(i, j).getRolledDieId());
                    if (d.getRolledValue()==3 || d.getRolledValue()==4)
                        result++;
                } catch (DiceContainerUnsupportedIdException e) {
                    e.printStackTrace();
                }
            }
        }
        return (result/2)*2;
    }

}
