package it.polimi.se2018.view.console;

import it.polimi.se2018.model.events.*;

import java.util.ArrayList;

public class ViewClientConsoleSetup extends ViewClientConsolePrint {

    private int idClient;

    private ArrayList<ModelChangedMessagePatternCard> patternCards = new ArrayList<ModelChangedMessagePatternCard>();

    private ModelChangedMessagePrivateObjective privateObjective;

    public ViewClientConsoleSetup(int idClient){
        this.idClient = idClient;
    }

    @Override
    public void update(ModelChangedMessagePatternCard message) {
        if (message.getIdPlayer() == idClient)
            patternCards.add(message);
    }

    @Override
    public void update(ModelChangedMessagePrivateObjective message) {
        if (message.getIdPlayer() == idClient)
            privateObjective = message;
    }

    @Override
    public void update(ModelChangedMessageDiceOnPatternCard message) {

    }

    @Override
    public void update(ModelChangedMessagePublicObjective message) {

    }

    @Override
    public void update(ModelChangedMessageDiceArena message) {

    }

    @Override
    public void update(ModelChangedMessageRound message) {

    }

    @Override
    public void update(ModelChangedMessageTokensLeft message) {

    }

    @Override
    public void update(ModelChangedMessageEndGame message) {

    }

    @Override
    public void update(ModelChangedMessageToolCard message) {

    }

    @Override
    public void update(ModelChangedMessageOnlyOnePlayerLeft message) {

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
            if(Integer.toString(patternCards.get(i).getIdPatternCard()).equals(s)) {
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
