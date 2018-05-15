package it.polimi.se2018.model.objectives;

import it.polimi.se2018.model.PatternCard;
import it.polimi.se2018.model.container.DiceContainer;
import it.polimi.se2018.model.container.DiceContainerUnsupportedIdException;
import it.polimi.se2018.model.container.DieRolledValueOutOfBoundException;
import org.junit.*;

public class TestPrivateObjective {
    private DiceContainer diceContainer = new DiceContainer();
    private PatternCard patternCard = new PatternCard(diceContainer, "13,FractalDrop,3,040y6r020000rp1by000");

    @After
    public void setUp() throws DiceContainerUnsupportedIdException, DieRolledValueOutOfBoundException {
        diceContainer = null;
        //ROSSI
        diceContainer.getDie(0).setRolledValue(2);
        diceContainer.getDie(1).setRolledValue(3);
        //GIALLI
        diceContainer.getDie(18).setRolledValue(5);
        diceContainer.getDie(19).setRolledValue(2);
        //VERDI
        diceContainer.getDie(36).setRolledValue(4);
        diceContainer.getDie(37).setRolledValue(1);
        //AZZURRI
        diceContainer.getDie(54).setRolledValue(1);
        //VIOLA
        diceContainer.getDie(72).setRolledValue(6);

        diceContainer.getDie(88).setRolledValue(5);
        diceContainer.getDie(69).setRolledValue(6);
        diceContainer.getDie(89).setRolledValue(6);
        diceContainer.getDie(5).setRolledValue(4);
        diceContainer.getDie(17).setRolledValue(3);
        diceContainer.getDie(70).setRolledValue(4);
        diceContainer.getDie(35).setRolledValue(3);
        diceContainer.getDie(51).setRolledValue(4);
        diceContainer.getDie(71).setRolledValue(2);
        diceContainer.getDie(52).setRolledValue(4);


    }

    @Test
    public void calculateScore() throws DiceContainerUnsupportedIdException {
        patternCard.getPatternCardCell(1, 0).setRolledDieId(36, false, false);
        patternCard.getPatternCardCell(2, 0).setRolledDieId(0, false, false);
        patternCard.getPatternCardCell(3, 0).setRolledDieId(18, false, false);
        patternCard.getPatternCardCell(4, 0).setRolledDieId(72, false, false);
        patternCard.getPatternCardCell(0, 1).setRolledDieId(1, false, false);
        patternCard.getPatternCardCell(1, 1).setRolledDieId(54, false, false);
        patternCard.getPatternCardCell(3,1).setRolledDieId(37, false, false);
        patternCard.getPatternCardCell(4,1).setRolledDieId(19, false, false);

        patternCard.getPatternCardCell(0,2).setRolledDieId(69, false, false);
        patternCard.getPatternCardCell(1,2).setRolledDieId(89, false, false);
        patternCard.getPatternCardCell(3,2).setRolledDieId(5, false, false);
        patternCard.getPatternCardCell(4,2).setRolledDieId(17, false, false);

        patternCard.getPatternCardCell(0,3).setRolledDieId(70, false, false);
        patternCard.getPatternCardCell(1,3).setRolledDieId(35, false, false);
        patternCard.getPatternCardCell(2,3).setRolledDieId(51, false, false);
        patternCard.getPatternCardCell(3,3).setRolledDieId(71, false, false);
        patternCard.getPatternCardCell(4,3).setRolledDieId(52, false, false);
    }
}
