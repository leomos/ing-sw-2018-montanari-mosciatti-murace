package it.polimi.se2018.model;

import it.polimi.se2018.model.container.DiceContainer;
import it.polimi.se2018.utils.Database;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static it.polimi.se2018.model.container.DieColor.*;


public class TestDatabase {
    private DiceContainer diceContainer;
    private Database database;

    @Before
    public void setup(){
        diceContainer = new DiceContainer();
        database = new Database(diceContainer);
    }

    @After
    public void tearDown(){
        diceContainer = null;
        database = null;
    }

    @Test
    public void loadToolCards_ParamAs4_asd_getToolCardIdShouldBe5(){
        assertEquals(5, database.loadToolCards().get(4).getToolCardId());
    }

    @Test
    public void loadPToolCards_ParamAs3_getDescription(){
        assertEquals("Muovi esattamente due dadi, rispettando tutte le restrizioni di piazzamento", database.loadToolCards().get(3).getDescription());
    }

    @Test
    public void loadPatternCards_ParamAs7_getDifficultyShouldBe7(){
        assertEquals(6, database.loadPatternCard().get(7).getDifficulty());
    }

    @Test
    public void loadPatternCards_ParamAs5_getValueConstraintShouldBe5(){
        assertEquals(5, database.loadPatternCard().get(5).getPatternCardCell(2,2).getValueConstraint());
    }

    @Test
    public void loadPatternCards_ParamAs12_getValueConstraintShouldBe5(){
        assertEquals(BLUE, database.loadPatternCard().get(12).getPatternCardCell(3,0).getColorConstraint());
    }

}
