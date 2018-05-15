package it.polimi.se2018.model.rounds;

import it.polimi.se2018.model.Player;

import java.util.ArrayList;
import java.util.Collections;

/* TODO: docs */
public class Round {

    private int id;

    private int idPlayerPlaying = -1;

    private int[] rolledDiceLeft;

    private ArrayList<Integer> players;

    private ArrayList<Integer> turns = new ArrayList<>();

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
        createTurns();
    }

    /* TODO: implement next player logic. */
    public void setNextPlayer() throws RoundFinishedException {
        if(!turns.isEmpty()) {
            idPlayerPlaying = turns.get(0);
            turns.remove(0);
        } else {
            throw new RoundFinishedException();
        }
    }

    private void createTurns() {
        ArrayList<Integer> chunk = (ArrayList<Integer>)players.clone();
        Collections.rotate(chunk, players.size() - players.indexOf(idPlayerPlaying));
        turns.addAll(chunk);
        Collections.reverse(chunk);
        turns.addAll(chunk);
        turns.remove(0);
    }
}
