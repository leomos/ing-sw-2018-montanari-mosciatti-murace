package it.polimi.se2018.model;

import it.polimi.se2018.model.container.DiceContainer;
import it.polimi.se2018.utils.Database;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

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
    public void loadToolCards_asd_asd(){
        assertEquals(5, database.loadToolCards().get(4).getToolCardId());
    }

    @Test
    public void loadPatternCards_ads_asd(){
        assertEquals(6, database.loadPatternCard().get(7).getDifficulty());
    }
}
