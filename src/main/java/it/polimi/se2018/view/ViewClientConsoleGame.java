package it.polimi.se2018.view;

import it.polimi.se2018.model.GamePhase;
import it.polimi.se2018.model.container.DiceContainerUnsupportedIdException;
import it.polimi.se2018.model.events.*;
import it.polimi.se2018.model.toolcards.ToolCardNotInPlayException;

import java.util.ArrayList;

public class ViewClientConsoleGame {

    private ArrayList<ModelChangedMessagePatternCard> patternCards = new ArrayList<ModelChangedMessagePatternCard>();

    private ArrayList<ModelChangedMessageDiceOnPatternCard> diceOnPatternCards = new ArrayList<ModelChangedMessageDiceOnPatternCard>();

    private ArrayList<ModelChangedMessagePublicObjective> publicObjectives = new ArrayList<ModelChangedMessagePublicObjective>();

    private ArrayList<ModelChangedMessageToolCard> toolCards = new ArrayList<ModelChangedMessageToolCard>();

    private ModelChangedMessageDiceArena diceArena;

    private ModelChangedMessagePrivateObjective privateObjective;

    private ArrayList<ModelChangedMessageRound> roundTrack = new ArrayList<ModelChangedMessageRound>();

    public void print() {

        for (int i = 0; i < patternCards.size(); i++)
            printPatternCards(patternCards.get(i));

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
