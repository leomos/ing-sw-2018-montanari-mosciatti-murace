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

    /**
     *
     * @param patternCard
     * @return PatternCard score at the end of the game based on public objective of card 7
     * @throws DiceContainerUnsupportedIdException If die's id is not valid
     */
    @Override
    public int calculateScore(PatternCard patternCard) {
        int result5 = 0;
        int result6 = 0;

        for (int i=0; i<5; i++) {
            for (int j=0; j<4; j++) {
                if (!patternCard.getPatternCardCell(i, j).isEmpty()) {
                    try {
                        Die d = diceContainer.getDie(patternCard.getPatternCardCell(i, j).getRolledDieId());
                        if (d.getRolledValue() == 5)
                            result5++;
                        if (d.getRolledValue() == 6)
                            result6++;
                    } catch (DiceContainerUnsupportedIdException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        if (result5 > result6)
            return result6 * 2;
        else
            return result5 * 2;
    }

}
