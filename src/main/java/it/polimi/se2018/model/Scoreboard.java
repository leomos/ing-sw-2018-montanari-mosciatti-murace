package it.polimi.se2018.model;

import java.util.ArrayList;
import java.util.HashMap;

public class Scoreboard {

    private ArrayList<Integer> player = new ArrayList<>();

    private HashMap<Integer, Integer> tokens = new HashMap<>();

    private int lastId;

    private String representation;

    private HashMap<Integer, Integer> score = new HashMap<>();

    private HashMap<Integer, Integer> orderedScore = new HashMap<>();

    public Scoreboard(int lastId){
        this.lastId = lastId;
    }

    public String getRepresentation() {
        this.updateRepresentation();
        return representation;
    }

    public HashMap<Integer, Integer> getScore() {
        return score;
    }

    public HashMap<Integer, Integer> getTokens() {
        return tokens;
    }

    public void setScore(int idPlayer, int score, int tokensLeft) {
        this.player.add(idPlayer);
        this.score.put(idPlayer, score);
        this.tokens.put(idPlayer, tokensLeft);
    }

    private void updateRepresentation(){
        representation = "";

        for(Integer key : player){

            representation += key;

            if(score.get(key) < 100)
                representation += "0";
            if(score.get(key) < 10)
                representation += "0";
            if(score.get(key) < 0 )
                representation += "0";
            else
                representation += "" + score.get(key);

            representation += tokens.get(key);

        }

        representation += lastId;

    }

}
