package it.polimi.se2018.model;

import java.util.*;

public class Scoreboard {

    private int lastId;

    private String representation;

    private HashMap<Integer, Integer[]> board = new HashMap<>();

    public Scoreboard(int lastId){
        this.lastId = lastId;
    }

    /**
     * Creates a single string that represents the final score.
     * The string is structured like:
     * 1° playerId; 1° score; 1° left tokens / 2° playerId; 2° score; 2° left tokens / last playerId playing
     * Obviously there can be up to 4 players in the string
     * @return string representation of final scores
     */
    public String getRepresentation() {
        representation = "";
        for(Integer[] score : boardOrderedByScore()) {
            representation += score[0] + ";" + score[1] + ";" + score[2] + "/";
        }

        representation += lastId;

        return representation;
    }

    /**
     * Set the score of a single player in the score board
     * @param idPlayer player id
     * @param score player final score
     * @param tokensLeft player tokens left
     */
    public void setScore(int idPlayer, int score, int tokensLeft) {
        Integer[] values = new Integer[2];
        values[0] = score;
        values[1] = tokensLeft;
        board.put(idPlayer, values);
    }

    //todo: javadoc
    private List<Integer[]> boardOrderedByScore() {
        List<Integer[]> orderedBoard = new ArrayList();
        Comparator<Integer[]> byScoreValue = new Comparator<Integer[]>() {
            @Override
            public int compare(Integer[] o1, Integer[] o2) {
                return o2[1] - o1[1];
            }
        };
        board.forEach((key, value) -> {
            Integer[] score = {key, value[0], value[1]};
            orderedBoard.add(score);
        });
        Collections.sort(orderedBoard, byScoreValue);
        return orderedBoard;
    }

}
