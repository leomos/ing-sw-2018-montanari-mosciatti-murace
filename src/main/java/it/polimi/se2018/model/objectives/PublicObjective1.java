package it.polimi.se2018.model.objectives;

import it.polimi.se2018.model.PatternCard;
import it.polimi.se2018.model.container.DiceContainer;
import it.polimi.se2018.model.container.DiceContainerUnsupportedIdException;
import it.polimi.se2018.model.container.Die;

public class PublicObjective1 extends PublicObjective {

    public PublicObjective1(DiceContainer diceContainer) {
        super(diceContainer);
        this.name = "Colori diversi - Riga";
        this.description = "Righe senza colori ripetuti";
        this.id = 1;
    }

    @Override
    /*TODO: tests */
    public int calculateScore(PatternCard patternCard) {
        int result = 0;
        boolean controllo = true;

        /* Controllo la condizione per tutte le righe della PatternCard */
        for (int i = 0; i < 4; i++) {

            /* Comincio controllando che non ci siano celle vuote nella riga che sto considerando */
            for (int j = 0; j < 5; j++) {
                if (patternCard.getPatternCardCell(i, j).isEmpty()) {
                    controllo = false;
                    break;
                }
            }

            if (controllo) {
                /* Controllo la condizione per tutte le celle di una riga della PatternCard */
                for (int j = 0; j < 5; j++) {
                    Die d = null;
                    try {
                        d = diceContainer.getDie(patternCard.getPatternCardCell(i, j).getRolledDieId());
                    } catch (DiceContainerUnsupportedIdException e) {
                        e.printStackTrace();
                    }
                    /* Controllo il colore del dado con ogni cella */
                    for (int m = 1; m + j < 5; m++) {
                        Die t = null;
                        try {
                            t = diceContainer.getDie(patternCard.getPatternCardCell(i, j + m).getRolledDieId());
                            /* Se i due dadi hanno colori diveri allora non serve che vada avanti con il confronto */
                            if (d.getColor() == t.getColor()) {
                                controllo = false;
                                break;
                            }
                        } catch (DiceContainerUnsupportedIdException e) {
                            e.printStackTrace();
                        }
                    }

                    /* Se ho trovato anche solo un mismatch allora non serve che vada avanti con il controllo della riga */
                    if (!controllo)
                        break;

                    result = result + 6;
                }
            }
        }
        return result;
    }
}
