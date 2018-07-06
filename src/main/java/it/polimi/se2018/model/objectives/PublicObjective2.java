package it.polimi.se2018.model.objectives;

import it.polimi.se2018.model.patternCard.PatternCard;
import it.polimi.se2018.model.container.DiceContainer;
import it.polimi.se2018.model.container.Die;
import it.polimi.se2018.model.container.DieColor;

import java.util.ArrayList;

public class PublicObjective2 extends PublicObjective {

    public PublicObjective2(DiceContainer diceContainer) {
        super(diceContainer);
        this.name = "Columns Color Variety";
        this.description = "Columns with no repeated colors";
        this.id = 2;
    }

    /**
     * Calculates the score of the pattern card given: + 5 for each columns without any repeated color
     * @param patternCard
     * @return PatternCard score at the end of the game based on public objective of card 2
     */
    @Override
    public int calculateScore(PatternCard patternCard) {
        ArrayList<DieColor> riga = new ArrayList<>();
        int result = 0;

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 4; j++) {
                if (!patternCard.getPatternCardCell(i, j).isEmpty()) {
                        Die d = diceContainer.getDie(patternCard.getPatternCardCell(i, j).getRolledDieId());
                        if (riga.indexOf(d.getColor()) == -1)
                            riga.add(d.getColor());
                }
            }
            if (riga.size()==4)
                result = result + 5;
            riga.clear();
        }
        return result;
    }
}
