package it.polimi.se2018.model.objectives;

import it.polimi.se2018.model.PatternCard;
import it.polimi.se2018.model.container.DiceContainer;
import it.polimi.se2018.model.container.DiceContainerUnsupportedIdException;
import it.polimi.se2018.model.container.Die;
import it.polimi.se2018.model.container.DieColor;

import java.util.ArrayList;

public class PublicObjective4 extends PublicObjective {

    public PublicObjective4(DiceContainer diceContainer) {
        super(diceContainer);
        this.name = "Sfumature diverse - Colonna";
        this.description = "Colonne senza sfumature ripetute";
        this.id = 4;
    }

    @Override
    public int calculateScore(PatternCard patternCard) {
        ArrayList<Integer> riga = new ArrayList<>();
        int result = 0;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                if (patternCard.getPatternCardCell(i, j).isEmpty())
                    break;
                try {
                    Die d = diceContainer.getDie(patternCard.getPatternCardCell(i, j).getRolledDieId());
                    if (riga.indexOf(d.getRolledValue()) != -1)
                        break;
                    riga.add(d.getRolledValue());
                } catch (DiceContainerUnsupportedIdException e) {
                    e.printStackTrace();
                }
            }
            if (riga.size()==4)
                result = result + 4;
        }
        return result;
    }
}
