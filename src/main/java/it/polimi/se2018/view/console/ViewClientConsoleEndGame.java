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

        if(scoreboard != null) {

            for (int i = 0; i < scoreboard.length() - 1; i += 5)
                System.out.println(((i / 5) + 1) + "° place ->" + scoreboard.charAt(i) + " with " + scoreboard.substring(i + 1, i + 4) + "\tTokens Left: " + scoreboard.charAt(i + 4));

            System.out.println("The last player to play was " + scoreboard.charAt(scoreboard.length() - 1));

            if (scoreboard.charAt(0) == idClient + '0')
                System.out.println("CONGRATS, YOU WON");
            else
                System.out.println("CONGRATS, YOU LOSE");

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
