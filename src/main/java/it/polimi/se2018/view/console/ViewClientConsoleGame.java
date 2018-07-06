package it.polimi.se2018.view.console;

import it.polimi.se2018.model.events.*;

import java.util.ArrayList;
import java.util.Scanner;

public class ViewClientConsoleGame extends ViewClientConsolePrint {

    private int idClient;

    private boolean suspended = false;

    private Scanner input;

    private ArrayList<Integer> idPlayers = new ArrayList<Integer>();

    private ArrayList<ModelChangedMessagePatternCard> patternCards = new ArrayList<ModelChangedMessagePatternCard>();

    private ArrayList<ModelChangedMessageDiceOnPatternCard> diceOnPatternCards = new ArrayList<ModelChangedMessageDiceOnPatternCard>();

    private ArrayList<ModelChangedMessagePublicObjective> publicObjectives = new ArrayList<ModelChangedMessagePublicObjective>();

    private ArrayList<ModelChangedMessageToolCard> toolCards = new ArrayList<ModelChangedMessageToolCard>();

    private ModelChangedMessageDiceArena diceArena;

    private ModelChangedMessagePrivateObjective privateObjective;

    private ModelChangedMessageTokensLeft tokensLeft;

    private ArrayList<ModelChangedMessageRound> roundTrack = new ArrayList<ModelChangedMessageRound>();

    public ViewClientConsoleGame(int idClient){
        this.idClient = idClient;
    }


    @Override
    public void update(ModelChangedMessagePatternCard modelChangedMessagePatternCard){
        idPlayers.add(modelChangedMessagePatternCard.getIdPlayer());
        patternCards.add(modelChangedMessagePatternCard);
        diceOnPatternCards.add(null);
    }

    public void update(ModelChangedMessageDiceOnPatternCard modelChangedMessageDiceOnPatternCard){
        int i = idPlayers.indexOf(modelChangedMessageDiceOnPatternCard.getIdPlayer());
        diceOnPatternCards.remove(i);
        diceOnPatternCards.add(i, modelChangedMessageDiceOnPatternCard);
    }

    public void update(ModelChangedMessagePrivateObjective modelChangedMessagePrivateObjective){
        if (modelChangedMessagePrivateObjective.getIdPlayer() == idClient)
            privateObjective = modelChangedMessagePrivateObjective;
    }

    public void update(ModelChangedMessagePublicObjective modelChangedMessagePublicObjective){
        publicObjectives.add(modelChangedMessagePublicObjective);
    }

    @Override
    public void update(ModelChangedMessageDiceArena modelChangedMessageDiceArena) {
        diceArena = modelChangedMessageDiceArena;
    }

    @Override
    public void update(ModelChangedMessageRound modelChangedMessageRound) {
        int i = modelChangedMessageRound.getIdRound();
        if(i >= roundTrack.size())
            roundTrack.add(modelChangedMessageRound);
        else {
            roundTrack.remove(i);
            roundTrack.add(i,modelChangedMessageRound);
        }
    }

    @Override
    public void update(ModelChangedMessageTokensLeft modelChangedMessageTokensLeft) {
        if (modelChangedMessageTokensLeft.getIdPlayer() == idClient)
            tokensLeft = modelChangedMessageTokensLeft;
    }

    @Override
    public void update(ModelChangedMessageToolCard modelChangedMessageToolCard) {
        if(toolCards.size() == 3) {
            for(int i = 0; i < 3; i++)
                if(toolCards.get(i).getIdToolCard() == (modelChangedMessageToolCard.getIdToolCard())) {
                    toolCards.remove(i);
                    toolCards.add(i,modelChangedMessageToolCard);
                }
        } else {
            toolCards.add(modelChangedMessageToolCard);
        }
    }

    @Override
    public void update(ModelChangedMessageOnlyOnePlayerLeft message) {

    }

    @Override
    public void update(ModelChangedMessageEndGame modelChangedMessageEndGame) {

    }

    public void setSuspended(boolean suspended) {
        this.suspended = suspended;
    }

