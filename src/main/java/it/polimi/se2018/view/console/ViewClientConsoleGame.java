package it.polimi.se2018.view.console;

import it.polimi.se2018.model.events.*;

import java.util.ArrayList;
import java.util.Scanner;

public class ViewClientConsoleGame extends ViewClientConsolePrint {

    private int idClient;

    private ArrayList<String> idPlayers = new ArrayList<String>();

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
    public void update(ModelChangedMessage message) {

        if (message instanceof ModelChangedMessagePatternCard){
                idPlayers.add(((ModelChangedMessagePatternCard) message).getIdPlayer());
                patternCards.add((ModelChangedMessagePatternCard) message);
                diceOnPatternCards.add(null);
        }
        else if (message instanceof ModelChangedMessageDiceOnPatternCard){
            int i = idPlayers.indexOf(((ModelChangedMessageDiceOnPatternCard) message).getIdPlayer());
            diceOnPatternCards.remove(i);
            diceOnPatternCards.add(i, (ModelChangedMessageDiceOnPatternCard) message);
        }
        else if(message instanceof ModelChangedMessagePrivateObjective) {
            if (((ModelChangedMessagePrivateObjective) message).getIdPlayer().equals(Integer.toString(idClient)))
                privateObjective = ((ModelChangedMessagePrivateObjective) message);
        }
        else if(message instanceof ModelChangedMessagePublicObjective)
            publicObjectives.add((ModelChangedMessagePublicObjective)message);
        else if(message instanceof ModelChangedMessageToolCard) {
            if(toolCards.size() == 3) {
                for(int i = 0; i < 3; i++)
                    if(toolCards.get(i).getIdToolCard().equals(((ModelChangedMessageToolCard) message).getIdToolCard())) {
                        toolCards.remove(i);
                        toolCards.add(i, (ModelChangedMessageToolCard) message);
                    }
            } else {
                toolCards.add((ModelChangedMessageToolCard) message);
            }
        }
        else if(message instanceof ModelChangedMessageDiceArena)
            diceArena = ((ModelChangedMessageDiceArena)message);
        else if(message instanceof ModelChangedMessageRound) {
            int i = Integer.parseInt(((ModelChangedMessageRound) message).getIdRound());
            if(i >= roundTrack.size())
                roundTrack.add((ModelChangedMessageRound) message);
            else {
                roundTrack.remove(i);
                roundTrack.add(i, (ModelChangedMessageRound) message);
            }
        }
        else if(message instanceof ModelChangedMessageTokensLeft)
            if(((ModelChangedMessageTokensLeft) message).getIdPlayer().equals(Integer.toString(idClient)))
                tokensLeft = (ModelChangedMessageTokensLeft) message;

    }

    @Override
    public void print() {

        System.out.println("/°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°/");
        System.out.println("/°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°/");
        System.out.println("/°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°//°-°/");

        int myPatternCardId = -1;
        for (int i = 0; i < patternCards.size(); i++)
            if(!(Integer.toString(idClient).equals(idPlayers.get(i))))
                printPatternCard(idPlayers.get(i), patternCards.get(i), diceOnPatternCards.get(i));
            else
                myPatternCardId = i;

        System.out.println("\nYour PatternCard");
        printPatternCard(idPlayers.get(myPatternCardId), patternCards.get(myPatternCardId), diceOnPatternCards.get(myPatternCardId));

        printPrivateObjective(privateObjective);

        System.out.println("You have left " + tokensLeft.getTokensLeft() + " tokens to use on ToolCards");

        for (int i = 0; i < roundTrack.size(); i++)
            printRoundTrack(roundTrack.get(i));
        int app = roundTrack.size() + 1;
        System.out.println("\n\nCURRENT ROUND IS:\t" + app );

        printDiceArena(diceArena);

        for (int i = 0; i < 3; i++)
            printPublicObjective(publicObjectives.get(i));

        for (int i = 0; i < 3; i++)
            printToolCards(toolCards.get(i));

        System.out.println("\n\n");

    }

    public ArrayList<Integer> getPositionInPatternCard(){
        ArrayList<Integer> position = new ArrayList<Integer>();
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

            if(moveOk1 || moveOk2)
                System.out.println("Try Again!");
        }
        while(moveOk1 || moveOk2);

