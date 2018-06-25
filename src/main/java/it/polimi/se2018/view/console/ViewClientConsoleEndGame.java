package it.polimi.se2018.view.console;

import it.polimi.se2018.model.events.ModelChangedMessage;
import it.polimi.se2018.model.events.ModelChangedMessageEndGame;

import java.util.ArrayList;

public class ViewClientConsoleEndGame extends ViewClientConsolePrint {

    private int idClient;

    private ModelChangedMessageEndGame scoreboardMessage;


    public ViewClientConsoleEndGame(int idClient){
        this.idClient = idClient;
    }

    @Override
    public void update(ModelChangedMessage message) {

        if (message instanceof ModelChangedMessageEndGame){
            scoreboardMessage = (ModelChangedMessageEndGame) message;
            this.print();
        }

    }

    @Override
    public void print() {

        String scoreboard = scoreboardMessage.getScoreboard();

        System.out.println(scoreboard);

        for(int i = 0; i < scoreboard.length(); i+=5){


            System.out.println(((i/5)+1) + "° place ->" + scoreboard.charAt(i) + " with " + scoreboard.substring(i+1, i+4) + "\tTokens Left: " + scoreboard.charAt(i+4));

        }

    }

    @Override
    public Integer askForPatternCard() {
        return null;
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
}
