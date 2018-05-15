package it.polimi.se2018.model.container;

import org.junit.*;

import static org.junit.Assert.*;

public class TestDiceContainer {

    private DiceContainer diceContainer;

    @Before
    public void setUp() {
        diceContainer = new DiceContainer();
    }

    @After
    public void tearDown() {
        diceContainer = null;
        assertNull(diceContainer);
    }

    @Test
    public void getUnrolledDice_JustAfterCreation_sizeShouldBe90() {
        assertEquals(diceContainer.getUnrolledDice().size(), 90);
    }

    @Test
    public void getDie_50AsParam_shouldNotBeNull() {
        try {
            assertNotNull(diceContainer.getDie(50));
        } catch (DiceContainerUnsupportedIdException e) {
            fail();
        }
    }

    @Test(expected = DiceContainerUnsupportedIdException.class)
    public void getDie_100AsParam_ExceptionThrown() throws DiceContainerUnsupportedIdException {
        diceContainer.getDie(100);
    }

    @Test(expected = DiceContainerUnsupportedIdException.class)
    public void getDie_NegativeNumberAsParam_ExceptionThrown() throws DiceContainerUnsupportedIdException {
        diceContainer.getDie(-1);
    }

    @Test
    public void getUnrolledDice_TwoDiceSetAsRolled_sizeShouldBe88() {
        try {
            diceContainer.getDie(0).setRolled(true);
            diceContainer.getDie(1).setRolled(true);
        } catch (DieRolledStateNotChangedException e) {
            fail();
        } catch (DiceContainerUnsupportedIdException e) {
            fail();
        }
        assertEquals(88, diceContainer.getUnrolledDice().size());
    }

}
