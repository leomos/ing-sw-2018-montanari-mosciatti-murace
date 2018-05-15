package it.polimi.se2018.model.rounds;

import it.polimi.se2018.model.Player;

import java.util.ArrayList;

/* TODO: tests and docs */
public class Round {

    private int id;

    private int idPlayerPlaying = -1;

    private int[] rolledDiceLeft;

    private ArrayList<Integer> players;

    private int numberOfTurnsPlayed = 1;

    public Round(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getIdPlayerPlaying() {
        return idPlayerPlaying;
    }

    public int[] getRolledDiceLeft() {
        return rolledDiceLeft;
    }

    public void setPlayers(ArrayList<Integer> players) {
        this.players = players;
    }

    public void setRolledDiceLeft(int[] rolledDiceLeft) {
        this.rolledDiceLeft = rolledDiceLeft;
    }

    public void setFirstPlayer(int idFirstPlayer) throws RoundFirstPlayerAlreadySet {
        if(idPlayerPlaying != -1) {
            throw new RoundFirstPlayerAlreadySet();
        }
        idPlayerPlaying = idFirstPlayer;
    }

    /* TODO: implement next player logic. */
    public void setNextPlayer() {
        int currentPlayerPositionInPlayersArray = players.indexOf(idPlayerPlaying);
        numberOfTurnsPlayed++;
        if(players.size() <= numberOfTurnsPlayed) {
            idPlayerPlaying = players.get(currentPlayerPositionInPlayersArray + 1);
        } else if(players.size() + 1 == numberOfTurnsPlayed) {
            idPlayerPlaying = players.get(currentPlayerPositionInPlayersArray);
        } else {
            idPlayerPlaying = players.get(currentPlayerPositionInPlayersArray - 1);
        }
    }
}
