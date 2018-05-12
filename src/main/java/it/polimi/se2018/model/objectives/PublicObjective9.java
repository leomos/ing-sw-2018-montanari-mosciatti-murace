package it.polimi.se2018.model.objectives;

import it.polimi.se2018.model.PatternCard;
import it.polimi.se2018.model.container.DiceContainer;
import it.polimi.se2018.model.container.DiceContainerUnsupportedIdException;
import it.polimi.se2018.model.container.Die;

public class PublicObjective9 extends PublicObjective {

    public PublicObjective9(DiceContainer diceContainer) {
        super(diceContainer);
        this.name = "Diagonali colorate";
        this.description = "Numero di dadi dello stesso colore diagonalmente adiacenti";
        this.id = 9;
    }

    /**
     *
     * @param patternCard
     * @return PatternCard score at the end of the game based on public objective of card 9
     * @throws DiceContainerUnsupportedIdException If die's id is not valid
     */
    @Override
    public int calculateScore(PatternCard patternCard) {
        int result = 0;

        for (int i=0; i<5; i++) {
            for (int j = 0; j < 4; j++) {
                if (!patternCard.getPatternCardCell(i, j).isEmpty()) {
                    try {
                        Die d = diceContainer.getDie(patternCard.getPatternCardCell(i, j).getRolledDieId());
                        if (d.getColor()==diceContainer.getDie(patternCard.getPatternCardCell(i-1, j-1).getRolledDieId()).getColor()
                                || d.getColor()==diceContainer.getDie(patternCard.getPatternCardCell(i+1, j+1).getRolledDieId()).getColor()
                                || d.getColor()==diceContainer.getDie(patternCard.getPatternCardCell(i+1, j-1).getRolledDieId()).getColor()
                                || d.getColor()==diceContainer.getDie(patternCard.getPatternCardCell(i-1, j+1).getRolledDieId()).getColor())
                            result++;
                    } catch (DiceContainerUnsupportedIdException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return result;
    }
}
