package it.polimi.se2018.view;

import it.polimi.se2018.model.events.ModelChangedMessage;
import it.polimi.se2018.model.events.ModelChangedMessagePatternCard;
import it.polimi.se2018.model.events.ModelChangedMessagePrivateObjective;

import java.util.ArrayList;

public class ViewClientConsoleSetup extends ViewClientConsolePrint {

    private int idClient;

    private ArrayList<ModelChangedMessagePatternCard> patternCards = new ArrayList<ModelChangedMessagePatternCard>();

    private ModelChangedMessagePrivateObjective privateObjective;

    public ViewClientConsoleSetup(int idClient){
        this.idClient = idClient;
    }

    @Override
    public void update(ModelChangedMessage message){
        if(message instanceof ModelChangedMessagePatternCard)
            if(((ModelChangedMessagePatternCard) message).getIdPlayer() == Integer.toString(idClient))
                patternCards.add((ModelChangedMessagePatternCard)message);
        else if(message instanceof ModelChangedMessagePrivateObjective)
            if(((ModelChangedMessagePrivateObjective)message).getIdPlayer() == Integer.toString(idClient))
                    privateObjective = ((ModelChangedMessagePrivateObjective)message);

    }

    @Override
    public void print() {

        for (int i = 0; i < patternCards.size(); i++)
            printPatternCard(patternCards.get(i), null);

        /* private objective? devo usare id view per stampare quello giusto*/

        }

}
