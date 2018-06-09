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

    private static final int NUMBER_OF_COLUMS= 5;
    private static final int NUMBER_OF_ROW = 4;

    private PatternCardCell[][] cells = new PatternCardCell[NUMBER_OF_COLUMS][NUMBER_OF_ROW];

    private int difficulty;

    private DiceContainer diceContainer;

    private String patternCardRepresentation;

    private String diceRepresentation = "********************************************************************************";

    public PatternCard(DiceContainer diceContainer, int id, String name, int difficulty, String patternCardRepresentation) {
        this.diceContainer = diceContainer;
        this.id = id;
        this.name = name;
        this.difficulty = difficulty;
        this.patternCardRepresentation = patternCardRepresentation;
        int i, j;
        i = 0;
        j = 0;
        for(char c : patternCardRepresentation.toCharArray()) {

            if(c == '1') {
                this.cells[j][i] = new PatternCardCell(diceContainer, null, 1);
            }
            if(c == '2') {
                this.cells[j][i] = new PatternCardCell(diceContainer, null, 2);
            }
            if(c == '3') {
                this.cells[j][i] = new PatternCardCell(diceContainer, null, 3);
            }
            if(c == '4') {
                this.cells[j][i] = new PatternCardCell(diceContainer, null, 4);
            }
            if(c == '5') {
                this.cells[j][i] = new PatternCardCell(diceContainer, null, 5);
            }
            if(c == '6') {
                this.cells[j][i] = new PatternCardCell(diceContainer, null, 6);
            }

            if(c == 'g') {
                this.cells[j][i] = new PatternCardCell(diceContainer, GREEN, 0);
            }
            if(c == 'y') {
                this.cells[j][i] = new PatternCardCell(diceContainer, YELLOW, 0);
            }
            if(c == 'b') {
                this.cells[j][i] = new PatternCardCell(diceContainer, BLUE, 0);
            }
            if(c == 'r') {
                this.cells[j][i] = new PatternCardCell(diceContainer, RED, 0);
            }
            if(c == 'p') {
                this.cells[j][i] = new PatternCardCell(diceContainer, PURPLE, 0);
            }

            if(c == '0') {
                this.cells[j][i] = new PatternCardCell(diceContainer, null, 0);
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

    public String getDiceRepresentation() {
        return diceRepresentation;
    }

    public String getPatternCardRepresentation() {
        return patternCardRepresentation;
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
        Integer[][] k = new Integer[4][2];

        if( x+1 < 5 && !this.cells[x+1][y].isEmpty()) {
            k[0][0] = x+1;
            k[0][1] = y;
            proximityCellList.add(k[0]);
        }

        if( x-1 >= 0 && !this.cells[x-1][y].isEmpty()) {
            k[1][0] = x - 1;
            k[1][1] = y;
            proximityCellList.add(k[1]);
        }

        if( y+1 < 4 && !this.cells[x][y+1].isEmpty()) {
            k[2][0] = x;
            k[2][1] = y + 1;
            proximityCellList.add(k[2]);
        }

        if( y-1 >= 0 && !this.cells[x][y-1].isEmpty()) {
            k[3][0] = x;
            k[3][1] = y - 1;
            proximityCellList.add(k[3]);
        }

        return proximityCellList;

    }

    /**
     *
     * @param x cell's abscissa
     * @param y cell's ordinate
     * @return proximityCellList list of available positions diagonals to the cell defined by x and y
     */
    public ArrayList<Integer[]> checkDiagonalCells(int x, int y){
        ArrayList<Integer[]> diagonalsCellList = new ArrayList<>();
        Integer[][] k = new Integer[4][2];

        if( x+1 < 5 && y+1 < 4 ) {
            k[0][0] = x+1;
            k[0][1] = y+1;
            diagonalsCellList.add(k[0]);
        }

        if( x-1 >= 0 && y+1 < 4 ) {
            k[1][0] = x - 1;
            k[1][1] = y + 1;
            diagonalsCellList.add(k[1]);
        }

        if( x+1 < 4 && y-1 >= 0 ) {
            k[2][0] = x + 1;
            k[2][1] = y - 1;
            diagonalsCellList.add(k[2]);
        }

        if( x-1 >= 0 && y-1 >= 0 ) {
            k[3][0] = x - 1;
            k[3][1] = y - 1;
            diagonalsCellList.add(k[3]);
        }

        return diagonalsCellList;

    }

    /**
     * @param rolledDieId die's Id
     * @param x cell's abscissa
     * @param y cell's ordinate
     * @return proximityCellList list of available positions around the cell defined by x and y
     * @throws DiceContainerUnsupportedIdException
     */
    /*TODO: test + change Proximity to Ortogonal */
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

    public void updateDiceRepresentation() throws DiceContainerUnsupportedIdException {
        diceRepresentation = "";
        for(int i = 0; i < 4; i++)
            for(int j = 0; j < 5; j++){
                PatternCardCell c = cells[j][i];
                if(c.isEmpty())
                    diceRepresentation = diceRepresentation + "****";
                else {
                    if (c.getRolledDieId() < 10)
                        diceRepresentation = diceRepresentation + "0";
                    diceRepresentation = diceRepresentation + c.getRolledDieId()
                            + diceContainer.getDie(c.getRolledDieId()).getColorChar()
                            + diceContainer.getDie(c.getRolledDieId()).getRolledValue();
                }
            }
    }



}
