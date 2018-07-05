package it.polimi.se2018.view.console;

import it.polimi.se2018.model.events.*;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class ViewClientConsoleEndGame extends ViewClientConsolePrint {

    private int idClient;

    private ModelChangedMessageEndGame scoreboardMessage;

    public ViewClientConsoleEndGame(int idClient){
        this.idClient = idClient;
    }

    @Override
    public void update(ModelChangedMessageEndGame message) {
        scoreboardMessage = message;
        this.print();
    }

    @Override
    public void update(ModelChangedMessageOnlyOnePlayerLeft message) {
        System.out.println("\n\nOnly player " + message.getPlayerIdLeft() + " - " + message.getPlayers().get(idClient) + " is left in the game!");
        if(message.getPlayerIdLeft() == idClient)
            System.out.println("You won!");
        else
            System.out.println("You lost!");

        System.out.println("\n\nPress any key to close the game.");
    }

    @Override
    public void update(ModelChangedMessagePatternCard message) {

    }

    @Override
    public void update(ModelChangedMessagePrivateObjective message) {

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
    public void update(ModelChangedMessageToolCard message) {

    }

    @Override
    public void print() {

        String scoreboard = scoreboardMessage.getScoreboard();

        System.out.println("\n\n/°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°/");
        System.out.println("/°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°/");
        System.out.println("/°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°/\n\n");

        System.out.println("FINAL SCORE:\n\n");

        for (String s : scoreboard.split("/")) {
            String[] score = s.split(";");
            if(score.length == 3)
                System.out.println("ID: " + score[0] + " - " + scoreboardMessage.getPlayers().get(idClient) + " | " + "SCORE: " + score[1] + " | " + "TOKENS LEFT: " + score[2]);
        }

        System.out.println("\n\nPress any key to close the game.");

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
