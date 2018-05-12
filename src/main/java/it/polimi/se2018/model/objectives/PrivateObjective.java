package it.polimi.se2018.model.objectives;

import it.polimi.se2018.model.PatternCard;
import it.polimi.se2018.model.container.DiceContainer;
import it.polimi.se2018.model.container.DiceContainerUnsupportedIdException;
import it.polimi.se2018.model.container.Die;
import it.polimi.se2018.model.container.DieColor;

public class PrivateObjective implements Objective {
    private String name;

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

    /**
     *
     * @param patternCard
     * @return PatternCard score at the end of the game based on private objective of card
     * @throws DiceContainerUnsupportedIdException if die's id is not valid
     */
    public int calculateScore (PatternCard patternCard) throws DiceContainerUnsupportedIdException {
        int result=0;

        for (int x=0; x<=5; x++) {
            for (int y=0; y<=4; y++) {
                Die d = diceContainer.getDie(patternCard.getPatternCardCell(x,y).getRolledDieId());
                if (this.color.equals(d.getColor()))
                    result = result + d.getRolledValue();
            }
        }
        return result;
    }
}
