package it.polimi.se2018.view;

import it.polimi.se2018.model.events.ModelChangedMessageDiceOnPatternCard;
import it.polimi.se2018.model.events.ModelChangedMessagePatternCard;
import it.polimi.se2018.model.events.ModelChangedMessagePrivateObjective;

import java.util.ArrayList;

public class ViewClientConsoleSetup extends ViewClientConsole {

    private ArrayList<ModelChangedMessagePatternCard> patternCards = new ArrayList<ModelChangedMessagePatternCard>();

    private ArrayList<ModelChangedMessageDiceOnPatternCard> diceOnPatternCards = new ArrayList<ModelChangedMessageDiceOnPatternCard>();

    private ModelChangedMessagePrivateObjective privateObjective;

    public void print() {

        for (int i = 0; i < patternCards.size(); i++)
            printPatternCard(patternCards.get(i), diceOnPatternCards.get(i));

        /* private objective? devo usare id view per stampare quello giusto*/

        }

}
