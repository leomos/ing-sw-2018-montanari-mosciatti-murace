package it.polimi.se2018.model;

import it.polimi.se2018.model.container.DiceContainer;
import it.polimi.se2018.model.container.DiceContainerUnsupportedIdException;
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
    public void checkRollDiceIntoArena_NumberOfPlayersAs7_NoDiceShouldHaveValueStillAt0() throws DiceContainerUnsupportedIdException {
        try {
            diceArena.rollDiceIntoArena();
        } catch (DiceContainerUnsupportedIdException e) {
            e.printStackTrace();
        }
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

}
