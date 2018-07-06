package it.polimi.se2018.model.objectives;

import it.polimi.se2018.model.patternCard.PatternCard;
import it.polimi.se2018.model.container.DiceContainer;
import it.polimi.se2018.model.container.Die;

import java.util.ArrayList;

public class PublicObjective9 extends PublicObjective {

    public PublicObjective9(DiceContainer diceContainer) {
        super(diceContainer);
        this.name = "Color Diagonals";
        this.description = "Count of diagonally adjacent same color dice";
        this.id = 9;
    }

    /**
     * Calculates the score of the pattern card given: + 1 for each die with a die of the same color adjacent to it
     * @param patternCard
     * @return PatternCard score at the end of the game based on public objective of card 9
     */
    @Override
    public int calculateScore(PatternCard patternCard) {
        int result = 0;
        boolean bool;

        for (int i=0; i < 5; i++) {
            for (int j = 0; j < 4; j++) {
                if (!patternCard.getPatternCardCell(i, j).isEmpty()) {
                    bool = false;
                    Die d = diceContainer.getDie(patternCard.getPatternCardCell(i, j).getRolledDieId());
                    ArrayList<Integer[]> diagonalsCellList = patternCard.checkDiagonalCells(i,j);
                    for (Integer k[]: diagonalsCellList)
                        if (!patternCard.getPatternCardCell(k[0], k[1]).isEmpty())
                            if (d.getColor() == diceContainer.getDie(patternCard.getPatternCardCell(k[0], k[1]).getRolledDieId()).getColor()
                                    && bool == false) {
                                result++;
                                bool = true;
                            }
                }
            }
        }
        return result;
    }
}
