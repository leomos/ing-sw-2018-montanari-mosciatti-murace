package it.polimi.se2018.model;

import it.polimi.se2018.model.container.*;

import java.io.File;
import java.util.ArrayList;

/* TODO: Create method to read PatternCards from JSON. */
public class PatternCard {

    private int id;

    private String name;

    private PatternCardCell[][] cells;

    private int difficulty;

    private DiceContainer diceContainer;

    public PatternCard(DiceContainer diceContainer, String patternCardRepresentation) {
        String[] properties = patternCardRepresentation.split(",");
        this.diceContainer = diceContainer;
        this.id = Integer.parseInt(properties[0]);
        this.name = properties[1];
        this.difficulty = Integer.parseInt(properties[2]);
        for(char c : properties[3].toCharArray()) {
            if(c == '1') {
            }
        }
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public PatternCardCell getPatternCardCell(int x, int y){
        return cells[x][y];
    }

    private ArrayList<Integer[]> checkProximityCells(int x, int y){
        ArrayList<Integer[]> i = new ArrayList<>();
        Integer[] k = new Integer[2];

        if( x+1 < 5 && !getPatternCardCell(x+1, y).isEmpty()) {
            k[0] = x+1;
            k[1] = y;
            i.add(k);
        }

        if( x-1 > 0 && !getPatternCardCell(x-1, y).isEmpty()) {
            k[0] = x - 1;
            k[1] = y;
            i.add(k);
        }

        if( y+1 < 4 && !getPatternCardCell(x, y+1).isEmpty()) {
            k[0] = x;
            k[1] = y + 1;
            i.add(k);
        }

        if( y-1 > 0 && !getPatternCardCell(x, y-1).isEmpty()) {
            k[0] = x;
            k[1] = y - 1;
            i.add(k);
        }

        return i;

    }

    public boolean checkProximityCellsValidity(int rolledDieId, int x, int y)throws DiceContainerUnsupportedIdException {
        Die d = diceContainer.getDie(rolledDieId);
        Die app;

        for( Integer[] i : checkProximityCells(x, y) ) {
            app = diceContainer.getDie(getPatternCardCell(i[0],i[1]).getRolledDieId());
            if( app.getColor() == d.getColor() || app.getRolledValue() == d.getRolledValue()) {
                return false;
            }
        }

        return true;
    }


}
