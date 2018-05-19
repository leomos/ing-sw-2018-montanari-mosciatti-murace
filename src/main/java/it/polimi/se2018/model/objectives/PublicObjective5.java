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

    /**
     *
     * @param patternCard
     * @return PatternCard score at the end of the game based on public objective of card 5
     * @throws DiceContainerUnsupportedIdException If die's id is not valid
     */
    @Override
    public int calculateScore(PatternCard patternCard) {
        int result1 = 0;
        int result2 = 0;

        for (int i=0; i<5; i++) {
            for (int j=0; j<4; j++) {
                if (!patternCard.getPatternCardCell(i, j).isEmpty()){
                    try {
                        Die d = diceContainer.getDie(patternCard.getPatternCardCell(i, j).getRolledDieId());
                        if (d.getRolledValue() == 1)
                            result1++;
                        if (d.getRolledValue() == 2)
                            result2++;
                    } catch (DiceContainerUnsupportedIdException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        if (result1 > result2)
           return result2 * 2;
        else
            return result1 * 2;
    }
}
