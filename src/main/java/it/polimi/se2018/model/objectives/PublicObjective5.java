package it.polimi.se2018.model.objectives;

import it.polimi.se2018.model.patternCard.PatternCard;
import it.polimi.se2018.model.container.DiceContainer;
import it.polimi.se2018.model.container.Die;

public class PublicObjective5 extends PublicObjective{

    public PublicObjective5(DiceContainer diceContainer) {
        super(diceContainer);
        this.name = "Light Shade";
        this.description = "Set of 1&2 values anywhere";
        this.id = 5;
    }

    /**
     * Calculates the score of the pattern card given: + 2 for each set of 1&2 anywhere
     * @param patternCard
     * @return PatternCard score at the end of the game based on public objective of card 5
     */
    @Override
    public int calculateScore(PatternCard patternCard) {
        int result1 = 0;
        int result2 = 0;

        for (int i=0; i<5; i++) {
            for (int j=0; j<4; j++) {
                if (!patternCard.getPatternCardCell(i, j).isEmpty()){
                        Die d = diceContainer.getDie(patternCard.getPatternCardCell(i, j).getRolledDieId());
                        if (d.getRolledValue() == 1)
                            result1++;
                        if (d.getRolledValue() == 2)
                            result2++;
                }
            }
        }
        if (result1 > result2)
           return result2 * 2;
        else
            return result1 * 2;
    }
}
