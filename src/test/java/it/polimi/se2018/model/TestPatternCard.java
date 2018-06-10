package it.polimi.se2018.model;

import it.polimi.se2018.model.container.DiceContainer;
import it.polimi.se2018.model.container.DiceContainerUnsupportedIdException;
import it.polimi.se2018.model.container.DieRolledValueOutOfBoundException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestPatternCard {
    private DiceContainer diceContainer;
    private PatternCard patternCard;

    @Before
    public void setUp() {
        diceContainer = new DiceContainer();
        patternCard = new PatternCard(diceContainer, 0, "Virtus",5,"4025g006g203g405g100");
    }

    @After
    public void tearDown(){
        diceContainer = null;
        patternCard = null;
    }

    @Test
    public void getPatternCardCell_FirstParamAs2SecondParamAs2_getPatternCardCellShouldBeGREEN(){
        assertEquals(4, patternCard.getPatternCardCell(3,2).getValueConstraint());
    }

    @Test
    public void checkDieInAdjacentCells_FirstParamAs3SecondParamAs3_returnShouldBeFalse() throws DiceContainerUnsupportedIdException {
        try {
            diceContainer.getDie(5).setRolledValue(4);
            diceContainer.getDie(50).setRolledValue(4);
        } catch (DieRolledValueOutOfBoundException e) {
            fail();
        }
        patternCard.getPatternCardCell(0,0).setRolledDieId(50, false, false);
        assertEquals(false, patternCard.checkDieInAdjacentCells(3,3));
    }

    @Test
    public void checkDieInAdjacentCells_FirstParamAs1SecondParamAs1_returnShouldBeTrue() throws DiceContainerUnsupportedIdException {
        try {
            diceContainer.getDie(5).setRolledValue(4);
            diceContainer.getDie(50).setRolledValue(4);
        } catch (DieRolledValueOutOfBoundException e) {
            fail();
        }
        patternCard.getPatternCardCell(0,0).setRolledDieId(50, false, false);
        assertEquals(true, patternCard.checkDieInAdjacentCells(1,1));
    }


    @Test
    public void checkProximityCellsValidity_FirstParamAs0SecondParamAs1_returnShouldBeFalse() throws DiceContainerUnsupportedIdException {
        try {
            diceContainer.getDie(5).setRolledValue(4);
            diceContainer.getDie(50).setRolledValue(4);
            diceContainer.getDie(6).setRolledValue(2);
        } catch (DieRolledValueOutOfBoundException e) {
            fail();
        }
        patternCard.getPatternCardCell(0,0).setRolledDieId(5, false, false);
        assertEquals(false, patternCard.checkProximityCellsValidity(50,0,1));
        assertEquals(false, patternCard.checkProximityCellsValidity(6,1,0));
    }

    @Test
    public void checkProximityCellsValidity_FirstParamAs2SecondParamAs2_returnShouldBeTrue() throws DiceContainerUnsupportedIdException {
        try {
            diceContainer.getDie(5).setRolledValue(4);
            diceContainer.getDie(50).setRolledValue(3);
            diceContainer.getDie(22).setRolledValue(2);
        } catch (DieRolledValueOutOfBoundException e) {
            fail();
        }
        patternCard.getPatternCardCell(2,1).setRolledDieId(5, false, false);
        assertEquals(true, patternCard.checkProximityCellsValidity(50,2,2));
        assertEquals(true, patternCard.checkProximityCellsValidity(6,2,2));
    }

    @Test
    public void checkUpdateDiceRepresentation_UsePatternCardVirtus_jasdjasdikasol() throws DiceContainerUnsupportedIdException, DieRolledValueOutOfBoundException {
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
        this.patternCard.getPatternCardCell(3, 1).setRolledDieId(37, false, false);
        this.patternCard.getPatternCardCell(4, 1).setRolledDieId(19, false, false);

        this.patternCard.getPatternCardCell(0, 2).setRolledDieId(73, false, false);
        this.patternCard.getPatternCardCell(1, 2).setRolledDieId(38, false, false);
        this.patternCard.getPatternCardCell(3, 2).setRolledDieId(74, false, false);
        this.patternCard.getPatternCardCell(4, 2).setRolledDieId(2, true, false);

        this.patternCard.getPatternCardCell(0, 3).setRolledDieId(55, false, false);
        this.patternCard.getPatternCardCell(1, 3).setRolledDieId(20, false, false);
        this.patternCard.getPatternCardCell(2, 3).setRolledDieId(39, false, false);
        this.patternCard.getPatternCardCell(3, 3).setRolledDieId(56, false, false);
        this.patternCard.getPatternCardCell(4, 3).setRolledDieId(40, false, false);

        patternCard.updateDiceRepresentation();

        for(int i = 0; i < patternCard.getDiceRepresentation().length(); i+=4){
            if(patternCard.getDiceRepresentation().charAt(i) != '*'){
                int k = Integer.parseInt(patternCard.getDiceRepresentation().substring(i, i + 2));
                int m = Character.getNumericValue(patternCard.getDiceRepresentation().charAt(i+3));
                assertTrue(0 <= k);
                assertTrue(90 > k);
                assertTrue(0 < m);
                assertTrue(7 > m);
            }
        }
    }

    @Test
    public void checkUpdateDiceRepresentation_PatternCardWithNoDice_ExpectedStringOf80Asterisks() throws DiceContainerUnsupportedIdException {
        patternCard.updateDiceRepresentation();
        assertEquals("********************************************************************************",
                        patternCard.getDiceRepresentation());
    }

    @Test
    public void setFirstMove() {
        assertEquals("Virtus", patternCard.getName());
        assertEquals(0, patternCard.getId());
        assertEquals("4025g006g203g405g100", patternCard.getPatternCardRepresentation());
        patternCard.setFirstMove();
        assertFalse(patternCard.isFirstMove());
    }
}