package it.polimi.se2018.model.objectives;

import it.polimi.se2018.model.patternCard.PatternCard;
import it.polimi.se2018.model.container.DiceContainer;
import it.polimi.se2018.model.container.Die;

public class PublicObjective8 extends PublicObjective {

    public PublicObjective8(DiceContainer diceContainer) {
        super(diceContainer);
        this.name = "Shade Variety";
        this.description = "Sets of one of each value anywhere";
        this.id = 8;
    }

    /**
     * Calculates the score of the pattern card given: + 5 for each sets of one of each values anywhere
     * @param patternCard
     * @return PatternCard score at the end of the game based on public objective of card 8
     */
    @Override
    public int calculateScore(PatternCard patternCard) {
        int shade[] = new int[6];
        int min = 20;

        for (int i = 0; i < 6; i++) {
            shade[i] = 0;
        }
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 4; j++) {
                if (!patternCard.getPatternCardCell(i, j).isEmpty()) {
                    Die d = diceContainer.getDie(patternCard.getPatternCardCell(i, j).getRolledDieId());
                    shade[d.getRolledValue() - 1]++;
                }
            }
        }

        for(int i: shade)
            min = Math.min(min, i);

        return min * 5;

    }
}
