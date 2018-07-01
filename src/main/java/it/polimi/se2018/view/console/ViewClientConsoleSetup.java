package it.polimi.se2018.view.console;

import it.polimi.se2018.model.events.ModelChangedMessage;
import it.polimi.se2018.model.events.ModelChangedMessagePatternCard;
import it.polimi.se2018.model.events.ModelChangedMessagePrivateObjective;
import it.polimi.se2018.model.events.PlayerMessage;

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
        System.out.println("\nChoose a PatternCard by selecting one of the PatternCardId");

    }

    @Override
    public Integer askForPatternCard(String s)  {



            for(int i = 0; i < patternCards.size(); i++){
                if(patternCards.get(i).getIdPatternCard().equals(s)) {
                    return Integer.parseInt(s);
                }
            }

            System.out.println("Try Again!");

        return -1;
    }

    @Override
    public ArrayList<Integer> getPositionInPatternCard() {
        return null;
    }

    @Override
    public ArrayList<Integer> getSinglePositionInPatternCard(ArrayList<Integer> listOfAvailablePositions) {
        return null;
    }

    @Override
    public ArrayList<Integer> getIncrementedValue() {
        return null;
    }

    @Override
    public Integer getDieFromDiceArena() {
        return null;
    }

    @Override
    public ArrayList<Integer> getDieFromRoundTrack() {
        return null;
    }

    @Override
    public Integer getValueForDie() {
        return null;
    }

    @Override
    public ArrayList<Integer> getDoublePositionInPatternCard() { return null;}

    @Override
    public PlayerMessage getMainMove(String s) {
        return null;
    }

    @Override
    public void setSuspended(boolean suspended) {

    }


}
