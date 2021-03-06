package it.polimi.se2018.model.objectives;

import it.polimi.se2018.model.patternCard.PatternCard;
import it.polimi.se2018.model.container.DiceContainer;
import it.polimi.se2018.model.container.DieRolledValueOutOfBoundException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestPublicObjective1 {
    private DiceContainer diceContainer;
    private PatternCard patternCard;
    private PublicObjective1 publicObjective1;

    @Before
    public void setUp(){
        diceContainer = new DiceContainer();
        patternCard = new PatternCard(diceContainer, 13,"FractalDrop",3,"040y6r020000rp1by000");
        publicObjective1 = new PublicObjective1(diceContainer);
    }

    @After
    public void tearDown() {
        diceContainer = null;
        patternCard = null;
        publicObjective1 = null;
    }

    @Test
    public void getter() {
        assertEquals("Rows with no repeated colors", publicObjective1.getDescription());
        assertEquals("Row Color Variety", publicObjective1.getName());
    }

    @Test
    public void checkCalculateScore_ParamAsInstructionExample_ReturnShouldBe10() throws DieRolledValueOutOfBoundException {
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
        diceContainer.getDie(57).setRolledValue(1); //die added compared to the instruction's example
        //VIOLA
        diceContainer.getDie(72).setRolledValue(6);
        diceContainer.getDie(73).setRolledValue(5);
        diceContainer.getDie(74).setRolledValue(6);
        diceContainer.getDie(75).setRolledValue(2); //die added compared to the instruction's example
        //technically you shouldn't be able to insert this
        //last die but for the sake of this example we ignored
        //some positioning constraint

        this.patternCard.getPatternCardCell(0,0).setRolledDieId(57, false, false);
        this.patternCard.getPatternCardCell(1, 0).setRolledDieId(36, false, false);
        this.patternCard.getPatternCardCell(2, 0).setRolledDieId(0, false, false);
        this.patternCard.getPatternCardCell(3, 0).setRolledDieId(18, false, false);
        this.patternCard.getPatternCardCell(4, 0).setRolledDieId(72, false, false);

        this.patternCard.getPatternCardCell(0, 1).setRolledDieId(1, false, false);
        this.patternCard.getPatternCardCell(1, 1).setRolledDieId(54, false, false);
        this.patternCard.getPatternCardCell(2,1).setRolledDieId(75, false, false);
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

        assertEquals(12, publicObjective1.calculateScore(patternCard));
    }
}