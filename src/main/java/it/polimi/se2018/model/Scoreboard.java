package it.polimi.se2018.model;

import java.util.ArrayList;
import java.util.HashMap;

public class Scoreboard {

    private ArrayList<Integer> player;

    private HashMap<Integer, Integer> tokens;

    private int lastId;

    private HashMap<Integer, Integer> score;

    public Scoreboard(int lastId){
        this.lastId = lastId;
    }

    public void setScore(int idPlayer, int score, int tokensLeft) {
        this.player.add(idPlayer);
        this.score.put(idPlayer, score);
        this.tokens.put(idPlayer, tokensLeft);
    }

    /* TODO: order HashMap */
    public HashMap<Integer, Integer> getScoreBoard(){

        return score;
    }

}