        return position;
    }

    public ArrayList<Integer> getSinglePositionInPatternCard(ArrayList<Integer> listOfAvailablePositions){
        boolean moveOk = true;
        ArrayList<Integer> position = new ArrayList<Integer>();

        do {


            System.out.println("\nInsert x and y separated by space");
            if(listOfAvailablePositions.size() != 0)
                System.out.println("The position must be one of the following" + listOfAvailablePositions);
            Scanner input = new Scanner(System.in);
            String s = input.nextLine();
            String[] parts = s.split(" ");

            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 4; j++) {
                    String app = "" + i + " " + j;
                    if (app.equals(s)) {
                        moveOk = false;
                        position.clear();
                        position.add(Integer.parseInt(s.split(" ")[0]));
                        position.add(Integer.parseInt(s.split(" ")[1]));
                    }
                }
            }

            if(listOfAvailablePositions.size() != 0) {
                for (int i = 0; i < listOfAvailablePositions.size(); i = i + 2)
                    if (listOfAvailablePositions.get(i).equals(position.get(0)) && listOfAvailablePositions.get(i + 1).equals(position.get(1)))
                        return position;


            }

            if (moveOk)
                System.out.println("Try again!");

        } while (moveOk  || listOfAvailablePositions.size() != 0);

        return position;
    }

    public ArrayList<Integer> getIncrementedValue() {

        ArrayList<Integer> dieAndDecision = new ArrayList<Integer>();
        boolean moveOk1, moveOk2;

        do {
            moveOk1 = true;
            moveOk2 = true;

            System.out.println("\nInsert idDie from DiceArena to change followed by 1 to increase value its value or by 0 to decrease");
            Scanner input = new Scanner(System.in);
            String s = input.nextLine();
            String[] parts = s.split(" ");

            if(parts.length == 2) {
                for (int i = 0; i < 90; i++)
                    if (parts[0].equals(Integer.toString(i)))
                        moveOk1 = false;

                if (parts[1].equals("1") || parts[1].equals("0"))
                    moveOk2 = false;
            }

            if(moveOk1 || moveOk2){
                System.out.println("Try again!");
            } else {
                dieAndDecision.add(Integer.parseInt(parts[0]));
                dieAndDecision.add(Integer.parseInt(parts[1]));
            }



        }
        while(moveOk1 || moveOk2);

        return dieAndDecision;

    }

    public Integer getDieFromDiceArena(){

        boolean moveOk;
        int idDie = -1;

        do{
            moveOk = true;

            System.out.println("\nInsert idDie from DiceArena to use");
            Scanner input = new Scanner(System.in);
            String s = input.nextLine();

            for(int i = 0; i < 90; i++){
                if(s.equals(Integer.toString(i))) {
                    moveOk = false;
                    idDie = Integer.parseInt(s);
                }
            }

            if(moveOk)
                System.out.println("Try Again!");

        } while(moveOk);

        return idDie;


    }

    public Integer getDieFromRoundTrack(){
        boolean moveOk;
        int idDie = -1;

        do{
            moveOk = true;

            System.out.println("\nInsert idDie from RoundTruck to use");
            Scanner input = new Scanner(System.in);
            String s = input.nextLine();

            for(int i = 0; i < 90; i++){
                if(s.equals(Integer.toString(i))) {
                    moveOk = false;
                    idDie = Integer.parseInt(s);
                }
            }

            if(moveOk)
                System.out.println("Try Again!");

        } while(moveOk);

        return idDie;

    }

    public Integer getValueForDie(){
        boolean moveOk;
        int value = -1;

        do{
            moveOk = true;

            System.out.println("\nInsert value to assign to Die");
            Scanner input = new Scanner(System.in);
            String s = input.nextLine();

            for(int i = 1; i < 7; i++){
                if(s.equals(Integer.toString(i))) {
                    moveOk = false;
                    value = Integer.parseInt(s);
                }
            }

            if(moveOk)
                System.out.println("Try Again!");

        } while(moveOk);

        return value;

    }

    @Override
    public Integer askForPatternCard() {
        return null;
    }


}
