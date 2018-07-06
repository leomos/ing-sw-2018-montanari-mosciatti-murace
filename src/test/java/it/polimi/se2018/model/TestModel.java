package it.polimi.se2018.model;

import it.polimi.se2018.model.container.DiceContainerUnsupportedIdException;
import it.polimi.se2018.model.container.DieRolledValueOutOfBoundException;
import it.polimi.se2018.model.events.PlayerMessageEndTurn;
import it.polimi.se2018.model.patternCard.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class TestModel {

    HashMap<Integer, String> HM = new HashMap<>();

    Model model;

    @Before
    public void setup(){
        HM.put(1, "Mat");
        HM.put(2, "Leo");
        HM.put(3, "Ale");
        model = new Model(HM, 1000);
        model.initSetup();
        assertEquals(GamePhase.SETUPPHASE, model.getGamePhase());
        for(int i = 1; i < 4; i++) {
            model.setChosenPatternCard(model.getTable().getPlayers(i).getPatternCards().get(0).getId(), i);
        }
    }

    @Test
    public void checkInitSetupAndInitGame_NoParametersNecessary_AllPlayersShouldHavePatternCard(){
        assertEquals(model.currentPlayerPlaying(), model.getTable().getRoundTrack().getCurrentRound().getIdPlayerPlaying());
    }

    @Test
    public void checkEndTurn_NoParametersNecessary_PlayerPlayingShouldBeTwo(){
        assertEquals(GamePhase.GAMEPHASE, model.getGamePhase());
        assertEquals(model.currentPlayerPlaying(), model.getTable().getRoundTrack().getCurrentRound().getIdPlayerPlaying());
        model.endTurn(new PlayerMessageEndTurn(1));
        assertEquals(2, model.currentPlayerPlaying());

    }

    @Test
    public void checkEndTurn10times_NoParametersNecessary_GamePhaseShouldBeENDGAMEPHASE(){

        model.initSetup();
        for(int i = 1; i < 4; i++) {
            model.setChosenPatternCard(model.getTable().getPlayers(i).getPatternCards().get(0).getId(), i);
        }
        assertEquals(model.currentPlayerPlaying(), model.getTable().getRoundTrack().getCurrentRound().getIdPlayerPlaying());
        assertEquals(2, model.currentPlayerPlaying());

        for(int i = 0; i < 60; i++)
            model.endTurn(new PlayerMessageEndTurn(model.currentPlayerPlaying()));


        assertEquals(GamePhase.ENDGAMEPHASE, model.getGamePhase());

    }

    @Test
    public void checkToolCard_ParamAsPlayer1_ExpectResult(){

        int toolCardId = model.getTable().getToolCardContainer().getToolCardInPlay().get(0).getToolCardId();

        assertEquals(true, model.checkToolCard(1, toolCardId));
    }

    @Test
    public void checkToolCard_Player1HasNotEnoughTokens_ExpectResult(){
        int toolCardId = model.getTable().getToolCardContainer().getToolCardInPlay().get(0).getToolCardId();
        model.getTable().getPlayers(1).setTokens(0);

        assertEquals(false, model.checkToolCard(1, toolCardId));
    }

    @Test
    public void checkIncrementOrDecrementDieValue_ParamAsIncrementFirstDieBy1_ResultShouldBeTheSameIfTheDieValueIs6() throws DiceContainerUnsupportedIdException {
        int initialValue = model.getTable().getDiceContainer().getDie(model.getTable().getDiceArena().getArena().get(0)).getRolledValue();
        int initialCost = model.getTable().getToolCardContainer().getToolCard(0).cost();
        model.incrementOrDecrementDieValue(1, 0, 1, 1);
        int finalValue = model.getTable().getDiceContainer().getDie(model.getTable().getDiceArena().getArena().get(0)).getRolledValue();
        int finalCost = model.getTable().getToolCardContainer().getToolCard(0).cost();

        if(initialValue == 6)
            assertEquals(initialValue, finalValue);
        else
            assertEquals(initialValue + 1, finalValue);

        assertEquals(initialCost + 1, finalCost);

    }

    @Test
    public void checkMoveDieInsidePatternCard_ParamAs11to10_11shouldBeEmptyAnd10ShouldBeNotEmpty() throws DiceContainerUnsupportedIdException, DieRolledValueOutOfBoundException, PatternCardNoAdjacentDieException, PatternCardDidNotRespectFirstMoveException, PatternCardNotRespectingCellConstraintException, PatternCardNotRespectingNearbyDieExpection, PatternCardCellIsOccupiedException, PatternCardAdjacentDieException {
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
    public void checkTwoDiceInsidePatternCard_ParamAs11to10and22to11_22ShouldBeEmpty(){

    }

}
