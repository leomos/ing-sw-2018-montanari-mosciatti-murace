package it.polimi.se2018.model.objectives;

import it.polimi.se2018.model.PatternCard;
import it.polimi.se2018.model.container.DiceContainer;
import it.polimi.se2018.model.container.DiceContainerUnsupportedIdException;
import it.polimi.se2018.model.container.Die;
import it.polimi.se2018.model.container.DieColor;

import java.util.ArrayList;

public class PublicObjective2 extends PublicObjective {

    public PublicObjective2(DiceContainer diceContainer) {
        super(diceContainer);
        this.name = "Colori diversi - Colonna";
        this.description = "Colonne senza colori ripetuti";
        this.id = 2;
    }

    @Override
    /*TODO: tests*/
    public int calculateScore(PatternCard patternCard) {
        /* Genero un ArrayList che contiene la lista dei colori presenti in ogni riga */
        ArrayList<DieColor> riga = new ArrayList<>();
        int result = 0;

        // Controllo la condizione per tutte le righe della PatternCard
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 4; j++) {
                // Comincio controllando che la cella che sto esaminando non sia vuota
                if (patternCard.getPatternCardCell(i, j).isEmpty())
                    break;
                try {
                    Die d = diceContainer.getDie(patternCard.getPatternCardCell(i, j).getRolledDieId());
                    // Se il colore del dado è già presente nell'ArrayList, allora esco, altrimenti aggiungo il suo colore
                    if (riga.indexOf(d.getColor()) != -1)
                        break;
                    riga.add(d.getColor());
                } catch (DiceContainerUnsupportedIdException e) {
                    e.printStackTrace();
                }
            }
            //Se, alla fine, ho un ArrayList con lunghezza 5 allora ho 5 colori diversi e quindi aumento il punteggio
            if (riga.size()==4)
                result = result + 5;
        }
        return result;
    }
}
