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
        die.setRolled(true);
        assertTrue(die.isRolled());
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

    @Test
    public void getColorChar_ReturnShouldBeb() {
        assertEquals("b", die.getColorChar());
    }

    @Test
    public void getColorChar_ReturnShouldBer() {
        Die d = new Die(DieColor.RED);
        assertEquals("r", d.getColorChar());
    }

    @Test
    public void getColorChar_ReturnShouldBey() {
        Die d = new Die(DieColor.YELLOW);
        assertEquals("y", d.getColorChar());
    }

    @Test
    public void getColorChar_ReturnShouldBep() {
        Die d = new Die(DieColor.PURPLE);
        assertEquals("p", d.getColorChar());
    }

    @Test
    public void getColorChar_ReturnShouldBeg() {
        Die d = new Die(DieColor.GREEN);
        assertEquals("g", d.getColorChar());
    }

    @Test
    public void turnAround_ParamAs5_FinalValueShouldBe2() {
        Die d = new Die(DieColor.GREEN);

        try {
            d.setRolledValue(5);
        } catch (DieRolledValueOutOfBoundException e) {
            e.printStackTrace();
        }

        d.setRolled(true);

        assertEquals(5, d.getRolledValue());

        d.turnAround();

        assertEquals(2, d.getRolledValue());
    }

    @Test
    public void unroll_ReturnShouldBeTrue() {
        Die d = new Die(DieColor.PURPLE);

        d.roll();
        d.unroll();

        assertEquals(false, d.isRolled());
        assertEquals(0, d.getRolledValue());
    }

    @Test
    public void turnAround_ReturnShouldBe1() {
        try {
            die.setRolledValue(6);
            die.setRolled(true);
        } catch (DieRolledValueOutOfBoundException e) {
            e.printStackTrace();
        }
        die.turnAround();
        assertEquals(1, die.getRolledValue());
    }

    @Test
    public void turnAround_ReturnShouldBe6() {
        try {
            die.setRolledValue(6);
        } catch (DieRolledValueOutOfBoundException e) {
            e.printStackTrace();
        }
        die.turnAround();
        assertEquals(6, die.getRolledValue());
    }
}
