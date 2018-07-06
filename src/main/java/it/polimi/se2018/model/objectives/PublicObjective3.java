package it.polimi.se2018.model.objectives;

import it.polimi.se2018.model.patternCard.PatternCard;
import it.polimi.se2018.model.container.DiceContainer;
import it.polimi.se2018.model.container.Die;

import java.util.ArrayList;

public class PublicObjective3 extends PublicObjective {

    public PublicObjective3(DiceContainer diceContainer) {
        super(diceContainer);
        this.name = "Columns Shade Variety";
        this.description = "Rows with no repeated values";
        this.id = 3;
    }

    /**
     * Calculates the score of the pattern card given: + 5 for each row without any repeated values
     * @param patternCard
     * @return PatternCard score at the end of the game based on public objective of card 3
     */
    @Override
    public int calculateScore(PatternCard patternCard) {
        ArrayList<Integer> riga = new ArrayList<>();
        int result = 0;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                if (!patternCard.getPatternCardCell(j, i).isEmpty()) {
                        Die d = diceContainer.getDie(patternCard.getPatternCardCell(j, i).getRolledDieId());
                        if (riga.indexOf(d.getRolledValue()) == -1)
                            riga.add(d.getRolledValue());

                }
            }
            if (riga.size() == 5)
                result = result + 5;
            riga.clear();
        }
        return result;
    }

}