    /**
     * Prints the table:
     * first it prints the other player's pattern card
     * then it prints the rounds, the tool card, the public objectives
     * then it prints the player's information (pattern card and private objective) + the draft pool
     */
    @Override
    public void print() {

        System.out.println("/°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°/");
        System.out.println("/°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°/");
        System.out.println("/°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°/");

        int myPatternCardId = -1;
        for (int i = 0; i < patternCards.size(); i++)
            if(idClient != idPlayers.get(i))
                printPatternCard(Integer.toString(idPlayers.get(i)), patternCards.get(i), diceOnPatternCards.get(i));
            else
                myPatternCardId = i;

        for (ModelChangedMessageRound round : roundTrack) printRoundTrack(round);
        int app = roundTrack.size() + 1;
        System.out.println("\n\nCURRENT ROUND IS:\t" + app );



        for (int i = 0; i < 3; i++)
            printPublicObjective(publicObjectives.get(i));

        for (int i = 0; i < 3; i++)
            printToolCards(toolCards.get(i));

        System.out.println("\n\n\nYour PatternCard");
        printPatternCard(Integer.toString(idPlayers.get(myPatternCardId)), patternCards.get(myPatternCardId), diceOnPatternCards.get(myPatternCardId));

        printPrivateObjective(privateObjective);

        System.out.println("You have left " + tokensLeft.getTokensLeft() + " tokens to use on ToolCards");
        printDiceArena(diceArena);
        System.out.println("\n");

    }

    /**
     * checks if the string inserted by the player is acceptable and generates either a playerMessageDie,
     * a playerMessageToolCard, player playerMessageEndTurn. If the moves is not correct, it generates a
     * playerMessage with clientId as -1
     * @param s string inserted by the player
     * @return playerMessage if the move was correct otherwise a playerMessage with clientId as -1
     */
    public PlayerMessage getMainMove(String s){


        String[] parts = s.split(" ");

        if (s.equals("/help")) {
            System.out.println("\nset + DieId + x_position + y_position  -> to position a die from the dice arena to the patterncard\n" +
                    "use + toolCardID -> to use the toolCard\nend  -> to end the turn");
        }

        if (parts.length == 4) {
            for (int i = 0; i < 9; i++)
                for (int j = 0; j < 5; j++)
                    for (int k = 0; k < 4; k++) {
                        String app = "set " + i + " " + j + " " + k;
                        if (s.equals(app)) {
                            int idDie = Integer.parseInt(parts[1]);
                            int x = Integer.parseInt(parts[2]);
                            int y = Integer.parseInt(parts[3]);
                            return new PlayerMessageDie(idClient, idDie, x, y);
                        }
                    }
        }

        if (parts.length == 2) {
            for (int i = 1; i < 13; i++) {
                String app = "use " + i;
                if (s.equals(app)) {
                    int idToolCard = Integer.parseInt(parts[1]);
                    return new PlayerMessageToolCard(idClient, idToolCard);
                }
            }
        }

        if (s.equals("end")) {
            return new PlayerMessageEndTurn(idClient);
        }

        return new PlayerMessageNotAFK(-1);

    }

    /**
     * Method needed for pattern card n.2, n.3 and n.4 to get a starting and a final position to move a die
     * @return array list containing the starting and the final positions
     */
    @Override
    public ArrayList<Integer> getPositionInPatternCard(){
        ArrayList<Integer> position = new ArrayList<>();
        boolean moveOk1, moveOk2;

        do {
            System.out.println("\nInsert Starting position on PatternCard separated by a space");

            moveOk1 = true;
            moveOk2 = true;

            Scanner input = new Scanner(System.in);
            String s = input.nextLine();

            for(int i = 0; i < 5; i++) {
                for (int j = 0; j < 4; j++) {
                    String app = "" + i + " " + j;
                    if (app.equals(s)) {
                        moveOk1 = false;
                        position.add(Integer.parseInt(s.split(" ")[0]));
                        position.add(Integer.parseInt(s.split(" ")[1]));
                    }
                }
            }

            System.out.println("\nInsert Final position on PatternCard separated by a space");

            input = new Scanner(System.in);
            s = input.nextLine();

            for(int i = 0; i < 5; i++) {
                for (int j = 0; j < 4; j++) {
                    String app = "" + i + " " + j;
                    if (app.equals(s)) {
                        moveOk2 = false;
                        position.add(Integer.parseInt(s.split(" ")[0]));
                        position.add(Integer.parseInt(s.split(" ")[1]));
                    }
                }
            }

            if(!suspended && (moveOk1 || moveOk2))
                System.out.println("Try Again!");
        }
        while((moveOk1 || moveOk2) && !suspended);

        return position;
    }

