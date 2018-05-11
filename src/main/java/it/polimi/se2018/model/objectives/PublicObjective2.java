package it.polimi.se2018.model.objectives;

import it.polimi.se2018.model.PatternCard;
import it.polimi.se2018.model.container.DiceContainerUnsupportedIdException;
import it.polimi.se2018.model.container.Die;

public class PublicObjective2 extends PublicObjective {

    public PublicObjective2() {
        this.name = "Colori diversi - Colonna";
        this.description = "Colonne senza colori ripetuti";
        this.id = 2;
    }

    @Override
    /*TODO: tests. How can I solve twarnings? */
    public int calculateScore(PatternCard patternCard) {
        int result = 0;
        boolean controllo = true;

        /* Controllo la condizione per tutte le righe della PatternCard */
        for (int i = 0; i < 5; i++) {

            /* Comincio controllando che non ci siano celle vuote nella riga che sto considerando */
            for (int j = 0; j < 4; j++) {
                if (patternCard.getPatternCardCell(i, j).isEmpty()) {
                    controllo = false;
                    break;
                }
            }

            if (controllo) {
                /* Controllo la condizione per tutte le celle di una riga della PatternCard */
                for (int j = 0; j < 4; j++) {
                    Die d = null;
                    try {
                        d = diceContainer.getDie(patternCard.getPatternCardCell(i, j).getRolledDieId());
                    } catch (DiceContainerUnsupportedIdException e) {
                        e.printStackTrace();
                    }
                    /* Controllo il colore del dado con ogni cella */
                    for (int m = 1; m + j < 4; m++) {
                        Die t = null;
                        try {
                            t = diceContainer.getDie(patternCard.getPatternCardCell(i, j + m).getRolledDieId());
                        } catch (DiceContainerUnsupportedIdException e) {
                            e.printStackTrace();
                        }
                        /* Se i due dadi hanno colori diveri allora non serve che vada avanti con il confronto */
                        if (d.getColor() == t.getColor()) {
                            controllo = false;
                            break;
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
