package it.polimi.se2018.model.objectives;

import it.polimi.se2018.model.patternCard.PatternCard;
import it.polimi.se2018.model.container.DiceContainer;
import it.polimi.se2018.model.container.DiceContainerUnsupportedIdException;
import it.polimi.se2018.model.container.Die;

import java.util.ArrayList;

public class PublicObjective3 extends PublicObjective {

    public PublicObjective3(DiceContainer diceContainer) {
        super(diceContainer);
        this.name = "Sfumature diverse - Riga";
        this.description = "Righe senza sfumature ripetute";
        this.id = 3;
    }

    /**
     *
     * @param patternCard
     * @return PatternCard score at the end of the game based on public objective of card 3
     * @throws DiceContainerUnsupportedIdException if die's id is not valid
     */
    @Override
    public int calculateScore(PatternCard patternCard) {
        ArrayList<Integer> riga = new ArrayList<>();
        int result = 0;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                if (!patternCard.getPatternCardCell(j, i).isEmpty()) {
                    try {
                        Die d = diceContainer.getDie(patternCard.getPatternCardCell(j, i).getRolledDieId());
                        if (riga.indexOf(d.getRolledValue()) == -1)
                            riga.add(d.getRolledValue());
                    } catch (DiceContainerUnsupportedIdException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (riga.size() == 5)
                result = result + 5;
            riga.clear();
        }
        return result;
    }

}