    /**
     * Method needed for tool card n.12 to move either one or two dice
     * @return array list containing the start and the final positions of the movement(s)
     */
    @Override
    public ArrayList<Integer> getDoublePositionInPatternCard(){
        ArrayList<Integer> position = new ArrayList<>();
        boolean moveNotOk1, moveNotOk2;
        int numberOfMovements = 0;
        input = new Scanner(System.in);

        do {
            moveNotOk1 = true;
            System.out.println("How many dice of the same color do you want to move? 1 or 2?");
            String s = input.nextLine();

            if(s.equals("1")) {
                moveNotOk1 = false;
                numberOfMovements = 1;
            }else if(s.equals("2")){
                moveNotOk1 = false;
                numberOfMovements = 2;
            }

        }while(moveNotOk1 && !suspended);

        if(!suspended)
            for (int k = 0; k < numberOfMovements; k++) {
                do {
                    System.out.println("\nInsert Starting position for "+ k+1 +"° movement on PatternCard separated by a space ");

                    moveNotOk1 = true;
                    moveNotOk2 = true;

                    input = new Scanner(System.in);
                    String s = input.nextLine();

                    for (int i = 0; i < 5; i++) {
                        for (int j = 0; j < 4; j++) {
                            String app = "" + i + " " + j;
                            if (app.equals(s)) {
                                moveNotOk1 = false;
                                position.add(Integer.parseInt(s.split(" ")[0]));
                                position.add(Integer.parseInt(s.split(" ")[1]));
                            }
                        }
                    }

                    System.out.println("\nInsert Final position for " + k+1 + "° movement on PatternCard separated by a space");

                    input = new Scanner(System.in);
                    s = input.nextLine();

                    for (int i = 0; i < 5; i++) {
                        for (int j = 0; j < 4; j++) {
                            String app = "" + i + " " + j;
                            if (app.equals(s)) {
                                moveNotOk2 = false;
                                position.add(Integer.parseInt(s.split(" ")[0]));
                                position.add(Integer.parseInt(s.split(" ")[1]));
                            }
                        }
                    }

                    if(!suspended && (moveNotOk1 || moveNotOk2))
                        System.out.println("Try Again!");
                }
                while ((moveNotOk1 || moveNotOk2) && !suspended);
            }

        return position;
    }

    /**
     * Get a single position (x and y) that needs to be contained in listOfAvailablePositions.
     * If list of available positions is empty, the player can insert any positions
     * @param listOfAvailablePositions positions available
     * @return array list containing the position chosen by the player
     */
    @Override
    public ArrayList<Integer> getSinglePositionInPatternCard(ArrayList<Integer> listOfAvailablePositions){
        boolean moveNotOk = true;
        ArrayList<Integer> position = new ArrayList<Integer>();
        input = new Scanner(System.in);

        do {


            System.out.println("\nInsert x and y separated by space");
            if(!listOfAvailablePositions.isEmpty()) {
                System.out.println("The position must be one of the following:");
                for(int i = 0; i < listOfAvailablePositions.size(); i+=2)
                    System.out.println("[" + listOfAvailablePositions.get(i) + " " + listOfAvailablePositions.get(i+1) + "]");
            }

            String s = input.nextLine();

            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 4; j++) {
                    String app = "" + i + " " + j;
                    if (app.equals(s)) {
                        moveNotOk = false;
                        position.clear();
                        position.add(Integer.parseInt(s.split(" ")[0]));
                        position.add(Integer.parseInt(s.split(" ")[1]));
                    }
                }
            }

            if(!suspended && !listOfAvailablePositions.isEmpty()){
                for (int i = 0; i < listOfAvailablePositions.size(); i = i + 2)
                    if (listOfAvailablePositions.get(i).equals(position.get(0)) && listOfAvailablePositions.get(i + 1).equals(position.get(1)))
                        return position;


            }

