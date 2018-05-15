package it.polimi.se2018.model;

import it.polimi.se2018.model.container.DiceContainer;
import org.junit.*;
import static org.junit.Assert.*;
import static it.polimi.se2018.model.container.DieColor.*;

public class TestPatternCard {
    private DiceContainer diceContainer;
    private PatternCard patternCard;

    @Before
    public void setUp() {
        diceContainer = new DiceContainer();
        patternCard = new PatternCard(diceContainer, "0,Virtus,5,4025g006g203g405g100");
    }

    @Test
    public void getPatternCardCell_FirstParamAs2SecondParamAs2_getPatternCardCellShouldBeGREEN(){
        assertEquals(GREEN, patternCard.getPatternCardCell(2,2).getColorConstraint());
    }
}
