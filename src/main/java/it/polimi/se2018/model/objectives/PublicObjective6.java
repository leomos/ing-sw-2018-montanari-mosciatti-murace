package it.polimi.se2018.model.objectives;

import it.polimi.se2018.model.patternCard.PatternCard;
import it.polimi.se2018.model.container.DiceContainer;
import it.polimi.se2018.model.container.Die;

public class PublicObjective6 extends PublicObjective{

    public PublicObjective6(DiceContainer diceContainer) {
        super(diceContainer);
        this.name = "Medium Shades";
        this.description = "Set of 3&4 values anywhere";
        this.id = 6;
    }

    /**
     * Calculates the score of the pattern card given: + 2 for each set of 3&4 anywhere
     * @param patternCard
     * @return PatternCard score at the end of the game based on public objective of card 6
     */
    @Override
    public int calculateScore(PatternCard patternCard) {
        int result3 = 0;
        int result4 = 0;

        for (int i=0; i<5; i++) {
            for (int j=0; j<4; j++) {
                if (!patternCard.getPatternCardCell(i, j).isEmpty()){
                        Die d = diceContainer.getDie(patternCard.getPatternCardCell(i, j).getRolledDieId());
                        if (d.getRolledValue() == 4)
                            result4++;
                        if (d.getRolledValue() == 3)
                            result3++;
                }
            }
        }
        if(result3 > result4)
           return result4 * 2;
        else
            return result3 * 2;
    }

}
