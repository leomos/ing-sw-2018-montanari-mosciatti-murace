package it.polimi.se2018.model;

import it.polimi.se2018.model.container.DiceContainer;
import it.polimi.se2018.model.container.DiceContainerUnsupportedIdException;
import it.polimi.se2018.model.container.DieRolledStateNotChangedException;
import it.polimi.se2018.model.container.DieRolledValueOutOfBoundException;
import it.polimi.se2018.model.objectives.PrivateObjective;
import it.polimi.se2018.model.patternCard.PatternCard;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TestTable {
    HashMap<Integer, String> HM = new HashMap<>();
    private Table table;
    private DiceContainer diceContainer;
    private PatternCard patternCard;

    @Before
    public void setup(){
        HM.put(0, "Mat");
        HM.put(1, "Leo");
        HM.put(2, "Ale");
        table = new Table(HM);
    }

    @After
    public void teardown(){
        HM = null;
        table = null;
    }

    @Test
    public void checkTableConstructor_ParamAsThreePlayers_ExpectedNoNull(){
        assertNotEquals(null, table.getDiceContainer());
        assertNotEquals(null, table.getToolCardContainer());
        assertNotEquals(null, table.getDatabase());
        assertNotEquals(null, table.getDiceArena());
        assertNotEquals(null, table.getPlayers(2));
        assertNotEquals(null, table.getRoundTrack());
        assertNotEquals(null, table.getPatternCards(5));
        assertNotEquals(null, table.getPublicObjective(2));
    }

    @Test
    public void checkPlayerConstruct_FirstParamAs1SecondParamAsLeo_IdShouldBe1NameShouldBeLeo(){
        assertEquals(1, table.getPlayers(1).getId());
        assertEquals("Leo", table.getPlayers(1).getName());
    }

    @Test
    public void checkPlayerPrivateObjectiveIsDifferent_ParamAreSetRandomly_ExpectedReturnNotToBeEquals(){
        assertNotEquals(table.getPlayers(0).getPrivateObjective().getId(), table.getPlayers(1).getPrivateObjective().getId());
        assertNotEquals(table.getPlayers(0).getPrivateObjective().getId(), table.getPlayers(2).getPrivateObjective().getId());
        assertNotEquals(table.getPlayers(1).getPrivateObjective().getId(), table.getPlayers(2).getPrivateObjective().getId());
    }

    @Test
    public void checkPlayerPatterCardsAreDifferent_ParamAreSetRandomly_ExpectedReturnNotToBeEquals(){
        for(int i = 0; i < 4; i++)
            for(int j = 0; j < 4; j++)
                assertNotEquals(table.getPlayers(0).getPatternCards().get(i), table.getPlayers(1).getPatternCards().get(j));

        for(int i = 0; i < 4; i++)
            for(int j = 0; j < 4; j++)
                assertNotEquals(table.getPlayers(1).getPatternCards().get(i), table.getPlayers(2).getPatternCards().get(j));

        for(int i = 0; i < 4; i++)
            for(int j = 0; j < 4; j++)
                assertNotEquals(table.getPlayers(0).getPatternCards().get(i), table.getPlayers(2).getPatternCards().get(j));
    }


    @Test
    public void checkPublicObjectiveAreDifferentFromEachOther_ParamAreGeneratedRandomly_ExpectedReturnsNotToBeEquals(){
        assertNotEquals(table.getPublicObjective(0), table.getPublicObjective(1));
        assertNotEquals(table.getPublicObjective(0), table.getPublicObjective(2));
        assertNotEquals(table.getPublicObjective(2), table.getPublicObjective(1));
    }

    @Test
    public void checkToolCardInUseAreDifferentFromEachOther_ParamAreGeneratedRandomly_ExpectedReturnsNotToBeEquals() {
        assertNotEquals(table.getToolCardContainer().getToolCardInPlay().get(0), table.getToolCardContainer().getToolCardInPlay().get(1));
        assertNotEquals(table.getToolCardContainer().getToolCardInPlay().get(0), table.getToolCardContainer().getToolCardInPlay().get(2));
        assertNotEquals(table.getToolCardContainer().getToolCardInPlay().get(2), table.getToolCardContainer().getToolCardInPlay().get(1));
    }

    @Test
    public void checkForcePublicObjectiveIntoPlay_ParamAs3and5and8_ExpectedReturnsToBe4and6and9(){
        ArrayList<Integer> listOfPublicObjectiveToForce = new ArrayList<>();
        listOfPublicObjectiveToForce.add(3);
        listOfPublicObjectiveToForce.add(5);
        listOfPublicObjectiveToForce.add(8);
        table.forcePublicObjectiveIntoPlay(listOfPublicObjectiveToForce, diceContainer);

        assertEquals(4, table.getPublicObjective(0).getId());
        assertEquals(6, table.getPublicObjective(1).getId());
        assertEquals(9, table.getPublicObjective(2).getId());
    }

    @Test
    public void test() throws DiceContainerUnsupportedIdException, DieRolledValueOutOfBoundException, DieRolledStateNotChangedException {

        diceContainer = new DiceContainer();
        patternCard = new PatternCard(diceContainer, 13,"FractalDrop",3,"040y6r020000rp1by000");

        //RED
        diceContainer.getDie(0).setRolledValue(2);
        diceContainer.getDie(1).setRolledValue(3);
        diceContainer.getDie(2).setRolledValue(3);
        //GIALLI
        diceContainer.getDie(18).setRolledValue(5);
        diceContainer.getDie(19).setRolledValue(2);
        diceContainer.getDie(20).setRolledValue(3);
        //VERDI
        diceContainer.getDie(36).setRolledValue(4);
        diceContainer.getDie(37).setRolledValue(1);
        diceContainer.getDie(38).setRolledValue(6);
        diceContainer.getDie(39).setRolledValue(4);
        diceContainer.getDie(40).setRolledValue(4);
        //AZZURRI
        diceContainer.getDie(54).setRolledValue(1);
        diceContainer.getDie(55).setRolledValue(4);
        diceContainer.getDie(56).setRolledValue(2);
        //VIOLA
        diceContainer.getDie(72).setRolledValue(6);
        diceContainer.getDie(73).setRolledValue(5);
        diceContainer.getDie(74).setRolledValue(6);


        this.patternCard.getPatternCardCell(1, 0).setRolledDieId(36, false, false);
        this.patternCard.getPatternCardCell(2, 0).setRolledDieId(0, false, false);
        this.patternCard.getPatternCardCell(3, 0).setRolledDieId(18, false, false);
        this.patternCard.getPatternCardCell(4, 0).setRolledDieId(72, false, false);

        this.patternCard.getPatternCardCell(0, 1).setRolledDieId(1, false, false);
        this.patternCard.getPatternCardCell(1, 1).setRolledDieId(54, false, false);
        this.patternCard.getPatternCardCell(3,1).setRolledDieId(37, false, false);
        this.patternCard.getPatternCardCell(4,1).setRolledDieId(19, false, false);

        this.patternCard.getPatternCardCell(0,2).setRolledDieId(73, false, false);
        this.patternCard.getPatternCardCell(1,2).setRolledDieId(38, false, false);
        this.patternCard.getPatternCardCell(3,2).setRolledDieId(74, false, false);
        this.patternCard.getPatternCardCell(4,2).setRolledDieId(2, true, false);

        this.patternCard.getPatternCardCell(0,3).setRolledDieId(55, false, false);
        this.patternCard.getPatternCardCell(1,3).setRolledDieId(20, false, false);
        this.patternCard.getPatternCardCell(2,3).setRolledDieId(39, false, false);
        this.patternCard.getPatternCardCell(3,3).setRolledDieId(56, false, false);
        this.patternCard.getPatternCardCell(4,3).setRolledDieId(40, false, false);

        table.getPlayers(0).setPrivateObjective(new PrivateObjective(diceContainer, 5, "notImportant", "notImportant"));
        table.getPlayers(1).setPrivateObjective(new PrivateObjective(diceContainer, 4, "notImportant", "notImportant"));
        table.getPlayers(2).setPrivateObjective(new PrivateObjective(diceContainer, 3, "notImportant", "notImportant"));

        table.getPlayers(0).setChosenPatternCard(patternCard);
        table.getPlayers(1).setChosenPatternCard(patternCard);
        table.getPlayers(2).setChosenPatternCard(patternCard);

        ArrayList<Integer> listOfPublicObjectiveToForce = new ArrayList<>();
        listOfPublicObjectiveToForce.add(1);
        listOfPublicObjectiveToForce.add(4);
        listOfPublicObjectiveToForce.add(9);
        table.forcePublicObjectiveIntoPlay(listOfPublicObjectiveToForce, diceContainer);

        table.calculateScores();

        ArrayList<Integer> results = new ArrayList<>(table.getScoreboard().getScore().values());
        ArrayList<Integer> expectedValue = new ArrayList<>();
        expectedValue.add(40);
        expectedValue.add(30);
        expectedValue.add(42);

        assertEquals(expectedValue, results);

    }

}
