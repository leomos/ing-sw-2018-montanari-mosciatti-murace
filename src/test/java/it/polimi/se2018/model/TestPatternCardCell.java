package it.polimi.se2018.model;

import it.polimi.se2018.model.container.*;
import it.polimi.se2018.model.patternCard.CellIsEmptyException;
import it.polimi.se2018.model.patternCard.PatternCardCell;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class TestPatternCardCell {

    private DiceContainer diceContainer;
    private PatternCardCell patternCardCell;

    @Before
    public void setUp() {
        diceContainer = new DiceContainer();
        patternCardCell = new PatternCardCell(null, 4);
    }

    @After
    public void tearDown() {
        patternCardCell = null;
        diceContainer = null;
    }

    @Test
    public void setRolledDieId_FirstParamAs3SecondParamAsFalseThirdParamAsFalse_getRolledDieIDShouldBe3() {
        try {
            diceContainer.getDie(3).setRolledValue(4);
            patternCardCell.setRolledDieId(3, false, false);
        } catch (DieRolledValueOutOfBoundException e) {
            fail();
        } catch (DiceContainerUnsupportedIdException e) {
            fail();
        }
        assertEquals(3, patternCardCell.getRolledDieId());
    }

    /*@Test
    public void setRolledDieId_FirstParamAs6SecondParamAsFalseThirdParamAsFalse_getRolledDieIDShouldBeNegativeOne() {
        try {
            diceContainer.getDie(3).setRolledValue(6);
            patternCardCell.setRolledDieId(3, false, false);
        } catch (DieRolledValueOutOfBoundException e) {
            fail();
        } catch (DiceContainerUnsupportedIdException e) {
            fail();
        }
        assertEquals(-1, patternCardCell.getRolledDieId());
    }*/

    @Test
    public void setRolledDieId_FirstParamAs6SecondParamAsFalseThirdParamAsFalse_getRolledDieIDShouldBe5() {
        try {
            diceContainer.getDie(5).setRolledValue(6);
            patternCardCell.setRolledDieId(5, true, false);
        } catch (DieRolledValueOutOfBoundException e) {
            fail();
        } catch (DiceContainerUnsupportedIdException e) {
            fail();
        }
        assertEquals(5, patternCardCell.getRolledDieId());
    }

    @Test
    public void removeDie() throws DiceContainerUnsupportedIdException {
        patternCardCell.setRolledDieId(0, false, false);
        try {
            patternCardCell.removeDie();
        } catch (CellIsEmptyException e) {
            e.printStackTrace();
        }
        assertEquals(-1, patternCardCell.getRolledDieId());
    }

    @Test(expected = CellIsEmptyException.class)
    public void checkRemoveDie_NoDieInCell_ExpeptionThrown() throws CellIsEmptyException{
        patternCardCell.removeDie();
    }

    @Test
    public void checkDieValidity_RedDieOnBlueCellWithAndWithoutIgnoreColorConstraint_ExpectedTrueAndFalse(){
        patternCardCell = new PatternCardCell(DieColor.BLUE, 0);
        Die die = new Die(DieColor.RED);
        try {
            die.setRolledValue(4);
        } catch (DieRolledValueOutOfBoundException e) {
            e.printStackTrace();
        }

        assertEquals(false, patternCardCell.checkDieValidity(die.getRolledValue(), die.getColor(), false, false));
        assertEquals(true, patternCardCell.checkDieValidity(die.getRolledValue(), die.getColor(), false, true));

    }

    @Test
    public void checkDieValidity_DieWithWrongValueComparedToCellWithAndWithoutIgnoreColorConstraint_ExpectedTrueAndFalse(){
        patternCardCell = new PatternCardCell(null, 2);
        Die die = new Die(DieColor.RED);
        try {
            die.setRolledValue(4);
        } catch (DieRolledValueOutOfBoundException e) {
            e.printStackTrace();
        }

        assertEquals(false, patternCardCell.checkDieValidity(die.getRolledValue(), die.getColor(), false, false));
        assertEquals(true, patternCardCell.checkDieValidity(die.getRolledValue(), die.getColor(), true, false));

    }

    @Test
    public void checkDieValidity_RedDieWithValueOf4OnEmptyCell_ExpectedTrue(){
        patternCardCell = new PatternCardCell(null, 0);
        Die die = new Die(DieColor.RED);

        try {
            die.setRolledValue(4);
        } catch (DieRolledValueOutOfBoundException e) {
            e.printStackTrace();
        }

        assertEquals(true, patternCardCell.checkDieValidity(die.getRolledValue(), die.getColor(), false, false));
        assertEquals(true, patternCardCell.checkDieValidity(die.getRolledValue(), die.getColor(), false, true));
        assertEquals(true, patternCardCell.checkDieValidity(die.getRolledValue(), die.getColor(), true, false));
        assertEquals(true, patternCardCell.checkDieValidity(die.getRolledValue(), die.getColor(), true, true));

    }
}
