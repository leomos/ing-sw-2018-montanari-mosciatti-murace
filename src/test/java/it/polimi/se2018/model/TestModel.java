package it.polimi.se2018.model;

import it.polimi.se2018.model.container.DiceContainer;
import it.polimi.se2018.model.container.DieColor;
import it.polimi.se2018.model.container.DieRolledValueOutOfBoundException;
import it.polimi.se2018.model.events.PlayerMessageEndTurn;
import it.polimi.se2018.model.patternCard.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TestModel {

    HashMap<Integer, String> HM = new HashMap<>();

    Model model;

    @Before
    public void setup() {
        HM.put(1, "Mat");
        HM.put(2, "Leo");
        HM.put(3, "Ale");
        model = new Model(HM, 1000);
        model.initSetup();
        assertEquals(GamePhase.SETUPPHASE, model.getGamePhase());
        for (int i = 1; i < 4; i++) {
            model.setChosenPatternCard(model.getTable().getPlayers(i).getPatternCards().get(0).getId(), i);
        }
    }

    @Test
    public void checkInitSetupAndInitGame_NoParametersNecessary_AllPlayersShouldHavePatternCard() {
        assertEquals(model.currentPlayerPlaying(), model.getTable().getRoundTrack().getCurrentRound().getIdPlayerPlaying());
    }

    @Test
    public void checkEndTurn_NoParametersNecessary_PlayerPlayingShouldBeTwo() {
        assertEquals(GamePhase.GAMEPHASE, model.getGamePhase());
        assertEquals(model.currentPlayerPlaying(), model.getTable().getRoundTrack().getCurrentRound().getIdPlayerPlaying());
        model.endTurn(new PlayerMessageEndTurn(1));
        assertEquals(2, model.currentPlayerPlaying());

    }

    @Test
    public void checkEndTurn10times_NoParametersNecessary_GamePhaseShouldBeENDGAMEPHASE() {

        model.initSetup();
        for (int i = 1; i < 4; i++) {
            model.setChosenPatternCard(model.getTable().getPlayers(i).getPatternCards().get(0).getId(), i);
        }
        assertEquals(model.currentPlayerPlaying(), model.getTable().getRoundTrack().getCurrentRound().getIdPlayerPlaying());
        assertEquals(2, model.currentPlayerPlaying());

        for (int i = 0; i < 60; i++)
            model.endTurn(new PlayerMessageEndTurn(model.currentPlayerPlaying()));


        assertEquals(GamePhase.ENDGAMEPHASE, model.getGamePhase());

    }

    @Test
    public void checkToolCard_ParamAsPlayer1_ExpectResult() {

        int toolCardId = model.getTable().getToolCardContainer().getToolCardInPlay().get(0).getToolCardId();

        assertEquals(true, model.checkToolCard(1, toolCardId));
    }

    @Test
    public void checkToolCard_Player1HasNotEnoughTokens_ExpectResult() {
        int toolCardId = model.getTable().getToolCardContainer().getToolCardInPlay().get(0).getToolCardId();
        model.getTable().getPlayers(1).setTokens(0);

        assertEquals(false, model.checkToolCard(1, toolCardId));
    }

    @Test
    public void checkIncrementOrDecrementDieValue_ParamAsIncrementFirstDieBy1_ResultShouldBeTheSameIfTheDieValueIs6() {
        int initialValue = model.getTable().getDiceContainer().getDie(model.getTable().getDiceArena().getArena().get(0)).getRolledValue();
        int initialCost = model.getTable().getToolCardContainer().getToolCard(0).cost();
        model.incrementOrDecrementDieValue(1, 0, 1, 1);
        int finalValue = model.getTable().getDiceContainer().getDie(model.getTable().getDiceArena().getArena().get(0)).getRolledValue();
        int finalCost = model.getTable().getToolCardContainer().getToolCard(0).cost();

        if (initialValue == 6)
            assertEquals(initialValue, finalValue);
        else {
            assertEquals(initialValue + 1, finalValue);
            assertEquals(initialCost + 1, finalCost);
        }

    }

    @Test
    public void checkMoveDieInsidePatternCard_ParamAs11to10_11shouldBeEmptyAnd10ShouldBeNotEmpty() throws DieRolledValueOutOfBoundException, PatternCardNoAdjacentDieException, PatternCardDidNotRespectFirstMoveException, PatternCardNotRespectingCellConstraintException, PatternCardNotRespectingNearbyDieExpection, PatternCardCellIsOccupiedException, PatternCardAdjacentDieException {
        model.getTable().getDiceContainer().getDie(0).setRolledValue(5);
        model.getTable().getDiceContainer().getDie(20).setRolledValue(3);
        model.getTable().getPlayers(1).getChosenPatternCard().setDieInPatternCard(0, 0, 0, true, true, false);
        model.getTable().getPlayers(1).getChosenPatternCard().setFirstMove();
        model.getTable().getPlayers(1).getChosenPatternCard().setDieInPatternCard(20, 1, 1, true, true, false);

        ArrayList<Integer> positions = new ArrayList<>();
        positions.add(1);
        positions.add(1);
        positions.add(1);
        positions.add(0);

        assertEquals(false, model.getTable().getPlayers(1).getChosenPatternCard().getPatternCardCell(0, 0).isEmpty());
        assertEquals(false, model.getTable().getPlayers(1).getChosenPatternCard().getPatternCardCell(1, 1).isEmpty());
        assertEquals(true, model.getTable().getPlayers(1).getChosenPatternCard().getPatternCardCell(1, 0).isEmpty());

        model.moveDieInsidePatternCard(1, positions, true, true, 1);

        assertEquals(false, model.getTable().getPlayers(1).getChosenPatternCard().getPatternCardCell(0, 0).isEmpty());
        assertEquals(true, model.getTable().getPlayers(1).getChosenPatternCard().getPatternCardCell(1, 1).isEmpty());
        assertEquals(false, model.getTable().getPlayers(1).getChosenPatternCard().getPatternCardCell(1, 0).isEmpty());
    }

    @Test
    public void checkTwoDiceInsidePatternCard_ParamAs11to10and22to11_22ShouldBeEmpty() throws DieRolledValueOutOfBoundException, PatternCardNoAdjacentDieException, PatternCardDidNotRespectFirstMoveException, PatternCardNotRespectingCellConstraintException, PatternCardNotRespectingNearbyDieExpection, PatternCardCellIsOccupiedException, PatternCardAdjacentDieException {
        model.getTable().getPlayers(1).setChosenPatternCard(new PatternCard(model.getTable().getDiceContainer(), 13, "FractalDrop", 3, "040y6r020000rp1by000"));

        model.getTable().getDiceContainer().getDie(0).setRolledValue(5);
        model.getTable().getDiceContainer().getDie(20).setRolledValue(3);
        model.getTable().getDiceContainer().getDie(40).setRolledValue(4);
        model.getTable().getPlayers(1).getChosenPatternCard().setDieInPatternCard(0, 0, 2, false, false, false);
        model.getTable().getPlayers(1).getChosenPatternCard().setFirstMove();
        model.getTable().getPlayers(1).getChosenPatternCard().setDieInPatternCard(20, 1, 2, false, false, false);
        model.getTable().getPlayers(1).getChosenPatternCard().setDieInPatternCard(40, 1, 1, false, false, false);


        ArrayList<Integer> positions = new ArrayList<>();
        positions.add(0);
        positions.add(2);
        positions.add(2);
        positions.add(3);
        ArrayList<Integer> positions2 = new ArrayList<>();
        positions2.add(1);
        positions2.add(1);
        positions2.add(3);
        positions2.add(3);

        assertEquals(false, model.getTable().getPlayers(1).getChosenPatternCard().getPatternCardCell(0, 2).isEmpty());
        assertEquals(false, model.getTable().getPlayers(1).getChosenPatternCard().getPatternCardCell(1, 2).isEmpty());
        assertEquals(false, model.getTable().getPlayers(1).getChosenPatternCard().getPatternCardCell(1, 1).isEmpty());
        assertEquals(true, model.getTable().getPlayers(1).getChosenPatternCard().getPatternCardCell(3, 3).isEmpty());
        assertEquals(true, model.getTable().getPlayers(1).getChosenPatternCard().getPatternCardCell(2, 3).isEmpty());

        model.moveTwoDiceInsidePatternCard(1, positions, positions2, 1);

        assertEquals(true, model.getTable().getPlayers(1).getChosenPatternCard().getPatternCardCell(0, 2).isEmpty());
        assertEquals(true, model.getTable().getPlayers(1).getChosenPatternCard().getPatternCardCell(1, 1).isEmpty());
        assertEquals(false, model.getTable().getPlayers(1).getChosenPatternCard().getPatternCardCell(1, 2).isEmpty());
        assertEquals(false, model.getTable().getPlayers(1).getChosenPatternCard().getPatternCardCell(2, 3).isEmpty());
        assertEquals(false, model.getTable().getPlayers(1).getChosenPatternCard().getPatternCardCell(3, 3).isEmpty());

    }

    @Test
    public void checkTurnDieAround_ParamAreRandoms_ResultShouldBeTrue() {
        DiceContainer diceContainer = model.getTable().getDiceContainer();
        model.getTable().getPlayers(1).setTokens(100);

        for (int i = 0; i < model.getTable().getDiceArena().getArena().size(); i++) {
            int dieValue = diceContainer.getDie(model.getTable().getDiceArena().getArena().get(i)).getRolledValue();
            model.turnDieAround(1, i, 7);
            int newDieValue = diceContainer.getDie(model.getTable().getDiceArena().getArena().get(i)).getRolledValue();
            switch (dieValue) {
                case 1:
                    assertEquals(6, newDieValue);
                    break;
                case 2:
                    assertEquals(5, newDieValue);
                    break;
                case 3:
                    assertEquals(4, newDieValue);
                    break;
                case 4:
                    assertEquals(3, newDieValue);
                    break;
                case 5:
                    assertEquals(2, newDieValue);
                    break;
                case 6:
                    assertEquals(1, newDieValue);
                    break;
                default:
                    ;
                    break;
            }
        }
    }

    @Test
    public void checkRerollDiceArena_ParamsAreRandom_CheckColorsAreTheSame() {
        DiceContainer diceContainer = model.getTable().getDiceContainer();
        ArrayList<DieColor> d1 = new ArrayList<>();

        for (int i = 0; i < model.getTable().getDiceArena().getArena().size(); i++)
            d1.add(diceContainer.getDie(model.getTable().getDiceArena().getArena().get(i)).getColor());

        model.rerollDiceArena(1, 1);

        for (int i = 0; i < 5; i++)
            model.endTurn(new PlayerMessageEndTurn(model.currentPlayerPlaying()));

        model.rerollDiceArena(1, 1);

        ArrayList<DieColor> d2 = new ArrayList<>();
        for (int i = 0; i < model.getTable().getDiceArena().getArena().size(); i++)
            d2.add(diceContainer.getDie(model.getTable().getDiceArena().getArena().get(i)).getColor());

        assertEquals(d1, d2);
    }

    @Test
    public void checkGiveConsecutiveRoundsToPlayer2_DoneAtTheBeginningOfTheTurn_CheckPlayer2WillSkipNextTurn() {
        model.getTable().getPlayers(2).hasSetDieThisTurn();
        model.giveConsecutiveRoundsToPlayer(2, 1);
        assertEquals(2, model.currentPlayerPlaying());
        model.endTurn(new PlayerMessageEndTurn(2));
        assertEquals(3, model.currentPlayerPlaying());
        model.endTurn(new PlayerMessageEndTurn(3));
        assertEquals(1, model.currentPlayerPlaying());
        model.endTurn(new PlayerMessageEndTurn(1));
        assertEquals(1, model.currentPlayerPlaying());
        model.endTurn(new PlayerMessageEndTurn(1));
        assertEquals(3, model.currentPlayerPlaying());
        model.endTurn(new PlayerMessageEndTurn(3));
        assertNotEquals(2, model.currentPlayerPlaying());
    }

    @Test
    public void checkRoolDieAgain_ParamsAreRandom_CheckColorIsTheSame() {
        DiceContainer diceContainer = model.getTable().getDiceContainer();

        for (int i = 0; i < model.getTable().getDiceArena().getArena().size(); i++) {

            DieColor d1 = diceContainer.getDie(model.getTable().getDiceArena().getArena().get(i)).getColor();

            model.rollDieAgain(1, i, 1);
            DieColor d2 = diceContainer.getDie(model.getTable().getDiceArena().getArena().get(i)).getColor();

            assertEquals(d1, d2);
        }
    }

    @Test
    public void checkSwapDieAmongRoundTrackAndDiceArena_() throws DieRolledValueOutOfBoundException {

        for (int i = 0; i < 6; i++)
            model.endTurn(new PlayerMessageEndTurn(model.currentPlayerPlaying()));

        model.getTable().getDiceArena().rollOneDieIntoDiceArena(0, 10, 4);
        ArrayList<Integer> diceLeft = new ArrayList<>();
        model.getTable().getDiceContainer().getDie(23).setRolledValue(5);
        diceLeft.add(23);

        model.getTable().getRoundTrack().getRound(0).setRolledDiceLeft(diceLeft);

        model.swapDieAmongRoundTrackAndDiceArena(1, 7,0, 0 , 1);

        assertEquals(true, model.getTable().getDiceArena().getArena().contains(23));
        assertEquals(true, model.getTable().getRoundTrack().getRound(0).getRolledDiceLeft().contains(10));


    }

    @Test
    public void checkGiveValueToDie_ParamAs0And5_FinalValueAs5(){

        model.giveValueToDie(0, 23, 4);
        assertEquals(4, model.getTable().getDiceContainer().getDie(23).getRolledValue());
    }

    @Test
    public void checkDiceColor_PositionsAs00And11_AssertShouldBeTrue() throws DieRolledValueOutOfBoundException {

        model.getTable().getDiceArena().removeDieFromDiceArena(model.getTable().getDiceArena().getArena().get(0));
        model.getTable().getDiceArena().rollOneDieIntoDiceArena(0, 1, 2);

        for (int i = 0; i < 6; i++)
            model.endTurn(new PlayerMessageEndTurn(model.currentPlayerPlaying()));


        model.getTable().getPlayers(1).setChosenPatternCard(new PatternCard(model.getTable().getDiceContainer(), 13, "FractalDrop", 3, "040y6r020000rp1by000"));

        DiceContainer diceContainer = model.getTable().getDiceContainer();
        diceContainer.getDie(0).setRolledValue(1);
        diceContainer.getDie(2).setRolledValue(2);
        model.getTable().getDiceArena().rollOneDieIntoDiceArena(0, 0, 1);
        model.getTable().getDiceArena().rollOneDieIntoDiceArena(1, 2, 2);

        model.setDieInPatternCardFromDiceArena(1, 0, 0, 0, false, 1);
        model.getTable().getPlayers(1).setHasSetDieThisTurn(false);
        model.setDieInPatternCardFromDiceArena(1, 0, 1, 1, false, 1);

        ArrayList<Integer> p1 = new ArrayList<>();
        p1.add(0);
        p1.add(0);
        ArrayList<Integer> p2 = new ArrayList<>();
        p2.add(1);
        p2.add(1);


        assertEquals(true, model.checkDiceColor(1, p1, p2));

    }

    @Test
    public void checkTimesUp_NoParams_CheckPlayerIsActuallySuspended(){
        int playerPlaying = model.currentPlayerPlaying();
        model.timesUp();

        assertEquals(true, model.getTable().getPlayers(playerPlaying).isSuspended());

    }

    @Test
    public void checkSwapDieWithDieFromDiceBag_ParamsAreRandoms_CheckNewIdIsDifferentFromOldOne(){
        int oldId = model.getTable().getDiceArena().getArena().get(0);

        int newId = model.swapDieWithDieFromDiceBag(0, 1);

        assertNotEquals(oldId, newId);

    }

    @Test
    public void checkSetSuspended_ParamaAsFalse_CheckHeIsntSuspendedAnymore(){

        model.getTable().getPlayers(1).setSuspended(true);

        model.setPlayerSuspended(1, false);

        assertEquals(false, model.getTable().getPlayers(1).isSuspended());

    }

}
