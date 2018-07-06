package it.polimi.se2018.model.rounds;

import it.polimi.se2018.model.Table;
import it.polimi.se2018.model.container.DiceContainer;
import it.polimi.se2018.model.container.DieRolledValueOutOfBoundException;
import it.polimi.se2018.model.player.Player;
import it.polimi.se2018.model.player.PlayerHasNotSetDieThisTurnException;
import org.junit.*;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class TestRound {

    private Round round;

    private DiceContainer diceContainer;

    private Table table;

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
        HashMap<Integer, String> HM = new HashMap<>();
        HM.put(0, "Mat");
        HM.put(1, "Leo");
        HM.put(34, "Ale");
        table = new Table(HM);
        round.setFirstPlayer(0);
        for (int i = 0; i < 5; i++) {
            try {
                round.setNextPlayer(table);
            } catch (RoundFinishedException e) {
                e.printStackTrace();
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
        HashMap<Integer, String> HM = new HashMap<>();
        HM.put(0, "Mat");
        HM.put(1, "Leo");
        HM.put(34, "Ale");
        table = new Table(HM);
        round.setPlayers(players);
        try {
            round.setFirstPlayer(1);
        } catch (RoundFirstPlayerAlreadySetException roundFirstPlayerAlreadySet) {
            fail();
        }
        for (int i = 0; i < 6; i++) {
            round.setNextPlayer(table);
        }
        fail();
        assertEquals(1, round.getIdPlayerPlaying());
    }

    @Test
    public void turnsPlayedByPlayer_After1RoundPlayedByPlayerWithId34_turnsPlayedShouldBe1() throws RoundFinishedException {
        ArrayList<Integer> players = new ArrayList<>();
        players.add(34);
        players.add(1);
        players.add(0);
        round.setPlayers(players);
        HashMap<Integer, String> HM = new HashMap<>();
        HM.put(0, "Mat");
        HM.put(1, "Leo");
        HM.put(34, "Ale");
        table = new Table(HM);
        try {
            round.setFirstPlayer(0);
        } catch (RoundFirstPlayerAlreadySetException roundFirstPlayerAlreadySet) {
            fail();
        }
        round.setNextPlayer(table);
        assertEquals(1,round.turnsPlayedByPlayer(34));
    }

    @Test
    public void turnsPlayedByPlayer_After1RoundStartingFrom34_turnsPlayedBy0ShouldBe0() throws RoundFinishedException {
        ArrayList<Integer> players = new ArrayList<>();
        players.add(34);
        players.add(1);
        players.add(0);
        round.setPlayers(players);
        HashMap<Integer, String> HM = new HashMap<>();
        HM.put(0, "Mat");
        HM.put(1, "Leo");
        HM.put(34, "Ale");
        table = new Table(HM);
        try {
            round.setFirstPlayer(34);
        } catch (RoundFirstPlayerAlreadySetException roundFirstPlayerAlreadySet) {
            fail();
        }
        round.setNextPlayer(table);
        assertEquals(0,round.turnsPlayedByPlayer(0));
    }

    @Test
    public void turnsPlayedByPlayer_After4RoundsStartingFrom34_turnsPlayedBy1ShouldBe1() throws RoundFinishedException {
        ArrayList<Integer> players = new ArrayList<>();
        players.add(34);
        players.add(1);
        players.add(0);
        round.setPlayers(players);
        HashMap<Integer, String> HM = new HashMap<>();
        HM.put(0, "Mat");
        HM.put(1, "Leo");
        HM.put(34, "Ale");
        table = new Table(HM);
        try {
            round.setFirstPlayer(34);
        } catch (RoundFirstPlayerAlreadySetException roundFirstPlayerAlreadySet) {
            fail();
        }
        round.setNextPlayer(table);
        round.setNextPlayer(table);
        round.setNextPlayer(table);
        assertEquals(1, round.turnsPlayedByPlayer(1));
    }

    @Test
    public void isSecondPartOfRound_after4TurnInARoundWith4Players_allAssersShouldWork() throws RoundFinishedException {
        ArrayList<Integer> players = new ArrayList<>();
        players.add(34);
        players.add(21);
        players.add(1);
        players.add(0);
        round.setPlayers(players);
        HashMap<Integer, String> HM = new HashMap<>();
        HM.put(0, "Mat");
        HM.put(1, "Leo");
        HM.put(34, "Ale");
        HM.put(21, "Ale");
        table = new Table(HM);
        try {
            round.setFirstPlayer(34);
        } catch (RoundFirstPlayerAlreadySetException roundFirstPlayerAlreadySet) {
            fail();
        }
        round.setNextPlayer(table);
        assertEquals(false, round.isSecondPartOfRound());
        round.setNextPlayer(table);
        assertEquals(false, round.isSecondPartOfRound());
        round.setNextPlayer(table);
        assertEquals(false, round.isSecondPartOfRound());
        round.setNextPlayer(table);
        assertEquals(true, round.isSecondPartOfRound());
        round.setNextPlayer(table);
        assertEquals(true, round.isSecondPartOfRound());
        round.setNextPlayer(table);
        assertEquals(true, round.isSecondPartOfRound());
        round.setNextPlayer(table);
        assertEquals(true, round.isSecondPartOfRound());
    }

    @Test
    public void getRepresentation_3DiceLeftWithVariousValues_getRepresentationShouldBeRight() {
        ArrayList<Integer> dice = new ArrayList<>();

        try {
            dice.add(45);
            diceContainer.getDie(45).setRolledValue(3);
            dice.add(67);
            diceContainer.getDie(67).setRolledValue(4);
            dice.add(9);
            diceContainer.getDie(9).setRolledValue(5);
        } catch (DieRolledValueOutOfBoundException e) {
            fail();
        }

        round.setRolledDiceLeft(dice);
        assertEquals(round.getRepresentation(), "45g367b409r5");
    }

    @Test
    public void swapDie_3DiceLeftSwap2ndWith10_getRolledDiceLeftShouldContain10AndNot9() {
        ArrayList<Integer> dice = new ArrayList<>();

        try {
            dice.add(45);
            diceContainer.getDie(45).setRolledValue(3);
            dice.add(67);
            diceContainer.getDie(67).setRolledValue(4);
            dice.add(9);
            diceContainer.getDie(9).setRolledValue(5);

            diceContainer.getDie(10).setRolledValue(1);
        } catch (DieRolledValueOutOfBoundException e) {
            fail();
        }

        round.setRolledDiceLeft(dice);

        try {
            round.swapDie(2,10);
        } catch (DieNotPresentException e) {
            fail();
        }

        assertFalse(round.getRolledDiceLeft().contains(9));
        assertTrue(round.getRolledDiceLeft().contains(10));
    }

    @Test(expected = DieNotPresentException.class)
    public void swapDie_3DiceLeftSwap3rdWith10_ExceptionThrown() throws DieNotPresentException {
        ArrayList<Integer> dice = new ArrayList<>();

        try {
            dice.add(45);
            diceContainer.getDie(45).setRolledValue(3);
            dice.add(67);
            diceContainer.getDie(67).setRolledValue(4);
            dice.add(9);
            diceContainer.getDie(9).setRolledValue(5);

            diceContainer.getDie(10).setRolledValue(1);
        } catch (DieRolledValueOutOfBoundException e) {
            fail();
        }

        round.setRolledDiceLeft(dice);

        round.swapDie(3,10);
    }

    @Test
    public void giveConsecutiveTurnsToPlayer_4Players_hasSetDieThisTurnOfTestPlayerShouldBeFalse() {
        Player test = new Player(34, "piero");
        ArrayList<Integer> players = new ArrayList<>();
        players.add(21);
        players.add(1);
        players.add(10);
        players.add(test.getId());
        round.setPlayers(players);

        HashMap<Integer, String> HM = new HashMap<>();
        HM.put(10, "Mat");
        HM.put(1, "Leo");
        HM.put(test.getId(), test.getName());
        HM.put(21, "Ale");
        table = new Table(HM);

        try {
            round.setFirstPlayer(1);
            round.setNextPlayer(table);
            round.setNextPlayer(table);
        } catch (RoundFirstPlayerAlreadySetException roundFirstPlayerAlreadySet) {
            fail();
        } catch (RoundFinishedException e) {
            e.printStackTrace();
        }
        try {
            round.giveConsecutiveTurnsToPlayer(test.getId(), test);
        } catch (RoundPlayerAlreadyPlayedSecondTurnException e) {
            fail();
        } catch (PlayerHasNotSetDieThisTurnException e) {
            fail();
        }
        assertFalse(test.hasSetDieThisTurn());
    }

}
