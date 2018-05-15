package it.polimi.se2018.model;

import it.polimi.se2018.model.container.DiceContainer;
import it.polimi.se2018.model.container.DiceContainerUnsupportedIdException;
import it.polimi.se2018.model.container.Die;

import java.util.ArrayList;

import static it.polimi.se2018.model.container.DieColor.*;

/* TODO: Create method to read PatternCards from JSON. */
public class PatternCard {

    private int id;

    private boolean firstMove = true;

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
        int i = 0, j = 0;
        for(char c : properties[3].toCharArray()) {
            if(c == '1') {
                cells[i][j] = new PatternCardCell(diceContainer, null, 1);
            }
            if(c == '2') {
                cells[i][j] = new PatternCardCell(diceContainer, null, 2);
            }
            if(c == '3') {
                cells[i][j] = new PatternCardCell(diceContainer, null, 3);
            }
            if(c == '4') {
                cells[i][j] = new PatternCardCell(diceContainer, null, 4);
            }
            if(c == '5') {
                cells[i][j] = new PatternCardCell(diceContainer, null, 5);
            }
            if(c == '6') {
                cells[i][j] = new PatternCardCell(diceContainer, null, 6);
            }

            if(c == 'g') {
                cells[i][j] = new PatternCardCell(diceContainer, GREEN, 0);
            }
            if(c == 'y') {
                cells[i][j] = new PatternCardCell(diceContainer, YELLOW, 0);
            }
            if(c == 'b') {
                cells[i][j] = new PatternCardCell(diceContainer, BLUE, 0);
            }
            if(c == 'r') {
                cells[i][j] = new PatternCardCell(diceContainer, RED, 0);
            }
            if(c == 'p') {
                cells[i][j] = new PatternCardCell(diceContainer, PURPLE, 0);
            }
            if(c == '0') {
                cells[i][j] = new PatternCardCell(diceContainer, null, 0);
            }



            if(j == 4){
                j = 0;
                i++;
            }else {
                j++;
            }

        }
    }

    public boolean isFirstMove() {
        return firstMove;
    }

    public void setFirstMove(){
        firstMove = false;
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

    /**
     *
     * @param x cell's abscissa
     * @param y cell's ordinate
     * @return proximityCellList list of available positions above, under or next to the cell defined by x and y
     * @throws
     */
    private ArrayList<Integer[]> checkProximityCells(int x, int y){
        ArrayList<Integer[]> proximityCellList = new ArrayList<>();
        Integer[] k = new Integer[2];

        if( x+1 < 5 && !this.cells[x+1][y].isEmpty()) {
            k[0] = x+1;
            k[1] = y;
            proximityCellList.add(k);
        }

        if( x-1 > 0 && !this.cells[x-1][y].isEmpty()) {
            k[0] = x - 1;
            k[1] = y;
            proximityCellList.add(k);
        }

        if( y+1 < 4 && !this.cells[x][y+1].isEmpty()) {
            k[0] = x;
            k[1] = y + 1;
            proximityCellList.add(k);
        }

        if( y-1 > 0 && !this.cells[x][y-1].isEmpty()) {
            k[0] = x;
            k[1] = y - 1;
            proximityCellList.add(k);
        }

        return proximityCellList;

    }

    /**
     *
     * @param x cell's abscissa
     * @param y cell's ordinate
     * @return proximityCellList list of available positions diagonals to the cell defined by x and y
     */
    private ArrayList<Integer[]> checkDiagonalCells(int x, int y){
        ArrayList<Integer[]> proximityCellList = new ArrayList<>();
        Integer[] k = new Integer[2];

        if( x+1 < 5 && y+1 < 4 && !this.cells[x+1][y+1].isEmpty()) {
            k[0] = x+1;
            k[1] = y+1;
            proximityCellList.add(k);
        }

        if( x-1 >= 0 && y+1 < 4 && !this.cells[x-1][y+1].isEmpty()) {
            k[0] = x - 1;
            k[1] = y + 1;
            proximityCellList.add(k);
        }

        if( x+1 < 4 && y-1 >= 0 && !this.cells[x+1][y-1].isEmpty()) {
            k[0] = x + 1;
            k[1] = y - 1;
            proximityCellList.add(k);
        }

        if( x-1 >= 0 && y-1 >= 0 && !this.cells[x-1][y-1].isEmpty()) {
            k[0] = x - 1;
            k[1] = y - 1;
            proximityCellList.add(k);
        }

        return proximityCellList;

    }

    /**
     * @param rolledDieId die's Id
     * @param x cell's abscissa
     * @param y cell's ordinate
     * @return proximityCellList list of available positions around the cell defined by x and y
     * @throws DiceContainerUnsupportedIdException
     */

    /*TODO: test */
    public boolean checkProximityCellsValidity(int rolledDieId, int x, int y)throws DiceContainerUnsupportedIdException {
        Die d = diceContainer.getDie(rolledDieId);
        Die app;

        for( Integer[] i : checkProximityCells(x, y) ) {
            app = diceContainer.getDie(this.cells[i[0]][i[1]].getRolledDieId());
            if( app.getColor() == d.getColor() || app.getRolledValue() == d.getRolledValue()) {
                return false;
            }
        }

        return true;
    }

    /**
     * returns whether there is a die in one of the adjacent position to the cell defined by x and y
     * @param x cell's abscissa
     * @param y cell's ordinate
     * @return
     * @throws DiceContainerUnsupportedIdException
     */
    public boolean checkDieInAdjacentCells(int x, int y) throws DiceContainerUnsupportedIdException {

        for( Integer[] i : checkProximityCells(x, y) ){
            if(!this.cells[i[0]][i[1]].isEmpty())
                return true;
        }

        for( Integer[] i :checkDiagonalCells(x,y)){
            if(!this.cells[i[0]][i[1]].isEmpty())
                return true;
        }

        return false;
    }

    public boolean checkFirstMove(int x, int y){
        return x == 0 || x == 4 || y == 0 || y == 3;
    }

}
