package it.polimi.se2018.view.console;

import it.polimi.se2018.model.events.ModelChangedMessage;
import it.polimi.se2018.model.events.ModelChangedMessageEndGame;
import it.polimi.se2018.model.events.PlayerMessage;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

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

        System.out.println("\n\n/°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°/");
        System.out.println("/°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°/");
        System.out.println("/°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°/");

        for (String s : scoreboard.split("/")) {
            String[] score = s.split(";");
            if(score.length == 3)
                System.out.println("ID: " + score[0] + " | " + "SCORE: " + score[1] + " | " + "TOKENS LEFT: " + score[2]);
        }

        try {
            TimeUnit.SECONDS.sleep(30);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        System.exit(0);

    }

    @Override
    public Integer askForPatternCard(String s) {
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
