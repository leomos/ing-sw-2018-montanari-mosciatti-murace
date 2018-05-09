package it.polimi.se2018.model.container;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * {@link it.polimi.se2018.model.container.Die}
 */
public class TestDie {

    private static Die die;

    @BeforeClass
    public static void setUpClass() {
        die = new Die(DieColor.BLUE);
    }

    @AfterClass
    public static void tearDownClass() {
        die = null;
        assertNull(die);
    }

    @Test
    public void setRolled_TrueAs1stParam_isRolledShouldBeTrue() {
        try {
            die.setRolled(true);
        } catch (DieRolledStateNotChangedException e) {
            fail();
        }
        assertTrue(die.isRolled());
    }

    @Test(expected = DieRolledStateNotChangedException.class)
    public void setRolled_FalseAs1stParam_ExceptionThrown() throws DieRolledStateNotChangedException {
        die.setRolled(false);
    }

    @Test
    public void setRolledValue_5As1stParam_getRolledValueShouldBe5() {
        try {
            die.setRolledValue(5);
        } catch (DieRolledValueOutOfBoundException e) {
            fail();
        }
        assertEquals(5,die.getRolledValue());
    }

    @Test(expected = DieRolledValueOutOfBoundException.class)
    public void setRolledValue_NegativeNumberAs1stParam_ExcepionThrown() throws DieRolledValueOutOfBoundException {
        die.setRolledValue(-1);
    }

    @Test(expected = DieRolledValueOutOfBoundException.class)
    public void setRolledValue_7As1stParam_ExceptionThrown() throws DieRolledValueOutOfBoundException {
        die.setRolledValue(7);
    }

    @Test(expected = DieRolledValueOutOfBoundException.class)
    public void setRolledValue_0As1stParam_ExceptionThrown() throws DieRolledValueOutOfBoundException {
        die.setRolledValue(0);
    }

}
