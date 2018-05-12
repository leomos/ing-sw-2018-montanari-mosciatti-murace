package it.polimi.se2018.model.objectives;

import it.polimi.se2018.model.PatternCard;
import it.polimi.se2018.model.container.DiceContainer;
import it.polimi.se2018.model.container.DiceContainerUnsupportedIdException;
import it.polimi.se2018.model.container.Die;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

public class PublicObjective8 extends PublicObjective{

    public PublicObjective8(DiceContainer diceContainer) {
        super(diceContainer);
        this.name = "Sfumature diverse";
        this.description = "Set di dadi di ogni valore ovunque";
        this.id = 8;
    }

    /**
     *
     * @param patternCard
     * @return PatternCard score at the end of the game based on public objective of card 8
     * @throws DiceContainerUnsupportedIdException If die's id is not valid
     */
    @Override
    public int calculateScore(PatternCard patternCard) {
        int result = 0;
        int[] shade = new int[5];
        int min = 10;

        for (int i=0; i<=5; i++) {
            shade[i] = 0;
        }
        for (int i=0; i<5; i++) {
            for (int j=0; j<4; j++) {
                if (!patternCard.getPatternCardCell(i, j).isEmpty()) {
                    try {
                        Die d = diceContainer.getDie(patternCard.getPatternCardCell(i, j).getRolledDieId());
                        shade[d.getRolledValue()-1]++;
                    } catch (DiceContainerUnsupportedIdException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        for (int i=0; i<=5; i++) {
            if (min==shade[i])
                result++;
            if (min>shade[i]) {
                result = 1;
                min = shade[i];
            }
        }
        return result*5;
    }
}
