package it.polimi.se2018.model.toolcards;

import it.polimi.se2018.model.container.DiceContainer;
import it.polimi.se2018.utils.Database;
import org.junit.*;
import static org.junit.Assert.*;

public class TestToolCardContainer {

    private Database database;
    private DiceContainer diceContainer;
    private ToolCardContainer toolCardContainer;

    @Before
    public void setUp() {
        diceContainer = new DiceContainer();
        database = new Database(diceContainer);
        toolCardContainer = new ToolCardContainer(diceContainer);
    }

    @After
    public void tearDown() {
        diceContainer = null;
        toolCardContainer = null;
        diceContainer = null;
    }

    @Test
    public void setToolCardInPlay_ControlName() {
        toolCardContainer.setToolCardInPlay(0);
            assertEquals("Pinza Sgrossatrice", toolCardContainer.getToolCardInPlay().get(0).getName());
    }

    @Test
    public void setToolCardInPlay_ControlDescription() {
        toolCardContainer.setToolCardInPlay(0);
            assertEquals("Dopo aver scelto un dado, aumenta o diminuisci il valore del dado scelto di 1. Non puoi cambiare un 6 in 1 o un 1 in 6.", toolCardContainer.getToolCardInPlay().get(0).getDescription());

    }

    @Test (expected = java.lang.Exception.class)
    public void setToolCardInPlay_ExceptionThrown() throws ToolCardNotInPlayException {
        toolCardContainer.setToolCardInPlay(0);
        toolCardContainer.getToolCardInPlay(2).getDescription();
    }
}
