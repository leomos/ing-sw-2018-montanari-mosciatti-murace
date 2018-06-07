package it.polimi.se2018.view.console;

import it.polimi.se2018.model.events.*;

import java.util.ArrayList;

public class ViewClientConsoleGame extends ViewClientConsolePrint {

    int idClient;

    private ArrayList<ModelChangedMessagePatternCard> patternCards = new ArrayList<ModelChangedMessagePatternCard>();

    private ArrayList<ModelChangedMessageDiceOnPatternCard> diceOnPatternCards = new ArrayList<ModelChangedMessageDiceOnPatternCard>();

    private ArrayList<ModelChangedMessagePublicObjective> publicObjectives = new ArrayList<ModelChangedMessagePublicObjective>();

    private ArrayList<ModelChangedMessageToolCard> toolCards = new ArrayList<ModelChangedMessageToolCard>();

    private ModelChangedMessageDiceArena diceArena;

    private ModelChangedMessagePrivateObjective privateObjective;

    private ArrayList<ModelChangedMessageRound> roundTrack = new ArrayList<ModelChangedMessageRound>();

    public ViewClientConsoleGame(int idClient){
        this.idClient = idClient;
    }


    //VA BENE SOLO PER IL PRIMO GIRO//
    @Override
    public void update(ModelChangedMessage message){
        if(message instanceof ModelChangedMessagePatternCard)
            patternCards.add((ModelChangedMessagePatternCard)message);
        else if(message instanceof ModelChangedMessagePrivateObjective) {
            if (((ModelChangedMessagePrivateObjective) message).getIdPlayer().equals(Integer.toString(idClient)))
                privateObjective = ((ModelChangedMessagePrivateObjective) message);
        }
        else if(message instanceof ModelChangedMessageDiceOnPatternCard)
            diceOnPatternCards.add((ModelChangedMessageDiceOnPatternCard)message);
        else if(message instanceof ModelChangedMessagePublicObjective)
            publicObjectives.add((ModelChangedMessagePublicObjective)message);
        else if(message instanceof ModelChangedMessageToolCard)
            toolCards.add((ModelChangedMessageToolCard)message);
        else if(message instanceof ModelChangedMessageDiceArena)
            diceArena = ((ModelChangedMessageDiceArena)message);


    }

    @Override
    public void print() {

        for (int i = 0; i < patternCards.size(); i++)
            printPatternCard(patternCards.get(i), diceOnPatternCards.get(i));

        printPrivateObjective(privateObjective);

        //for (int i = 0; i < 10; i++)
            //printRoundTrack(roundTrack.get(i));

        printDiceArena(diceArena);

        for (int i = 0; i < 3; i++)
            printPublicObjective(publicObjectives.get(i));

        for (int i = 0; i < 3; i++)
            printToolCards(toolCards.get(i));
    }

}
