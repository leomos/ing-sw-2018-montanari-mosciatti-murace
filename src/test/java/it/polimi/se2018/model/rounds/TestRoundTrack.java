package it.polimi.se2018.model.rounds;

import it.polimi.se2018.model.DieNotPresentException;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.container.DiceContainer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class TestRoundTrack {

    private RoundTrack roundTrack;

    private DiceContainer diceContainer;

    @Before
    public void setUp() {
        diceContainer = new DiceContainer();
        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player(0, "Gianni"));
        players.add(new Player(1,"Fabrizio"));
        roundTrack = new RoundTrack(players, diceContainer);
    }

    @After
    public void tearDown() {
        roundTrack = null;
        assertNull(roundTrack);
    }

    @Test
    public void startNextRound_CalledOneTime_currentRoundIdShouldBe0() {
        roundTrack.startNextRound();
        assertEquals(0,roundTrack.getCurrentRound().getId());
    }

    @Test
    public void startNextRound_Called10Times_currentRoundIdShouldBe9() {
        for (int i = 0; i < 10; i++) {
            roundTrack.startNextRound();
        }
        assertEquals(9,roundTrack.getCurrentRound().getId());
    }

    @Test(expected = RoundTrackNoMoreRoundsException.class)
    public void startNextRound_Called11Times_ExceptionThrown() throws RoundTrackNoMoreRoundsException {
        for (int i = 0; i < 11; i++) {
            roundTrack.startNextRound();
        }
        assertEquals(9,roundTrack.getCurrentRound().getId());
    }

    @Test
    public void setRolledDiceLeftForCurrentRound_1stParamAsCustomMadeArrayOfInt_currentRoundRolledDiceLeftShouldBeEqualsToCustomMadeArray(){
        ArrayList<Integer> diceLeft = new ArrayList<Integer>();
        diceLeft.add(34);
        diceLeft.add(22);
        diceLeft.add(90);
        roundTrack.startNextRound();
        roundTrack.setRolledDiceLeftForCurrentRound(diceLeft);
        assertEquals(diceLeft, roundTrack.getCurrentRound().getRolledDiceLeft());
    }

    @Test(expected = RoundTrackTooManyDiceForCurrentPlayers.class)
    public void setRolledDiceLeftForCurrentRound_1stParamAsInvalidArrayOfInt_ExceptionThrown()
            throws RoundTrackTooManyDiceForCurrentPlayers {
        ArrayList<Integer> diceLeft = new ArrayList<Integer>();
        diceLeft.add(34);
        diceLeft.add(22);
        diceLeft.add(90);
        diceLeft.add(23);
        diceLeft.add(35);
        diceLeft.add(89);
        roundTrack.setRolledDiceLeftForCurrentRound(diceLeft);
    }

    @Test
    public void swapDieInRound_1stParamAsIdPresentInLeftDice2ndParamAsValidId_1stParamShouldNotBePresentAnd2ndParamShouldBePresent() {
        ArrayList<Integer> diceLeft = new ArrayList<Integer>();
        diceLeft.add(1);
        diceLeft.add(23);
        diceLeft.add(42);
        roundTrack.startNextRound();
        roundTrack.setRolledDiceLeftForCurrentRound(diceLeft);
        try {
            roundTrack.swapDieInRound(23,54);
        } catch (DieNotPresentException e) {
            fail();
        }
        assertTrue(roundTrack.getCurrentRound().isDiePresentInLeftDice(54));
    }

    @Test(expected = DieNotPresentException.class)
    public void swapDieInRound_1stParamAsIdNotPresentInLetDice2ndParamAsValidId_ExceptionThrown() throws DieNotPresentException {
        ArrayList<Integer> diceLeft = new ArrayList<Integer>();
        diceLeft.add(1);
        diceLeft.add(23);
        diceLeft.add(42);
        roundTrack.startNextRound();
        roundTrack.setRolledDiceLeftForCurrentRound(diceLeft);
        roundTrack.swapDieInRound(90,54);
    }
}
