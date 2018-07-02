package it.polimi.se2018.model.patternCard;

import it.polimi.se2018.model.container.DiceContainer;
import it.polimi.se2018.model.container.DiceContainerUnsupportedIdException;
import it.polimi.se2018.model.container.Die;

import java.util.ArrayList;

import static it.polimi.se2018.model.container.DieColor.*;

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

    /**
     * Depending on the patternCardRepresentation, it creates 20 cells. For each char of patternCardRepresentation, it creates
     * a cell with a precise constraint. A cell can't have both a value and a color constraint.
     * @param diceContainer dice container
     * @param id pattern card id
     * @param name pattern card name
     * @param difficulty pattern card difficulty
     * @param patternCardRepresentation pattern card representation, composed of 20 chars where every char corresponds to a cell
     */
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
                this.cells[j][i] = new PatternCardCell(null, 1);
            }
            if(c == '2') {
                this.cells[j][i] = new PatternCardCell(null, 2);
            }
            if(c == '3') {
                this.cells[j][i] = new PatternCardCell(null, 3);
            }
            if(c == '4') {
                this.cells[j][i] = new PatternCardCell(null, 4);
            }
            if(c == '5') {
                this.cells[j][i] = new PatternCardCell(null, 5);
            }
            if(c == '6') {
                this.cells[j][i] = new PatternCardCell(null, 6);
            }

            if(c == 'g') {
                this.cells[j][i] = new PatternCardCell(GREEN, 0);
            }
            if(c == 'y') {
                this.cells[j][i] = new PatternCardCell(YELLOW, 0);
            }
            if(c == 'b') {
                this.cells[j][i] = new PatternCardCell(BLUE, 0);
            }
            if(c == 'r') {
                this.cells[j][i] = new PatternCardCell(RED, 0);
            }
            if(c == 'p') {
                this.cells[j][i] = new PatternCardCell(PURPLE, 0);
            }

            if(c == '0') {
                this.cells[j][i] = new PatternCardCell(null, 0);
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
        this.updateDiceRepresentation();
        return diceRepresentation;
    }

    public String getPatternCardRepresentation() {
        return patternCardRepresentation;
    }

    public PatternCardCell getPatternCardCell(int x, int y){
        return cells[x][y];
    }

    /**
     * @param x cell's abscissa
     * @param y cell's ordinate
     * @return proximityCellList list of available positions above, under or on the side to the cell defined by x and y
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

        if( x+1 < 5 && y-1 >= 0 ) {
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
     * checks whether the die's color or the die's value are present in the cells orthogonal to the one defined by x and y
     * @param rolledDieId die's Id
     * @param x cell's abscissa
     * @param y cell's ordinate
     * @return true if there are no dice with the same color or the same value of the die orthogonal to the cell
     * @throws DiceContainerUnsupportedIdException
     */
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
     * @return true if there is a die
     * @throws DiceContainerUnsupportedIdException if there is a die id not accepted
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

    public void setDieInPatternCard(int idDie, int x, int y, boolean ignoreValueConstraint, boolean ignoreColorConstraint, boolean ignoreAdjency) throws DiceContainerUnsupportedIdException, PatternCardDidNotRespectFirstMoveException, PatternCardNoAdjacentDieException, PatternCardCellIsOccupiedException, PatternCardNotRespectingCellConstraintException, PatternCardNotRespectingNearbyDieExpection, PatternCardAdjacentDieException {
        Die d = diceContainer.getDie(idDie);

        try {
                if (this.getPatternCardCell(x, y).checkDieValidity(d.getRolledValue(), d.getColor(), ignoreValueConstraint, ignoreColorConstraint)) {
                    if (this.getPatternCardCell(x, y).isEmpty()) {
                        if (this.isFirstMove()) {
                            if (this.checkFirstMove(x, y)) {
                                cells[x][y].setRolledDieId(idDie, ignoreValueConstraint, ignoreColorConstraint);
                            } else {
                                throw new PatternCardDidNotRespectFirstMoveException();
                            }
                        } else if (this.checkProximityCellsValidity(idDie, x, y)) {
                            if (!ignoreAdjency){
                                if (this.checkDieInAdjacentCells(x, y))
                                    cells[x][y].setRolledDieId(idDie, ignoreValueConstraint, ignoreColorConstraint);
                                else {
                                    throw new PatternCardNoAdjacentDieException();
                                }
                            } else {
                                if(!this.checkDieInAdjacentCells(x, y))
                                    cells[x][y].setRolledDieId(idDie, ignoreValueConstraint, ignoreColorConstraint);
                                else
                                    throw new PatternCardAdjacentDieException();
                            }
                        } else {
                            throw new PatternCardNotRespectingNearbyDieExpection();
                        }
                    } else {
                        throw new PatternCardCellIsOccupiedException();
                    }
                } else {
                    throw new PatternCardNotRespectingCellConstraintException();
                }

        } catch (DiceContainerUnsupportedIdException e) {
            e.printStackTrace();
        }
    }

    public boolean checkFirstMove(int x, int y){
        return x == 0 || x == 4 || y == 0 || y == 3;
    }

    /**
     * create a list of all the available positions for a die inside this pattern card
     * @param idDie die id that the player wants to set
     * @return array list containing all the positions (in group of two)
     * @throws DiceContainerUnsupportedIdException if there is a die id not accepted
     */
    public ArrayList<Integer> getAvailablePositions(int idDie) throws DiceContainerUnsupportedIdException {

        Die d = null;
        ArrayList<Integer> list = new ArrayList<>();

        try {
            d = diceContainer.getDie(idDie);
        } catch (DiceContainerUnsupportedIdException e) {
            e.printStackTrace();
        }

        for(int i = 0; i < 5; i++)
            for(int j = 0; j < 4; j++)
                if(!firstMove){
                    if(cells[i][j].checkDieValidity(d.getRolledValue(), d.getColor(), false, false)
                            && this.checkProximityCellsValidity(idDie, i, j) && this.checkDieInAdjacentCells(i,j) &&cells[i][j].isEmpty()) {
                        list.add(i);
                        list.add(j);
                    }
                }else if(checkFirstMove(i,j) && cells[i][j].checkDieValidity(d.getRolledValue(), d.getColor(), false, false)
                        && cells[i][j].isEmpty()) {
                    list.add(i);
                    list.add(j);
                }
        return list;
    }

    /**
     * @return number of dice present in the pattern card
     */
    public int getNumberOfDiceInThePatternCard(){
        int count = 0;
        for(int i = 0; i < 5; i++)
            for(int j = 0; j < 4; j++)
                if(!cells[i][j].isEmpty())
                    count++;
        return count;
    }

    /**
     * Creates a single string representing the dice on this pattern card. For each die, it adds to the
     * string the id, followed by char representing the color, followed by the rolled value.
     * If the die in one cell is not present, it instead puts in the string ****
     */
    private void updateDiceRepresentation() {
        diceRepresentation = "";
        for(int i = 0; i < 4; i++)
            for(int j = 0; j < 5; j++){
                PatternCardCell c = cells[j][i];
                if(c.isEmpty())
                    diceRepresentation = diceRepresentation + "****";
                else {
                    if (c.getRolledDieId() < 10)
                        diceRepresentation = diceRepresentation + "0";
                    try {
                        diceRepresentation = diceRepresentation + c.getRolledDieId()
                                + diceContainer.getDie(c.getRolledDieId()).getColorChar()
                                + diceContainer.getDie(c.getRolledDieId()).getRolledValue();
                    } catch (DiceContainerUnsupportedIdException e) {
                        e.printStackTrace();
                    }
                }
            }
    }



}
