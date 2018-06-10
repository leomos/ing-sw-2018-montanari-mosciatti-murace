package it.polimi.se2018.model.rounds;

import it.polimi.se2018.model.container.DiceContainer;
import org.junit.*;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class TestRound {

    private Round round;

    private DiceContainer diceContainer;

    @Before
    public void setUp() {
        diceContainer = new DiceContainer();
        round = new Round(0, diceContainer);
    }

    @After
    public void tearDown() {
        diceContainer = null;
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
        } catch (RoundFirstPlayerAlreadySetException roundFirstPlayerAlreadySet) {
            fail();
        }
        assertEquals(5, round.getIdPlayerPlaying());
    }

    @Test(expected = RoundFirstPlayerAlreadySetException.class)
    public void setFirstPlayer_1stParamAs6AfterIdAlreadySetTo5_ExcpetionThrown() throws RoundFirstPlayerAlreadySetException {
        ArrayList<Integer> players = new ArrayList<>();
        players.add(34);
        players.add(5);
        players.add(0);
        round.setPlayers(players);
        try {
            round.setFirstPlayer(5);
        } catch (RoundFirstPlayerAlreadySetException roundFirstPlayerAlreadySet) {
            fail();
        }

        round.setFirstPlayer(6);
        fail();
    }

    @Test
    public void setNextPlayerCalled5Times_PlayersWith3Elements_ShouldNotThrowException() throws RoundFirstPlayerAlreadySetException {
        ArrayList<Integer> players = new ArrayList<>();
        players.add(34);
        players.add(1);
        players.add(0);
        round.setPlayers(players);
        round.setFirstPlayer(0);
        for (int i = 0; i < 5; i++) {
            round.setNextPlayer();
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
        } catch (RoundFirstPlayerAlreadySetException roundFirstPlayerAlreadySet) {
            fail();
        }
        for (int i = 0; i < 6; i++) {
            round.setNextPlayer();
        }
        fail();
        assertEquals(1, round.getIdPlayerPlaying());
    }

    @Test
    public void turnsPlayedByPlayer_After1RoundPlayedByPlayerWithId34_turnsPlayedShouldBe1() {
        ArrayList<Integer> players = new ArrayList<>();
        players.add(34);
        players.add(1);
        players.add(0);
        round.setPlayers(players);
        try {
            round.setFirstPlayer(0);
        } catch (RoundFirstPlayerAlreadySetException roundFirstPlayerAlreadySet) {
            fail();
        }
        round.setNextPlayer();
        assertEquals(1,round.turnsPlayedByPlayer(34));
    }

    @Test
    public void turnsPlayedByPlayer_After1RoundStartingFrom34_turnsPlayedBy0ShouldBe0() {
        ArrayList<Integer> players = new ArrayList<>();
        players.add(34);
        players.add(1);
        players.add(0);
        round.setPlayers(players);
        try {
            round.setFirstPlayer(34);
        } catch (RoundFirstPlayerAlreadySetException roundFirstPlayerAlreadySet) {
            fail();
        }
        round.setNextPlayer();
        assertEquals(0,round.turnsPlayedByPlayer(0));
    }

    @Test
    public void turnsPlayedByPlayer_After4RoundsStartingFrom34_turnsPlayedBy1ShouldBe1(){
        ArrayList<Integer> players = new ArrayList<>();
        players.add(34);
        players.add(1);
        players.add(0);
        round.setPlayers(players);
        try {
            round.setFirstPlayer(34);
        } catch (RoundFirstPlayerAlreadySetException roundFirstPlayerAlreadySet) {
            fail();
        }
        round.setNextPlayer();
        round.setNextPlayer();
        round.setNextPlayer();
        assertEquals(1, round.turnsPlayedByPlayer(1));
    }
}
