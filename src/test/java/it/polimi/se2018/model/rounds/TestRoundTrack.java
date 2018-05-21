package it.polimi.se2018.model.rounds;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

public class TestRoundTrack {

    private RoundTrack roundTrack;

    @Before
    public void setUp() {
        ArrayList<Integer> players = new ArrayList<>();
        players.add(1);
        players.add(2);
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
}
