package it.polimi.se2018.model.objectives;

import it.polimi.se2018.model.PatternCard;
import it.polimi.se2018.model.container.DiceContainer;
import it.polimi.se2018.model.container.DiceContainerUnsupportedIdException;
import it.polimi.se2018.model.container.Die;
import it.polimi.se2018.model.container.DieColor;
import javafx.scene.control.Cell;

public class PrivateObjective implements Objective {

    private String description;

    private int id;

    private DieColor color;

    private DiceContainer diceContainer;

    public int getId () {
        return this.id;
    }

    public String getDescription () {
        return this.description;
    }

    public int calculateScore (PatternCard patternCard) throws DiceContainerUnsupportedIdException {
        int result=0;

        for (int x=0; x<=5; x++) {
            for (int y=0; y<=4; y++) {
                Die d = diceContainer.getDie(patternCard.getPatternCardCell(x,y).getThrowedDieId());
                if (this.color.equals(d.getColor()))
                    result = result + d.getThrowedValue();
            }
        }
        return result;
    }
}
