package it.polimi.se2018.model.objectives;

import it.polimi.se2018.model.patternCard.PatternCard;
import it.polimi.se2018.model.container.DiceContainer;
import it.polimi.se2018.model.container.DiceContainerUnsupportedIdException;
import it.polimi.se2018.model.container.Die;
import it.polimi.se2018.model.container.DieColor;

import java.util.ArrayList;

public class PublicObjective2 extends PublicObjective {

    public PublicObjective2(DiceContainer diceContainer) {
        super(diceContainer);
        this.name = "Colori diversi - Colonna";
        this.description = "Colonne senza colori ripetuti";
        this.id = 2;
    }

    /**
     * @param patternCard
     * @return PatternCard score at the end of the game based on public objective of card 2
     * @throws DiceContainerUnsupportedIdException if die's id is not valid
     */
    @Override
    public int calculateScore(PatternCard patternCard) {
        ArrayList<DieColor> riga = new ArrayList<>();
        int result = 0;

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 4; j++) {
                if (!patternCard.getPatternCardCell(i, j).isEmpty()) {
                    try {
                        Die d = diceContainer.getDie(patternCard.getPatternCardCell(i, j).getRolledDieId());
                        if (riga.indexOf(d.getColor()) == -1)
                            riga.add(d.getColor());
                    } catch (DiceContainerUnsupportedIdException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (riga.size()==4)
                result = result + 5;
            riga.clear();
        }
        return result;
    }
}
