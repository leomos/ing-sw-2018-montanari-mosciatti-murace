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

    /**
     *
     * @param patternCard
     * @return PatternCard score at the end of the game based on public objective of card 6
     * @throws DiceContainerUnsupportedIdException If die's id is not valid
     */
    @Override
    public int calculateScore(PatternCard patternCard) {
        int result3 = 0;
        int result4 = 0;

        for (int i=0; i<5; i++) {
            for (int j=0; j<4; j++) {
                if (!patternCard.getPatternCardCell(i, j).isEmpty()){
                    try {
                        Die d = diceContainer.getDie(patternCard.getPatternCardCell(i, j).getRolledDieId());
                        if (d.getRolledValue() == 4)
                            result4++;
                        if (d.getRolledValue() == 3)
                            result3++;
                    } catch (DiceContainerUnsupportedIdException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        if(result3 > result4)
           return result4 * 2;
        else
            return result3 * 2;
    }

}
