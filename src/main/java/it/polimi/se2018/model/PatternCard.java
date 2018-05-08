package it.polimi.se2018.model;

import it.polimi.se2018.model.container.*;

import java.util.ArrayList;

public class PatternCard {

    private int id;

    private int favourTokens;

    private PatternCardCell[][] cell;

    private int difficulty;

    private DiceContainer diceContainer;

    public int getId() {
        return id;
    }

    public int getFavourTokens() {
        return favourTokens;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public PatternCardCell getPatternCardCell(int x, int y){
        return cell[x][y];
    }

    private ArrayList<Integer[]> checkProximityCells(int x, int y){
        ArrayList<Integer[]> i = new ArrayList<Integer[]>();
        Integer k[] = new Integer[2];

        if( x+1 < 5 && !getPatternCardCell(x+1, y).isEmpy()) {
            k[0] = x+1;
            k[1] = y;
            i.add(k);
        }

        if( x-1 > 0 && !getPatternCardCell(x-1, y).isEmpy()) {
            k[0] = x - 1;
            k[1] = y;
            i.add(k);
        }

        if( y+1 < 4 && !getPatternCardCell(x, y+1).isEmpy()) {
            k[0] = x;
            k[1] = y + 1;
            i.add(k);
        }

        if( y-1 > 0 && !getPatternCardCell(x, y-1).isEmpy()) {
            k[0] = x;
            k[1] = y - 1;
            i.add(k);
        }

        return i;

    }

    public boolean checkProximityCellsValidity(int throwedDieId, int x, int y)throws DiceContainerUnsupportedIdException {
        Die d = diceContainer.getDie(throwedDieId);
        Die app;

        for( Integer i[] : checkProximityCells(x, y) ) {
            app = diceContainer.getDie(getPatternCardCell(i[0],i[1]).getThrowedDieId());
            if( app.getColor() == d.getColor() || app.getThrowedValue() == d.getThrowedValue()) {
                return false;
            }
        }

        return true;
    }


}
