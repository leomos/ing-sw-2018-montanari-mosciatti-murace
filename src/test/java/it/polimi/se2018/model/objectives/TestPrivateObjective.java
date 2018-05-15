package it.polimi.se2018.model.objectives;

import it.polimi.se2018.model.PatternCard;
import it.polimi.se2018.model.container.DiceContainer;
import org.junit.Before;


public class TestPrivateObjective {

    private DiceContainer diceContainer;
    private PatternCard patternCard;

    @Before
    public void setUp() {
        diceContainer = new DiceContainer();
        patternCard = new PatternCard(diceContainer, "0,Virtus,5,4025g006g203g405g100");
    }
}
