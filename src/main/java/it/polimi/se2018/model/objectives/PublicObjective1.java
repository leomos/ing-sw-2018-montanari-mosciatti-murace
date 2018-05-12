package it.polimi.se2018.model.objectives;

import it.polimi.se2018.model.PatternCard;
import it.polimi.se2018.model.container.DiceContainer;
import it.polimi.se2018.model.container.DiceContainerUnsupportedIdException;
import it.polimi.se2018.model.container.Die;
import it.polimi.se2018.model.container.DieColor;

import java.util.ArrayList;


public class PublicObjective1 extends PublicObjective {

    public PublicObjective1(DiceContainer diceContainer) {
        super(diceContainer);
        this.name = "Colori diversi - Riga";
        this.description = "Righe senza colori ripetuti";
        this.id = 1;
    }

    /**
     *
     * @param patternCard
     * @return PatternCard score at the end of the game based on public objective of card 1
     * @throws DiceContainerUnsupportedIdException if die's id is not valid
     */
    @Override
    /*TODO: tests */
    public int calculateScore(PatternCard patternCard) {
        ArrayList<DieColor> riga = new ArrayList<>();
        int result = 0;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                if (patternCard.getPatternCardCell(i, j).isEmpty())
                    break;
                try {
                    Die d = diceContainer.getDie(patternCard.getPatternCardCell(i, j).getRolledDieId());
                    if (riga.indexOf(d.getColor()) != -1)
                        break;
                    riga.add(d.getColor());
                } catch (DiceContainerUnsupportedIdException e) {
                    e.printStackTrace();
                }
            }
            if (riga.size()==5)
                result = result + 6;
        }
    return result;
    }
}