            if (moveNotOk && !suspended)
                System.out.println("Try again!");

        } while (moveNotOk  || !listOfAvailablePositions.isEmpty());

        return position;
    }

    /**
     * Method needed for tool card n.1
     * @return array list containing the die chosen and a value to see if he wants to increase or decrease its value
     */
    @Override
    public ArrayList<Integer> getIncrementedValue() {
        ArrayList<Integer> dieAndDecision = new ArrayList<>();
        boolean moveNotOk1, moveNotOk2;
        input = new Scanner(System.in);


        do {
            moveNotOk1 = true;
            moveNotOk2 = true;

            System.out.println("\nInsert idDie from DiceArena to change followed by 1 to increase value its value or by 0 to decrease");
            String s = input.nextLine();
            String[] parts = s.split(" ");

            if(parts.length == 2) {
                for (int i = 0; i < 9; i++)
                    if (parts[0].equals(Integer.toString(i)))
                        moveNotOk1 = false;

                if (parts[1].equals("1") || parts[1].equals("0"))
                    moveNotOk2 = false;
            }

            if(!suspended){
                if(moveNotOk1 || moveNotOk2){
                    System.out.println("Try again!");
                } else {
                    dieAndDecision.add(Integer.parseInt(parts[0]));
                    dieAndDecision.add(Integer.parseInt(parts[1]));
                }
            }



        }
        while((moveNotOk1 || moveNotOk2) && !suspended);

        return dieAndDecision;

    }

    /**
     * Method needed for pattern card so that the player can select one of the dice from the dice arena
     * @return die chosen
     */
    @Override
    public Integer getDieFromDiceArena(){
        input = new Scanner(System.in);
        boolean moveNotOk;
        int idDie = -1;

        do{
            moveNotOk = true;

            System.out.println("\nInsert idDie from DiceArena to use");
            String s = input.nextLine();

            for(int i = 0; i < 9; i++){
                if(s.equals(Integer.toString(i))) {
                    moveNotOk = false;
                    idDie = Integer.parseInt(s);
                }
            }

            if(!suspended && moveNotOk)
                System.out.println("Try Again!");

        } while(moveNotOk && !suspended);

        return idDie;


    }

    /**
     * Method needed for tool cards
     * @return array list containing the die chosen by the player and in which round it appears
     */
    @Override
    public ArrayList<Integer> getDieFromRoundTrack(){
        boolean moveNotOk;
        ArrayList<Integer> idDie = new ArrayList<>();
        input = new Scanner(System.in);

        do{
            moveNotOk = true;

            System.out.println("\nInsert idRound followed by idDie");
            String s = input.nextLine();
            String[] parts = s.split(" ");

            if(parts.length == 2) {
                for (int i = 1; i < 11; i++) {
                    for (int j = 0; j < 9; j++) {
                        if (parts[0].equals(Integer.toString(i)) && parts[1].equals(Integer.toString(j))) {
                            moveNotOk = false;
                            idDie.add(Integer.parseInt(parts[0]) - 1);
                            idDie.add(Integer.parseInt(parts[1]));
                        }
                    }
                }
            }

            if(!suspended && moveNotOk)
                System.out.println("Try Again!");

        } while(moveNotOk && !suspended);

        return idDie;

    }

    /**
     * Method needed for pattern card so that the player can choose which value to give to the die
     * @return value chosen by the player
     */
    @Override
    public Integer getValueForDie(){
        boolean moveNotOk;
        int value = -1;
        input = new Scanner(System.in);

        do{
            moveNotOk = true;

            System.out.println("\nInsert value to assign to Die");
            String s = input.nextLine();

            for(int i = 1; i < 7; i++){
                if(s.equals(Integer.toString(i))) {
                    moveNotOk = false;
                    value = Integer.parseInt(s);
                }
            }

            if(!suspended && moveNotOk)
                System.out.println("Try Again!");

        } while(moveNotOk && !suspended);

        return value;

    }

    @Override
    public Integer askForPatternCard(String s) {
        return null;
    }


}
