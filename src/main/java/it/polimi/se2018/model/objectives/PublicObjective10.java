package it.polimi.se2018.model.objectives;

import it.polimi.se2018.model.patternCard.PatternCard;
import it.polimi.se2018.model.container.DiceContainer;
import it.polimi.se2018.model.container.Die;

public class PublicObjective10 extends PublicObjective {

    public PublicObjective10(DiceContainer diceContainer) {
        super(diceContainer);
        this.name = "Color Variety";
        this.description = "Sets of one of each color anywhere";
        this.id = 10;
    }

    /**
     * Calculates the score of the pattern card given: + 4 for each sets of one of each color anywhere
     * @param patternCard
     * @return PatternCard score at the end of the game based on public objective of card 10
     */
    @Override
    public int calculateScore(PatternCard patternCard) {
        int[] shade = new int[5];
        int min = 20;

        for (int i=0; i < 5; i++) {
            shade[i] = 0;
        }
        for (int i=0; i<5; i++) {
            for (int j=0; j<4; j++) {
                if (!patternCard.getPatternCardCell(i, j).isEmpty()) {
                        Die d = diceContainer.getDie(patternCard.getPatternCardCell(i, j).getRolledDieId());
                        switch (d.getColor()) {
                            case BLUE: shade[0]++; break;
                            case GREEN: shade[1]++; break;
                            case PURPLE: shade[2]++; break;
                            case RED: shade[3]++; break;
                            case YELLOW: shade[4]++; break;
                            default: break;
                        }
                }
            }
        }

        for(int i: shade)
            min = Math.min(min, i);

        return min*4;
    }
}
