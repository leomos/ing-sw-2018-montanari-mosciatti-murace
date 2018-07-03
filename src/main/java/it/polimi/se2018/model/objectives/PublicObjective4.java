package it.polimi.se2018.model.objectives;

import it.polimi.se2018.model.patternCard.PatternCard;
import it.polimi.se2018.model.container.DiceContainer;
import it.polimi.se2018.model.container.DiceContainerUnsupportedIdException;
import it.polimi.se2018.model.container.Die;

import java.util.ArrayList;

public class PublicObjective4 extends PublicObjective {

    public PublicObjective4(DiceContainer diceContainer) {
        super(diceContainer);
        this.name = "Columns Shade Variety";
        this.description = "Columns with no repeated values";
        this.id = 4;
    }

    /**
     * Calculates the score of the pattern card given: + 4 for each column without any repeated values
     * @param patternCard
     * @return PatternCard score at the end of the game based on public objective of card 4
     * @throws DiceContainerUnsupportedIdException If die's id is not valid
     */
    @Override
    public int calculateScore(PatternCard patternCard) {
        ArrayList<Integer> riga = new ArrayList<>();
        int result = 0;

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 4; j++) {
                if (!patternCard.getPatternCardCell(i, j).isEmpty()){
                    try {
                        Die d = diceContainer.getDie(patternCard.getPatternCardCell(i, j).getRolledDieId());
                        if (riga.indexOf(d.getRolledValue()) == -1)
                            riga.add(d.getRolledValue());
                    } catch (DiceContainerUnsupportedIdException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (riga.size()==4)
                result = result + 4;
            riga.clear();
        }
        return result;
    }
}
