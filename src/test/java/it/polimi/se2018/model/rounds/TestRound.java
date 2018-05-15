package it.polimi.se2018.model.rounds;

import org.junit.*;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class TestRound {

    private Round round;

    @Before
    public void setUp() {
        round = new Round(0);
    }

    @After
    public void tearDown() {
        round = null;
        assertNull(round);
    }

    @Test
    public void setFirstPlayer_1stParamAs5_getIdPlayerPlayingShouldBe5() {
        ArrayList<Integer> players = new ArrayList<>();
        players.add(34);
        players.add(5);
        players.add(0);
        round.setPlayers(players);
        try {
            round.setFirstPlayer(5);
        } catch (RoundFirstPlayerAlreadySet roundFirstPlayerAlreadySet) {
            roundFirstPlayerAlreadySet.printStackTrace();
        }
        assertEquals(5, round.getIdPlayerPlaying());
    }

    @Test(expected = RoundFirstPlayerAlreadySet.class)
    public void setFirstPlayer_1stParamAs6AfterIdAlreadySetTo5_ExcpetionThrown() throws RoundFirstPlayerAlreadySet {
        ArrayList<Integer> players = new ArrayList<>();
        players.add(34);
        players.add(5);
        players.add(0);
        round.setPlayers(players);
        try {
            round.setFirstPlayer(5);
        } catch (RoundFirstPlayerAlreadySet roundFirstPlayerAlreadySet) {
            roundFirstPlayerAlreadySet.printStackTrace();
        }

        round.setFirstPlayer(6);
        fail();
    }

    @Test
    public void setNextPlayerCalled5Times_PlayersWith3Elements_ShouldNotThrowException() throws RoundFirstPlayerAlreadySet {
        ArrayList<Integer> players = new ArrayList<>();
        players.add(34);
        players.add(1);
        players.add(0);
        round.setPlayers(players);
        round.setFirstPlayer(0);
        for (int i = 0; i < 5; i++) {
            try {
                round.setNextPlayer();
            } catch (RoundFinishedException e) {
                fail();
            }
        }
        assertEquals(0, round.getIdPlayerPlaying());
    }

    @Test(expected = RoundFinishedException.class)
    public void setNextPlayerCalled6Times_PlayersWith3Elements_ShouldNotThrowException() throws RoundFinishedException {
        ArrayList<Integer> players = new ArrayList<>();
        players.add(34);
        players.add(1);
        players.add(0);
        round.setPlayers(players);
        try {
            round.setFirstPlayer(1);
        } catch (RoundFirstPlayerAlreadySet roundFirstPlayerAlreadySet) {
            fail();
        }
        for (int i = 0; i < 6; i++) {
            round.setNextPlayer();
        }
        fail();
        assertEquals(1, round.getIdPlayerPlaying());
    }
}
