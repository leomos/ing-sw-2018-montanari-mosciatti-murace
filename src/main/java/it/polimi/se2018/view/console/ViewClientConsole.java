package it.polimi.se2018.view.console;

import it.polimi.se2018.model.GamePhase;
import it.polimi.se2018.model.events.*;
import it.polimi.se2018.network.server.ServerInterface;
import it.polimi.se2018.view.ViewClient;

import java.util.ArrayList;
import java.util.Scanner;

import static it.polimi.se2018.model.GamePhase.GAMEPHASE;

public class ViewClientConsole extends ViewClient {

    private int idClient;

    private boolean isMyTurn = false;

    private GamePhase gamePhase = GamePhase.SETUPPHASE;

    private ViewClientConsolePrint viewClientConsolePrint;

    ServerInterface serverInterface;

    public ViewClientConsole( ){
    }


    public void update(ModelChangedMessage message){
        if(message instanceof ModelChangedMessageConnected){
            this.idClient = ((ModelChangedMessageConnected) message).getIdClient();
            viewClientConsolePrint = new ViewClientConsoleSetup(this.idClient);
        }
        else if(message instanceof ModelChangedMessageMoveFailed){
            if(((ModelChangedMessageMoveFailed) message).getPlayer().equals(Integer.toString(idClient)))
                System.out.println(((ModelChangedMessageMoveFailed) message).getErrorMessage());
        }
        else if(message instanceof ModelChangedMessageRefresh) {
            if (((ModelChangedMessageRefresh) message).getGamePhase() != gamePhase) {
                gamePhase = ((ModelChangedMessageRefresh) message).getGamePhase(); //cambia anche per end game
                if(gamePhase == GAMEPHASE)
                    viewClientConsolePrint = new ViewClientConsoleGame(this.idClient);
            }else {
                viewClientConsolePrint.print();
                if(((ModelChangedMessageRefresh) message).getIdPlayerPlaying() != null)
                    if(((ModelChangedMessageRefresh) message).getIdPlayerPlaying().equals(Integer.toString(idClient)))
                        isMyTurn = true;
                    else
                        isMyTurn = false;

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

    public PlayerMessageSetup askForPatternCard()  {
        Scanner input = new Scanner(System.in);
        boolean moveOk = true;
        do {
            System.out.println("\nChoose a PatternCard by selecting one of the PatternCardId");

            String s = input.nextLine();

            for(int i = 0; i < 24; i++){
                String app = "" + i;
                if(app.equals(s)) {
                    int idPatternCard = Integer.parseInt(s);
                    moveOk = false;
                    return new PlayerMessageSetup(idClient, idPatternCard);
                }
            }

            if(moveOk)
                System.out.println("Try Again!");
        }
        while(moveOk);

        return null;
    }

    public PlayerMessage askForMove(){
        if(isMyTurn) {
            boolean moveOk = true;

            do{

                System.out.println("\n\nIt's your turn");
                System.out.println("/help: get List of moves");
                Scanner input = new Scanner(System.in);
                String s = input.nextLine();
                String[] parts = s.split(" ");

                if(s.equals("/help")) {
                    System.out.println("\nset + DieId + x_position + y_position  -> to position a die from the dice arena to the patterncard\n" +
                            "use + toolCardID -> to use the toolCard\nend  -> to end the turn");
                    moveOk = false;
                }

                if(parts.length == 4) {
                    for (int i = 0; i < 89; i++)
                        for (int j = 0; j < 5; j++)
                            for(int k = 0; k < 4; k++){
                                String app = "set " + i + " " + j + " " + k;
                                if (s.equals(app)) {
                                    moveOk = false;
                                    int idDie = Integer.parseInt(parts[1]);
                                    int x = Integer.parseInt(parts[2]);
                                    int y = Integer.parseInt(parts[3]);
                                    return new PlayerMessageDie(idClient, idDie, x, y);
                                }
                            }
                }

                if(parts.length == 2){
                    for(int i = 1; i < 13; i++){
                        String app = "use " + i;
                        if(s.equals(app)){
                            int idToolCard = Integer.parseInt(parts[1]);
                            moveOk = false;
                            return new PlayerMessageToolCard(idClient, idToolCard);
                        }
                    }
                }

                if (s.equals("end")) {
                    moveOk = false;
                    return new PlayerMessageEndTurn(idClient);
                }

                if(moveOk == true)
                    System.out.println("Try Again!");
            }
            while(moveOk);
        }
        return null;
    }

    public ArrayList<Integer> getPositionInPatternCard(){
        if(isMyTurn){
            ArrayList<Integer> position = new ArrayList<Integer>();
            boolean moveOk = true;

            do {
                System.out.println("\nInsert position on PatternCard separated by a space");

                Scanner input = new Scanner(System.in);
                String s = input.nextLine();

                for(int i = 0; i < 5; i++) {
                    for (int j = 0; j < 4; j++) {
                        String app = "" + i + " " + j;
                        if (app.equals(s)) {
                            moveOk = false;
                            position.add(Integer.parseInt(s.split(" ")[0]));
                            position.add(Integer.parseInt(s.split(" ")[1]));
                        }
                    }
                }

                if(moveOk)
                    System.out.println("Try Again!");
            }
            while(moveOk);

            return position;
        }else
            return null;
    }

    public int getDieFromPatternCard(){
        int idDie = -1;

        System.out.println("\nInsert ID die");
        Scanner input = new Scanner(System.in);
        String s = input.nextLine();
        idDie = Integer.parseInt(s);

        return idDie;
    }

    public int getDieFromRoundTrack(){
        int dieId = -1;

        System.out.println("\nInsert ID die");
        Scanner input = new Scanner(System.in);
        String s = input.nextLine();
        dieId = Integer.parseInt(s);


        return dieId;
    }

    public boolean getIncrementedValue(){
        boolean i = false;

        System.out.println("\nInsert 1 to increment value, 0 to decrement");
        Scanner input = new Scanner(System.in);
        String s = input.nextLine();
        int k = Integer.parseInt(s);
        if(k == 1)
            i = true;
        else
            i = false;

        return i;
    }
}