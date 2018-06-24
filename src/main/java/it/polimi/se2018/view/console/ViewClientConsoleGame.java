package it.polimi.se2018.view.console;

import it.polimi.se2018.model.events.*;

import java.util.ArrayList;

public class ViewClientConsoleGame extends ViewClientConsolePrint {

    private int idClient;

    private ArrayList<String> idPlayers = new ArrayList<String>();

    private ArrayList<ModelChangedMessagePatternCard> patternCards = new ArrayList<ModelChangedMessagePatternCard>();

    private ArrayList<ModelChangedMessageDiceOnPatternCard> diceOnPatternCards = new ArrayList<ModelChangedMessageDiceOnPatternCard>();

    private ArrayList<ModelChangedMessagePublicObjective> publicObjectives = new ArrayList<ModelChangedMessagePublicObjective>();

    private ArrayList<ModelChangedMessageToolCard> toolCards = new ArrayList<ModelChangedMessageToolCard>();

    private ModelChangedMessageDiceArena diceArena;

    private ModelChangedMessagePrivateObjective privateObjective;

    private ModelChangedMessageTokensLeft tokensLeft;

    private ArrayList<ModelChangedMessageRound> roundTrack = new ArrayList<ModelChangedMessageRound>(10);

    public ViewClientConsoleGame(int idClient){
        this.idClient = idClient;
    }


    @Override
    public void update(ModelChangedMessage message) {

        if (message instanceof ModelChangedMessagePatternCard){
                idPlayers.add(((ModelChangedMessagePatternCard) message).getIdPlayer());
                patternCards.add((ModelChangedMessagePatternCard) message);
                diceOnPatternCards.add(null);
        }
        else if (message instanceof ModelChangedMessageDiceOnPatternCard){
            int i = idPlayers.indexOf(((ModelChangedMessageDiceOnPatternCard) message).getIdPlayer());
            diceOnPatternCards.remove(i);
            diceOnPatternCards.add(i, (ModelChangedMessageDiceOnPatternCard) message);
        }
        else if(message instanceof ModelChangedMessagePrivateObjective) {
            if (((ModelChangedMessagePrivateObjective) message).getIdPlayer().equals(Integer.toString(idClient)))
                privateObjective = ((ModelChangedMessagePrivateObjective) message);
        }
        else if(message instanceof ModelChangedMessagePublicObjective)
            publicObjectives.add((ModelChangedMessagePublicObjective)message);
        else if(message instanceof ModelChangedMessageToolCard) {
            if(toolCards.size() == 3) {
                for(int i = 0; i < 3; i++)
                    if(toolCards.get(i).getIdToolCard().equals(((ModelChangedMessageToolCard) message).getIdToolCard())) {
                        toolCards.remove(i);
                        toolCards.add(i, (ModelChangedMessageToolCard) message);
                    }
            } else {
                toolCards.add((ModelChangedMessageToolCard) message);
            }
        }
        else if(message instanceof ModelChangedMessageDiceArena)
            diceArena = ((ModelChangedMessageDiceArena)message);
        else if(message instanceof ModelChangedMessageRound) {
            roundTrack.add(Integer.parseInt(((ModelChangedMessageRound) message).getIdRound()), (ModelChangedMessageRound) message);
        }
        else if(message instanceof ModelChangedMessageTokensLeft)
            if(((ModelChangedMessageTokensLeft) message).getIdPlayer().equals(Integer.toString(idClient)))
                tokensLeft = (ModelChangedMessageTokensLeft) message;

    }

    @Override
    public void print() {

        System.out.println("/°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°/");
        System.out.println("/°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°/");
        System.out.println("/°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°/");

        int myPatternCardId = -1;
        for (int i = 0; i < patternCards.size(); i++)
            if(!(Integer.toString(idClient).equals(idPlayers.get(i))))
                printPatternCard(idPlayers.get(i), patternCards.get(i), diceOnPatternCards.get(i));
            else
                myPatternCardId = i;

        System.out.println("\nYour PatternCard");
        printPatternCard(idPlayers.get(myPatternCardId), patternCards.get(myPatternCardId), diceOnPatternCards.get(myPatternCardId));

        printPrivateObjective(privateObjective);

        System.out.println("You have left " + tokensLeft.getTokensLeft() + " tokens to use on ToolCards");

        for (int i = 0; i < roundTrack.size(); i++)
            printRoundTrack(roundTrack.get(i));
        int app = roundTrack.size() + 1;
        System.out.println("\n\nCURRENT ROUND IS:\t" + app );

        printDiceArena(diceArena);

        for (int i = 0; i < 3; i++)
            printPublicObjective(publicObjectives.get(i));

        for (int i = 0; i < 3; i++)
            printToolCards(toolCards.get(i));

    }


}
