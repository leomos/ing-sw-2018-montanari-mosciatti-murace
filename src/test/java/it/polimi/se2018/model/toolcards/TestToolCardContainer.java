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
        toolCardContainer = new ToolCardContainer(database);
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
            assertEquals("Grozing Pliers", toolCardContainer.getToolCardInPlay().get(0).getName());
    }

    @Test
    public void setToolCardInPlay_ControlDescription() {
        toolCardContainer.setToolCardInPlay(0);
            assertEquals("After drafting, increase or decrease the value of the drafted die by 1. 1 may not change to 6, or 6 to 1.", toolCardContainer.getToolCardInPlay().get(0).getDescription());

    }

}
