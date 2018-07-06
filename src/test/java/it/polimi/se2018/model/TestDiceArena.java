package it.polimi.se2018.model;

import it.polimi.se2018.model.container.DiceContainer;
import it.polimi.se2018.model.container.DieRolledValueOutOfBoundException;
import it.polimi.se2018.model.rounds.DieNotPresentException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestDiceArena {

    DiceArena diceArena;

    DiceContainer diceContainer;

    @Before
    public void setup(){
        diceContainer = new DiceContainer();
        diceArena = new DiceArena(7, diceContainer);
    }

    @Test
    public void checkRollDiceIntoArena_NumberOfPlayersAs7_NoDiceShouldHaveValueStillAt0() {

        diceArena.rollDiceIntoArena();
        assertNotEquals(0, diceContainer.getDie(diceArena.getArena().get(0)).getRolledValue());
        assertNotEquals(0, diceContainer.getDie(diceArena.getArena().get(1)).getRolledValue());
        assertNotEquals(0, diceContainer.getDie(diceArena.getArena().get(2)).getRolledValue());
        assertNotEquals(0, diceContainer.getDie(diceArena.getArena().get(3)).getRolledValue());
        assertNotEquals(0, diceContainer.getDie(diceArena.getArena().get(4)).getRolledValue());
        assertNotEquals(0, diceContainer.getDie(diceArena.getArena().get(5)).getRolledValue());
        assertNotEquals(0, diceContainer.getDie(diceArena.getArena().get(6)).getRolledValue());
    }

    @Test
    public void checkSwap_FirstParamAs5SecondParamAs87_5ShouldNotBeInArenaBut87Should(){
        diceArena.getArena().add(5);
        try {
            diceArena.swapDie(5,87);
        } catch (DieNotPresentException e) {
            e.printStackTrace();
        }
        assertTrue(diceArena.getArena().contains(87));
        assertFalse(diceArena.getArena().contains(5));

    }

    @Test(expected = DieNotPresentException.class)
    public void checkSwap_FirstParamAs7SecondParamAs87_ExceptionThrown() throws DieNotPresentException {
        diceArena.getArena().add(5);
        diceArena.swapDie(7,87);
    }

    @Test
    public void checkRolledDiceIntoArena_ThrowDiceTwice_ExpectedSize7(){
        diceArena.rollDiceIntoArena();
        assertEquals(7, diceArena.getArena().size());
    }

    @Test
    public void checkRemoveDie_DiePresentFirstAndNotPresentAfter_ExpectedTrueAndFalse(){
        diceArena.rollOneDieIntoDiceArena(0, 12,3);
        assertEquals(true, diceArena.getArena().contains(12));
        diceArena.removeDieFromDiceArena(12);
        assertEquals(false, diceArena.getArena().contains(12));
    }



    @Test
    public void checkUpdateRepresentation_ParamsAreRandom_CheckValuesAreAcceptable() {

        diceArena.rollDiceIntoArena();

        diceArena.getRepresentation();

        for(int i = 0; i < diceArena.getRepresentation().length(); i+=4){
            int k = Integer.parseInt(diceArena.getRepresentation().substring(i, i + 2));
            int m = Character.getNumericValue(diceArena.getRepresentation().charAt(i+3));
            assertTrue(0 <= k);
            assertTrue(90 > k);
            assertTrue(0 < m);
            assertTrue(7 > m);
        }
    }

    @Test
    public void checkRemoveDieFromDiceArena_23AsParam_SizeOfDiceArenaShouldBe6() {
        diceArena.rollOneDieIntoDiceArena(0, 4, 4);
        diceArena.rollOneDieIntoDiceArena(1, 11, 4);
        diceArena.rollOneDieIntoDiceArena(2, 23, 4);
        diceArena.rollOneDieIntoDiceArena(3, 34, 4);
        diceArena.rollOneDieIntoDiceArena(4, 54, 4);
        diceArena.rollOneDieIntoDiceArena(5, 63, 4);
        diceArena.rollOneDieIntoDiceArena(6, 85, 4);
        diceArena.removeDieFromDiceArena(23);
        assertEquals(6, diceArena.getArena().size());
    }


}
