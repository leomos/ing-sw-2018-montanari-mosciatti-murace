package it.polimi.se2018.model.container;

import org.junit.*;

import static org.junit.Assert.*;

/**
 * {@link it.polimi.se2018.model.container.Die}
 */
public class TestDie {

    private Die die;

    @Before
    public void setUp() {
        die = new Die(DieColor.BLUE);
    }

    @After
    public void tearDown() {
        die = null;
        assertNull(die);
    }

    @Test
    public void setRolled_TrueAsParam_isRolledShouldBeTrue() {
        try {
            die.setRolled(true);
        } catch (DieRolledStateNotChangedException e) {
            fail();
        }
        assertTrue(die.isRolled());
    }

    @Test(expected = DieRolledStateNotChangedException.class)
    public void setRolled_FalseAsParam_ExceptionThrown() throws DieRolledStateNotChangedException {
        die.setRolled(false);
    }

    @Test
    public void setRolledValue_5AsParam_getRolledValueShouldBe5() {
        try {
            die.setRolledValue(5);
        } catch (DieRolledValueOutOfBoundException e) {
            fail();
        }
        assertEquals(5,die.getRolledValue());
    }

    @Test(expected = DieRolledValueOutOfBoundException.class)
    public void setRolledValue_NegativeNumberAsParam_ExcepionThrown() throws DieRolledValueOutOfBoundException {
        die.setRolledValue(-1);
    }

    @Test(expected = DieRolledValueOutOfBoundException.class)
    public void setRolledValue_7AsParam_ExceptionThrown() throws DieRolledValueOutOfBoundException {
        die.setRolledValue(7);
    }

    @Test(expected = DieRolledValueOutOfBoundException.class)
    public void setRolledValue_0AsParam_ExceptionThrown() throws DieRolledValueOutOfBoundException {
        die.setRolledValue(0);
    }

    @Test
    public void roll_JustAfterInitializationOfDie_rolledValueShouldNotBe0() {
        die.roll();
        assertNotEquals(0, die.getRolledValue());
        assertTrue(die.isRolled());
    }
}
