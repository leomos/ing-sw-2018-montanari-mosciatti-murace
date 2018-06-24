package it.polimi.se2018.view.console;

import it.polimi.se2018.model.GamePhase;
import it.polimi.se2018.model.events.*;
import it.polimi.se2018.view.ViewClient;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import static it.polimi.se2018.model.GamePhase.GAMEPHASE;

public class ViewClientConsole extends ViewClient implements Runnable {

    private int idClient;

    private int idPlayerPlaying;

    private boolean canIPlay = true;

    private GamePhase gamePhase = GamePhase.SETUPPHASE;

    private ViewClientConsolePrint viewClientConsolePrint;

    public ViewClientConsole(){
    }

    public void setIdClient(int idClient){
        this.idClient = idClient;
        viewClientConsolePrint = new ViewClientConsoleSetup(idClient);
    }


    public void update(ModelChangedMessage message){
        if(message instanceof ModelChangedMessageMoveFailed){
            if(((ModelChangedMessageMoveFailed) message).getPlayer().equals(Integer.toString(idClient))) {
                System.out.println(((ModelChangedMessageMoveFailed) message).getErrorMessage());
                System.out.println("\n\nTry again");
                System.out.println("/help: get List of moves");
            }
        } else if(message instanceof ModelChangedMessageNewEvent){
            if(((ModelChangedMessageNewEvent) message).getPlayer().equals(Integer.toString(idClient))){
                System.out.println(((ModelChangedMessageNewEvent) message).getMessage());
            }
        }
        else if(message instanceof ModelChangedMessageRefresh) {
            if (((ModelChangedMessageRefresh) message).getGamePhase() != gamePhase) {
                gamePhase = ((ModelChangedMessageRefresh) message).getGamePhase(); //cambia anche per end game
                if(gamePhase == GAMEPHASE)
                    viewClientConsolePrint = new ViewClientConsoleGame(this.idClient);
            }else {
                viewClientConsolePrint.print();
                if(((ModelChangedMessageRefresh) message).getIdPlayerPlaying() != null) {
                    idPlayerPlaying = Integer.parseInt(((ModelChangedMessageRefresh) message).getIdPlayerPlaying());
                    if(idPlayerPlaying == idClient) {
                        System.out.println("\n\nIt's your turn");
                        System.out.println("/help: get List of moves");
                    }
                }
            }

        } else {
            viewClientConsolePrint.update(message);
        }
    }

    /*TODO: need to check if the type of the input is correct somehow in all of them*/

    public String askForName(){

        System.out.println("\nname");
        Scanner input = new Scanner(System.in);
        String s = input.nextLine();

        return s;

    }

    public Integer askForPatternCard()  {
        Scanner input = new Scanner(System.in);
        boolean moveOk = true;
        do {
            System.out.println("\nChoose a PatternCard by selecting one of the PatternCardId");

            String s = input.nextLine();

            for(int i = 0; i < 24; i++){
                String app = "" + i;
                if(app.equals(s)) {
                    moveOk = false;
                    return Integer.parseInt(app);
                }
            }

            if(moveOk)
                System.out.println("Try Again!");
        }
        while(moveOk);

        return null;
    }

    public void run(){
        boolean c = true;
        while(c) {

            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(gamePhase == GAMEPHASE) {

                while (c) {

                    try {
                        TimeUnit.SECONDS.sleep(3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(canIPlay) {

                        Scanner input = new Scanner(System.in);
                        String s = input.nextLine();
                        String[] parts = s.split(" ");

                        if (idPlayerPlaying == idClient) {

                            boolean moveOk = true;

                            do {

                                if (s.equals("/help")) {
                                    System.out.println("\nset + DieId + x_position + y_position  -> to position a die from the dice arena to the patterncard\n" +
                                            "use + toolCardID -> to use the toolCard\nend  -> to end the turn");
                                    moveOk = false;
                                }

                                if (parts.length == 4) {
                                    for (int i = 0; i < 89; i++)
                                        for (int j = 0; j < 5; j++)
                                            for (int k = 0; k < 4; k++) {
                                                String app = "set " + i + " " + j + " " + k;
                                                if (s.equals(app)) {
                                                    moveOk = false;
                                                    int idDie = Integer.parseInt(parts[1]);
                                                    int x = Integer.parseInt(parts[2]);
                                                    int y = Integer.parseInt(parts[3]);
                                                    try {
                                                        serverInterface.notify(new PlayerMessageDie(idClient, idDie, x, y));
                                                    } catch (RemoteException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            }
                                }

                                if (parts.length == 2) {
                                    for (int i = 1; i < 13; i++) {
                                        String app = "use " + i;
                                        if (s.equals(app)) {
                                            moveOk = false;
                                            int idToolCard = Integer.parseInt(parts[1]);
                                            try {
                                                serverInterface.notify(new PlayerMessageToolCard(idClient, idToolCard));
                                            } catch (RemoteException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                }

                                if (s.equals("end")) {
                                    moveOk = false;
                                    try {
                                        serverInterface.notify(new PlayerMessageEndTurn(idClient));
                                    } catch (RemoteException e) {
                                        e.printStackTrace();
                                    }
                                }

                                if (moveOk == true) {
                                    System.out.println("Try Again!");
                                    System.out.println("\n\nIt's your turn");
                                    System.out.println("/help: get List of moves");
                                }

                            }
                            while (moveOk);

                        } else {
                            System.out.println("\n\nIt's Player  " + idPlayerPlaying + " turn, not yours");
                        }
                    }

                }
            }
        }

    }

    public void block(){
        if(idPlayerPlaying == idClient)
            canIPlay = false;
    }

    public void free(){
        if (idPlayerPlaying == idClient)
            canIPlay = true;
    }

    public ArrayList<Integer> getPositionInPatternCard(){
        if(idPlayerPlaying == idClient){
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
        }else
            return null;
    }

    public ArrayList<Integer> getSinglePositionInPatternCard(){
        if(idPlayerPlaying == idClient) {
            boolean moveOk = true;
            ArrayList<Integer> position = new ArrayList<Integer>();

            do {


                System.out.println("\nInsert x and y separated by space");
                Scanner input = new Scanner(System.in);
                String s = input.nextLine();
                String[] parts = s.split(" ");

                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 4; j++) {
                        String app = "" + i + " " + j;
                        if (app.equals(s)) {
                            moveOk = false;
                            position.add(Integer.parseInt(s.split(" ")[0]));
                            position.add(Integer.parseInt(s.split(" ")[1]));
                        }
                    }
                }

                if (moveOk)
                    System.out.println("Try again!");

            } while (moveOk);

            return position;
        }

        return null;
    }

    public ArrayList<Integer> getIncrementedValue() {

        if (idPlayerPlaying == idClient) {
            ArrayList<Integer> dieAndDecision = new ArrayList<Integer>();
            boolean moveOk1, moveOk2;

            do {
                moveOk1 = true;
                moveOk2 = true;

                System.out.println("\nInsert idDie from DiceArena to change followed by 1 to increment value itss value or by 0 to decrement");
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

        return null;
    }

    public Integer getDieFromDiceArena(){

        if(idPlayerPlaying == idClient) {
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
        return null;
    }

    public Integer getDieFromRoundTrack(){
        if(idPlayerPlaying == idClient) {
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
        return null;
    }

    public Integer getValueForDie(){
        if(idPlayerPlaying == idClient) {
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
        return null;

    }
}