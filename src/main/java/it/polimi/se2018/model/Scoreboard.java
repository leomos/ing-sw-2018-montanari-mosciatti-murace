package it.polimi.se2018.model;

import java.util.*;

public class Scoreboard {

    private ArrayList<Integer> player = new ArrayList<>();

    private HashMap<Integer, Integer> tokens = new HashMap<>();

    private int lastId;

    private String representation;

    private HashMap<Integer, Integer[]> board = new HashMap<>();


    public Scoreboard(int lastId){
        this.lastId = lastId;
    }

    public String getRepresentation() {
        representation = "";
        for(Integer[] score : boardOrderedByScore()) {
            representation += score[0] + ";" + score[1] + ";" + score[2] + "/";
        }

        representation += lastId;

        return representation;
    }

    public void setScore(int idPlayer, int score, int tokensLeft) {
        Integer[] values = new Integer[2];
        values[0] = score;
        values[1] = tokensLeft;
        board.put(idPlayer, values);
    }

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
