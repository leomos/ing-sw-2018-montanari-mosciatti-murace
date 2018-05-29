package it.polimi.se2018.model.rounds;

import it.polimi.se2018.model.DieNotPresentException;
import it.polimi.se2018.model.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class TestRoundTrack {

    private RoundTrack roundTrack;

    @Before
    public void setUp() {
        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player(0, "Gianni"));
        players.add(new Player(1,"Fabrizio"));
        roundTrack = new RoundTrack(players);
    }

    @After
    public void tearDown() {
        roundTrack = null;
        assertNull(roundTrack);
    }

    @Test
    public void startNextRound_CalledOneTime_currentRoundIdShouldBe0() {
        try {
            roundTrack.startNextRound();
        } catch (RoundTrackNoMoreRoundsException e) {
            fail();
        }
        assertEquals(0,roundTrack.getCurrentRound().getId());
    }

    @Test
    public void startNextRound_Called10Times_currentRoundIdShouldBe9() {
        for (int i = 0; i < 10; i++) {
            try {
                roundTrack.startNextRound();
            } catch (RoundTrackNoMoreRoundsException e) {
                fail();
            }
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
        int[] diceLeft = new int[3];
        diceLeft[0] = 34;
        diceLeft[1] = 22;
        diceLeft[2] = 90;
        try {
            roundTrack.startNextRound();
        } catch (RoundTrackNoMoreRoundsException e) {
            fail();
        }
        try {
            roundTrack.setRolledDiceLeftForCurrentRound(diceLeft);
        } catch (RoundTrackTooManyDiceForCurrentPlayers roundTrackTooManyDiceForCurrentPlayers) {
            fail();
        }
        assertEquals(diceLeft, roundTrack.getCurrentRound().getRolledDiceLeft());
    }

    @Test(expected = RoundTrackTooManyDiceForCurrentPlayers.class)
    public void setRolledDiceLeftForCurrentRound_1stParamAsInvalidArrayOfInt_ExceptionThrown()
            throws RoundTrackTooManyDiceForCurrentPlayers {
        int[] diceLeft = new int[6];
        diceLeft[0] = 34;
        diceLeft[1] = 22;
        diceLeft[2] = 90;
        diceLeft[3] = 23;
        diceLeft[4] = 35;
        diceLeft[5] = 89;
        roundTrack.setRolledDiceLeftForCurrentRound(diceLeft);
    }

    @Test
    public void swapDieInRound_1stParamAsIdPresentInLeftDice2ndParamAsValidId_1stParamShouldNotBePresentAnd2ndParamShouldBePresent() {
        int[] diceLeft = new int[3];
        diceLeft[0] = 1;
        diceLeft[1] = 23;
        diceLeft[2] = 42;
        try {
            roundTrack.startNextRound();
        } catch (RoundTrackNoMoreRoundsException e) {
            fail();
        }
        try {
            roundTrack.setRolledDiceLeftForCurrentRound(diceLeft);
        } catch (RoundTrackTooManyDiceForCurrentPlayers roundTrackTooManyDiceForCurrentPlayers) {
            fail();
        }
        try {
            roundTrack.swapDieInRound(23,54);
        } catch (DieNotPresentException e) {
            fail();
        }
        assertTrue(roundTrack.getCurrentRound().isDiePresentInLeftDice(54));
    }

    @Test(expected = DieNotPresentException.class)
    public void swapDieInRound_1stParamAsIdNotPresentInLetDice2ndParamAsValidId_ExceptionThrown() throws DieNotPresentException {
        int[] diceLeft = new int[3];
        diceLeft[0] = 1;
        diceLeft[1] = 23;
        diceLeft[2] = 42;
        try {
            roundTrack.startNextRound();
        } catch (RoundTrackNoMoreRoundsException e) {
            fail();
        }
        try {
            roundTrack.setRolledDiceLeftForCurrentRound(diceLeft);
        } catch (RoundTrackTooManyDiceForCurrentPlayers roundTrackTooManyDiceForCurrentPlayers) {
            fail();
        }
        roundTrack.swapDieInRound(90,54);
    }
}
