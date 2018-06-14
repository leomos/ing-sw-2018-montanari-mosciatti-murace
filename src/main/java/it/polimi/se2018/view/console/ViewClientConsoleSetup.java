package it.polimi.se2018.view.console;

import it.polimi.se2018.model.events.ModelChangedMessage;
import it.polimi.se2018.model.events.ModelChangedMessagePatternCard;
import it.polimi.se2018.model.events.ModelChangedMessagePrivateObjective;

import java.util.ArrayList;
import java.util.Scanner;

public class ViewClientConsoleSetup extends ViewClientConsolePrint {

    private int idClient;

    private ArrayList<ModelChangedMessagePatternCard> patternCards = new ArrayList<ModelChangedMessagePatternCard>();

    private ModelChangedMessagePrivateObjective privateObjective;

    public ViewClientConsoleSetup(int idClient){
        this.idClient = idClient;
    }

    @Override
    public void update(ModelChangedMessage message){
        if(message instanceof ModelChangedMessagePatternCard) {
            if (((ModelChangedMessagePatternCard) message).getIdPlayer().equals(Integer.toString(idClient)))
                patternCards.add((ModelChangedMessagePatternCard) message);
        }
        else if(message instanceof ModelChangedMessagePrivateObjective)
            if (((ModelChangedMessagePrivateObjective) message).getIdPlayer().equals(Integer.toString(idClient)))
                privateObjective = ((ModelChangedMessagePrivateObjective) message);

    }

    @Override
    public void print() {
        System.out.println("SETUP: print");
        for (int i = 0; i < patternCards.size(); i++)
            printPatternCard(patternCards.get(i));

        printPrivateObjective(privateObjective);

        }


}
