package it.polimi.se2018.model;

import it.polimi.se2018.model.container.DiceContainer;
import it.polimi.se2018.model.container.DiceContainerUnsupportedIdException;
import it.polimi.se2018.model.container.DieRolledValueOutOfBoundException;
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
        patternCardCell = new PatternCardCell(diceContainer, null, 4);
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

    @Test
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
    }

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
}
