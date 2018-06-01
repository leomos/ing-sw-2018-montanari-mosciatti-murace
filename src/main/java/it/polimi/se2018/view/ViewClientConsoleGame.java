package it.polimi.se2018.view;

import it.polimi.se2018.model.events.*;

import java.util.ArrayList;

public class ViewClientConsoleGame extends ViewClientConsolePrint {

    private ArrayList<ModelChangedMessagePatternCard> patternCards = new ArrayList<ModelChangedMessagePatternCard>();

    private ArrayList<ModelChangedMessageDiceOnPatternCard> diceOnPatternCards = new ArrayList<ModelChangedMessageDiceOnPatternCard>();

    private ArrayList<ModelChangedMessagePublicObjective> publicObjectives = new ArrayList<ModelChangedMessagePublicObjective>();

    private ArrayList<ModelChangedMessageToolCard> toolCards = new ArrayList<ModelChangedMessageToolCard>();

    private ModelChangedMessageDiceArena diceArena;

    private ModelChangedMessagePrivateObjective privateObjective;

    private ArrayList<ModelChangedMessageRound> roundTrack = new ArrayList<ModelChangedMessageRound>();

    @Override
    public void print() {

        for (int i = 0; i < patternCards.size(); i++)
            printPatternCard(patternCards.get(i), diceOnPatternCards.get(i));

        for (int i = 0; i < 10; i++)
            printRoundTrack(roundTrack.get(i));

            printDiceArena(diceArena);

        for (int i = 0; i < 3; i++)
            printPublicObjective(publicObjectives.get(i));

        for (int i = 0; i < 3; i++)
            printToolCards(toolCards.get(i));

            /* private objective? devo usare view id per stampare quello giusto */
    }

}
