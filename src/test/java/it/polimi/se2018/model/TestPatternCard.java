package it.polimi.se2018.model;

import it.polimi.se2018.model.container.DiceContainer;
import it.polimi.se2018.model.container.DiceContainerUnsupportedIdException;
import it.polimi.se2018.model.container.DieRolledValueOutOfBoundException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;

public class TestPatternCard {
    private DiceContainer diceContainer;
    private PatternCard patternCard;

    @Before
    public void setUp() {
        diceContainer = new DiceContainer();
        patternCard = new PatternCard(diceContainer, "0,Virtus,5,4025g006g203g405g100");
    }

    @After
    public void tearDown(){
        diceContainer = null;
        patternCard = null;
    }

    @Test
    public void getPatternCardCell_FirstParamAs2SecondParamAs2_getPatternCardCellShouldBeGREEN(){
        assertEquals(4, patternCard.getPatternCardCell(3,2).getValueConstraint());
    }

    @Test
    public void checkDieInAdjacentCells_FirstParamAs3SecondParamAs3_returnShouldBeFalse() throws DiceContainerUnsupportedIdException {
        try {
            diceContainer.getDie(5).setRolledValue(4);
            diceContainer.getDie(50).setRolledValue(4);
        } catch (DieRolledValueOutOfBoundException e) {
            fail();
        }
        patternCard.getPatternCardCell(0,0).setRolledDieId(50, false, false);
        assertEquals(false, patternCard.checkDieInAdjacentCells(3,3));
    }

    @Test
    public void checkDieInAdjacentCells_FirstParamAs1SecondParamAs1_returnShouldBeTrue() throws DiceContainerUnsupportedIdException {
        try {
            diceContainer.getDie(5).setRolledValue(4);
            diceContainer.getDie(50).setRolledValue(4);
        } catch (DieRolledValueOutOfBoundException e) {
            fail();
        }
        patternCard.getPatternCardCell(0,0).setRolledDieId(50, false, false);
        assertEquals(true, patternCard.checkDieInAdjacentCells(1,1));
    }


    @Test
    public void checkProximityCellsValidity_FirstParamAs0SecondParamAs1_returnShouldBeFalse() throws DiceContainerUnsupportedIdException {
        try {
            diceContainer.getDie(5).setRolledValue(4);
            diceContainer.getDie(50).setRolledValue(4);
            diceContainer.getDie(6).setRolledValue(2);
        } catch (DieRolledValueOutOfBoundException e) {
            fail();
        }
        patternCard.getPatternCardCell(0,0).setRolledDieId(5, false, false);
        assertEquals(false, patternCard.checkProximityCellsValidity(50,0,1));
        assertEquals(false, patternCard.checkProximityCellsValidity(6,1,0));
    }

    @Test
    public void checkProximityCellsValidity_FirstParamAs2SecondParamAs2_returnShouldBeTrue() throws DiceContainerUnsupportedIdException {
        try {
            diceContainer.getDie(5).setRolledValue(4);
            diceContainer.getDie(50).setRolledValue(3);
            diceContainer.getDie(22).setRolledValue(2);
        } catch (DieRolledValueOutOfBoundException e) {
            fail();
        }
        patternCard.getPatternCardCell(2,1).setRolledDieId(5, false, false);
        assertEquals(true, patternCard.checkProximityCellsValidity(50,2,2));
        assertEquals(true, patternCard.checkProximityCellsValidity(6,2,2));
    }
}

