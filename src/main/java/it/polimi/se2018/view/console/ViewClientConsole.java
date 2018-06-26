package it.polimi.se2018.view.console;

import it.polimi.se2018.model.GamePhase;
import it.polimi.se2018.model.events.*;
import it.polimi.se2018.view.ViewClient;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import static it.polimi.se2018.model.GamePhase.ENDGAMEPHASE;
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
                System.out.println("ERROR: " + ((ModelChangedMessageMoveFailed) message).getErrorMessage());
                System.out.println("\n\nTry again");
            }
        } else if(message instanceof ModelChangedMessageNewEvent){
            if(((ModelChangedMessageNewEvent) message).getPlayer().equals(Integer.toString(idClient))){
                System.out.println("NEW EVENT: " + ((ModelChangedMessageNewEvent) message).getMessage());
            }
        }
        else if(message instanceof ModelChangedMessageRefresh) {
            if (((ModelChangedMessageRefresh) message).getGamePhase() != gamePhase) {
                gamePhase = ((ModelChangedMessageRefresh) message).getGamePhase();
                if(gamePhase == GAMEPHASE)
                    viewClientConsolePrint = new ViewClientConsoleGame(this.idClient);
                if(gamePhase == ENDGAMEPHASE)
                    viewClientConsolePrint = new ViewClientConsoleEndGame(this.idClient);
            }else {
                viewClientConsolePrint.print();
                if(((ModelChangedMessageRefresh) message).getIdPlayerPlaying() != null) {
                    idPlayerPlaying = Integer.parseInt(((ModelChangedMessageRefresh) message).getIdPlayerPlaying());
                    if(idPlayerPlaying == idClient && canIPlay) {
                        System.out.println("It's your turn");
                        System.out.println("/help: get List of moves");
                    }
                }
            }

        } else {
            viewClientConsolePrint.update(message);
        }
    }

    public String askForName(){

        System.out.println("\nName");
        Scanner input = new Scanner(System.in);
        String s = input.nextLine();

        return s;

    }

    public Integer askForPatternCard()  {
        return viewClientConsolePrint.askForPatternCard();
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
                        boolean moveOk;
                        do {
                            moveOk = true;

                            Scanner input = new Scanner(System.in);
                            String s = input.nextLine();
                            String[] parts = s.split(" ");

                            if (idPlayerPlaying == idClient) {

                                if (s.equals("/help")) {
                                    System.out.println("\nset + DieId + x_position + y_position  -> to position a die from the dice arena to the patterncard\n" +
                                            "use + toolCardID -> to use the toolCard\nend  -> to end the turn");
                                    moveOk = false;
                                }

                                if (parts.length == 4) {
                                    for (int i = 0; i < 9; i++)
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


                            } else {
                                System.out.println("\n\nIt's player  " + idPlayerPlaying + " turn, not yours");
                            }
                        }
                        while (moveOk);
                    }

                }

                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }

    }

    public Boolean block(){
        if(idPlayerPlaying == idClient)
            canIPlay = false;
        return true;
    }

    public Boolean free(){
        if (idPlayerPlaying == idClient)
            canIPlay = true;
        if(idPlayerPlaying == idClient && canIPlay) {
            System.out.println("It's your turn");
            System.out.println("/help: get List of moves");
        }
        return true;
    }

    @Override
    public ArrayList<Integer> getPositionInPatternCard(){
        if(idPlayerPlaying == idClient){

            return viewClientConsolePrint.getPositionInPatternCard();

        }
        return null;
    }

    @Override
    public ArrayList<Integer> getSinglePositionInPatternCard(ArrayList<Integer> listOfAvailablePositions){
        if(idPlayerPlaying == idClient) {

            return viewClientConsolePrint.getSinglePositionInPatternCard(listOfAvailablePositions);

        }
        return null;
    }

    @Override
    public ArrayList<Integer> getIncrementedValue() {

        if (idPlayerPlaying == idClient) {

            return viewClientConsolePrint.getIncrementedValue();

        }
        return null;
    }

    @Override
    public Integer getDieFromDiceArena(){

        if(idPlayerPlaying == idClient) {

            return viewClientConsolePrint.getDieFromDiceArena();

        }
        return null;
    }

    @Override
    public ArrayList<Integer> getDieFromRoundTrack(){
        if(idPlayerPlaying == idClient) {

            return viewClientConsolePrint.getDieFromRoundTrack();

        }
        return null;
    }

    @Override
    public Integer getValueForDie(){
        if(idPlayerPlaying == idClient) {

            return viewClientConsolePrint.getValueForDie();

        }
        return null;

    }

    public ArrayList<Integer> getDoublePositionInPatternCard(){
        if(idPlayerPlaying == idClient) {

            return viewClientConsolePrint.getDoublePositionInPatternCard();
        }
        return null;
    }
}