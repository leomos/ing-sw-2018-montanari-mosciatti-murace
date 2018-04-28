package it.polimi.se2018.model;

public class PatternCard {

    private int id;

    private int favourTokens;

    private PatternCardCell[][] cell;

    private int difficulty;

    public int getId() {
        return id;
    }

    public int getFavourTokens() {
        return favourTokens;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public PatternCardCell getPatterCardCell(int x, int y){
        return cell[x][y];
    }
}
