package it.polimi.se2018.view;

import it.polimi.se2018.model.events.ModelChangedMessagePatternCard;
import it.polimi.se2018.model.events.ModelChangedMessagePrivateObjective;

import java.util.ArrayList;

public class ViewClientConsoleSetup {

    private ArrayList<ModelChangedMessagePatternCard> patternCards = new ArrayList<ModelChangedMessagePatternCard>();

    private ModelChangedMessagePrivateObjective privateObjective;

    public void print() {

        for (int i = 0; i < patternCards.size(); i++)
            printPatternCards(patternCards.get(i));

        /* private objective? devo usare id view per stampare quello giusto*/

        }

}
