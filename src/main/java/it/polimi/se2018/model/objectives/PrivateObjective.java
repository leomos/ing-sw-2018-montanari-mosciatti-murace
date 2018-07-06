package it.polimi.se2018.model.objectives;

import it.polimi.se2018.model.patternCard.PatternCard;
import it.polimi.se2018.model.container.DiceContainer;
import it.polimi.se2018.model.container.Die;
import it.polimi.se2018.model.container.DieColor;

import static it.polimi.se2018.model.container.DieColor.*;

public class PrivateObjective implements Objective {

    private String name;

    private String description;

    private int id;

    private DieColor color;

    private DiceContainer diceContainer;

    public PrivateObjective(DiceContainer diceContainer, int id, String name, String description){
        this.diceContainer = diceContainer;
        this.id = id;
        this.name = name;
        this.description = description;
        switch (this.id){
            case 1: color = RED; break;
            case 2: color = YELLOW; break;
            case 3: color = GREEN; break;
            case 4: color = BLUE; break;
            case 5: color = PURPLE; break;
            default: color = null; break;
        }
    }

    public int getId () {
        return this.id;
    }

    public String getName() {
        return name;
    }

    public DieColor getColor() {
        return color;
    }

    public String getDescription () {
        return this.description;
    }

    /**
     * Calculates the score of the pattern card given: for each die of the color of the private objective,
     * the results increments of the die rolled value
     * @param patternCard
     * @return PatternCard score at the end of the game based on private objective of card
     */
    public int calculateScore (PatternCard patternCard) {
        int result=0;

        for (int x=0; x<5; x++) {
            for (int y=0; y<4; y++) {
                if(!patternCard.getPatternCardCell(x,y).isEmpty()) {
                    Die d = null;
                    d = diceContainer.getDie(patternCard.getPatternCardCell(x, y).getRolledDieId());
                    if (this.color.equals(d.getColor()))
                        result = result + d.getRolledValue();
                }
            }
        }
        return result;
    }
}
