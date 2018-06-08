package it.polimi.se2018.view.console;

import it.polimi.se2018.model.events.*;

import java.util.ArrayList;

public class ViewClientConsoleGame extends ViewClientConsolePrint {

    int idClient;

    private ArrayList<String> idPlayers = new ArrayList<String>();

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
    public void update(ModelChangedMessage message) {

        if (message instanceof ModelChangedMessagePatternCard){
                idPlayers.add(((ModelChangedMessagePatternCard) message).getIdPlayer());
                patternCards.add((ModelChangedMessagePatternCard) message);
        }
        else if (message instanceof ModelChangedMessageDiceOnPatternCard){
            int i = 0;
            if (!idPlayers.contains(((ModelChangedMessageDiceOnPatternCard) message).getIdPlayer())) {
                idPlayers.add(((ModelChangedMessageDiceOnPatternCard) message).getIdPlayer());
                diceOnPatternCards.add((ModelChangedMessageDiceOnPatternCard) message);
            }  else {
                i = idPlayers.indexOf(((ModelChangedMessageDiceOnPatternCard) message).getIdPlayer());
                diceOnPatternCards.add(i, (ModelChangedMessageDiceOnPatternCard) message);
            }
        }
        else if(message instanceof ModelChangedMessagePrivateObjective) {
            if (((ModelChangedMessagePrivateObjective) message).getIdPlayer().equals(Integer.toString(idClient)))
                privateObjective = ((ModelChangedMessagePrivateObjective) message);
        }
        else if(message instanceof ModelChangedMessagePublicObjective)
            publicObjectives.add((ModelChangedMessagePublicObjective)message);
        else if(message instanceof ModelChangedMessageToolCard)
            toolCards.add((ModelChangedMessageToolCard)message);
        else if(message instanceof ModelChangedMessageDiceArena)
            diceArena = ((ModelChangedMessageDiceArena)message);
        else if(message instanceof ModelChangedMessageRound) {
            roundTrack.add((ModelChangedMessageRound) message);
        }


    }

    @Override
    public void print() {

        int myPatternCardId = -1;
        for (int i = 0; i < patternCards.size(); i++)
            if(!(Integer.toString(idClient).equals(idPlayers.get(i))))
                printPatternCard(idPlayers.get(i), patternCards.get(i), diceOnPatternCards.get(i));
            else
                myPatternCardId = i;

        System.out.println("\nYour PatternCard");
        printPatternCard(idPlayers.get(myPatternCardId), patternCards.get(myPatternCardId), diceOnPatternCards.get(myPatternCardId));


        printPrivateObjective(privateObjective);

        for (int i = 0; i < roundTrack.size(); i++)
            printRoundTrack(roundTrack.get(i));

        printDiceArena(diceArena);

        for (int i = 0; i < 3; i++)
            printPublicObjective(publicObjectives.get(i));

        for (int i = 0; i < 3; i++)
            printToolCards(toolCards.get(i));

        System.out.println("\n\n/help: get List of moves");
    }

}
