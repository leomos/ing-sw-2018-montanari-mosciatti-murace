package it.polimi.se2018.model.toolcards;

import org.junit.*;
import static org.junit.Assert.*;

public class TestToolCard {

    private ToolCard toolCard;

    @Before
    public void setUp() {
        toolCard = new ToolCard(4, "Lathekin", "Muovi esattamente due dadi, rispettando tutte le restrizioni di piazzamento");
    }

    @After
    public void tearDown() {
        toolCard = null;
        assertNull(toolCard);
    }

    @Test
    public void isInGame_isInGameShouldBeFalse() {
        assertFalse(toolCard.isInGame());
    }

    @Test
    public void setInGame_TrueAsParam_isInGameShouldBeTrue() {
        toolCard.setInGame(true);
        assertTrue(toolCard.isInGame());
    }

    @Test
    public void setUsed_isUsedAtLeastOnceShouldBeTrue() {
        toolCard.setUsed();
        assertTrue(toolCard.isUsedAtLeastOnce());
    }

    @Test
    public void cost_ShouldReturn1() {
        assertEquals(1, toolCard.cost());
    }

    @Test
    public void cost_ShouldReturn2() {
        toolCard.setUsed();
        assertEquals(2, toolCard.cost());
    }
}
