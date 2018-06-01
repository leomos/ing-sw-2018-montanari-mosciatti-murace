package it.polimi.se2018.model;

import it.polimi.se2018.model.toolcards.ToolCardNotInPlayException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TestTable {
    HashMap<Integer, String> HM = new HashMap<>();
    private Table table;

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
    public void checkToolCardInUseAreDifferentFromEachOther_ParamAreGeneratedRandomly_ExpectedReturnsNotToBeEquals() throws ToolCardNotInPlayException {
        assertNotEquals(table.getToolCardContainer().getToolCardInPlay().get(0), table.getToolCardContainer().getToolCardInPlay().get(1));
        assertNotEquals(table.getToolCardContainer().getToolCardInPlay().get(0), table.getToolCardContainer().getToolCardInPlay().get(2));
        assertNotEquals(table.getToolCardContainer().getToolCardInPlay().get(2), table.getToolCardContainer().getToolCardInPlay().get(1));
    }

}
