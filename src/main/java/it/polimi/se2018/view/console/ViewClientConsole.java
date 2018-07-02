package it.polimi.se2018.view.console;

import it.polimi.se2018.model.GamePhase;
import it.polimi.se2018.model.events.*;
import it.polimi.se2018.view.ViewClient;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import static it.polimi.se2018.model.GamePhase.*;

public class ViewClientConsole extends ViewClient  {

    private int idClient;

    private int idPlayerPlaying;

    private boolean canIPlay = true;

    private GamePhase gamePhase;

    private boolean clientSuspended = false;

    private boolean hasChoosePatternCard = false;

    private ViewClientConsolePrint viewClientConsolePrint;

    public ViewClientConsole(){
    }

    public void setIdClient(int idClient){
        this.idClient = idClient;
    }

    @Override
    public synchronized void update(ModelChangedMessage message){

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
        else if(message instanceof ModelChangedMessageChangeGamePhase) {
            gamePhase = ((ModelChangedMessageChangeGamePhase) message).getGamePhase();
            if(gamePhase == SETUPPHASE)
                viewClientConsolePrint = new ViewClientConsoleSetup(this.idClient);
            if (gamePhase == GAMEPHASE)
                viewClientConsolePrint = new ViewClientConsoleGame(this.idClient);
            if (gamePhase == ENDGAMEPHASE)
                viewClientConsolePrint = new ViewClientConsoleEndGame(this.idClient);

        }
        else if (message instanceof ModelChangedMessageRefresh){
            viewClientConsolePrint.print();
            if(gamePhase == SETUPPHASE) {

                ;

            }
            if(((ModelChangedMessageRefresh) message).getIdPlayerPlaying() != null) {
                idPlayerPlaying = Integer.parseInt(((ModelChangedMessageRefresh) message).getIdPlayerPlaying());
                if(idPlayerPlaying == idClient && canIPlay) {
                    System.out.println("It's your turn");
                    System.out.println("/help: get List of moves");
                }
            }

        } else if(message instanceof ModelChangedMessagePlayerAFK){
            if(((ModelChangedMessagePlayerAFK) message).getPlayer().equals(Integer.toString(idClient))) {
                System.out.println(((ModelChangedMessagePlayerAFK) message).getMessage());
                clientSuspended = true;
                viewClientConsolePrint.setSuspended(true);
            } else {
                System.out.println("\nPlayer " + ((ModelChangedMessagePlayerAFK) message).getPlayer() + " is now suspended");
            }
        }
        else {
            viewClientConsolePrint.update(message);
        }
    }


    public void run(){
        boolean c = true;

        do {

            Scanner input = new Scanner(System.in);
            String app = input.nextLine();

            if (this.gamePhase.equals(SETUPPHASE)) {

                if (!clientSuspended) {
                    int chosenPatternCard = viewClientConsolePrint.askForPatternCard(app);
                    if (chosenPatternCard != -1) {

                        try {
                            serverInterface.notify(new PlayerMessageSetup(idClient, chosenPatternCard));
                            hasChoosePatternCard = true;
                            System.out.println("Wait for other players to choose their pattern cards!");
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    unSuspend();
                }
            } else {
                System.out.println("Wait for game to start and game phasz = " + gamePhase);
            }
        }
        while(gamePhase != SETUPPHASE && !hasChoosePatternCard);




        while(c) {


            boolean moveOk;

            do{

                moveOk = true;
                if(canIPlay) {

                    Scanner mainInput = new Scanner(System.in);
                    String s = mainInput.nextLine();
                    if (gamePhase == GAMEPHASE) {

                        if (!clientSuspended) {
                            if (idPlayerPlaying == idClient) {

                                PlayerMessage message = viewClientConsolePrint.getMainMove(s);

                                if (message.getPlayerId() != -1)
                                    try {
                                        serverInterface.notify(message);
                                    } catch (RemoteException e) {
                                        handleDisconnection();
                                    }
                                else {
                                    moveOk = false;
                                    System.out.println("Try again! /help");
                                }

                            } else {
                                System.out.println("It's player  " + idPlayerPlaying + " turn, not yours!");
                            }
                        } else {
                            unSuspend();
                        }
                    } else
                        System.out.println("Wait for other players to choose their pattern cards!");

                }

                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            while (moveOk);

        }




    }

    public void unSuspend(){
        System.out.println("You are not suspended anymore!");
        clientSuspended = false;
        viewClientConsolePrint.setSuspended(false);
        try {
            serverInterface.notify(new PlayerMessageNotAFK(idClient));
        } catch (RemoteException e) {
            handleDisconnection();
        }
    }

    @Override
    public Boolean block(){

        if(idPlayerPlaying == idClient)
            canIPlay = false;
        return true;
    }

    @Override
    public Boolean free(){
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

            ArrayList<Integer> returnValues = viewClientConsolePrint.getPositionInPatternCard();

            if(returnValues.isEmpty())
                unSuspend();
            return returnValues;

        }
        return null;
    }

    @Override
    public ArrayList<Integer> getSinglePositionInPatternCard(ArrayList<Integer> listOfAvailablePositions){
        if(idPlayerPlaying == idClient) {

            ArrayList<Integer> returnValues = viewClientConsolePrint.getSinglePositionInPatternCard(listOfAvailablePositions);

            if(returnValues.isEmpty())
                unSuspend();
            return returnValues;

        }
        return null;
    }

    @Override
    public ArrayList<Integer> getIncrementedValue() {
        if (idPlayerPlaying == idClient) {

            ArrayList<Integer> returnValues = viewClientConsolePrint.getIncrementedValue();

            if(returnValues.isEmpty())
                unSuspend();
            return returnValues;

        }
        return null;
    }

    @Override
    public Integer getDieFromDiceArena(){
        if(idPlayerPlaying == idClient) {

            int returnValues = viewClientConsolePrint.getDieFromDiceArena();

            if(returnValues == -1)
                unSuspend();
            return returnValues;

        }
        return null;
    }

    @Override
    public ArrayList<Integer> getDieFromRoundTrack(){
        if(idPlayerPlaying == idClient) {

            ArrayList<Integer> returnValues = viewClientConsolePrint.getDieFromRoundTrack();

            if(returnValues.isEmpty())
                unSuspend();
            return returnValues;

        }
        return null;
    }

    @Override
    public Integer getValueForDie(){
        if(idPlayerPlaying == idClient) {

            int returnValues = viewClientConsolePrint.getValueForDie();

            if(returnValues == -1)
                unSuspend();
            return returnValues;

        }
        return null;

    }

    @Override
    public ArrayList<Integer> getDoublePositionInPatternCard(){
        if(idPlayerPlaying == idClient) {

            ArrayList<Integer> returnValues = viewClientConsolePrint.getDoublePositionInPatternCard();

            if(returnValues.isEmpty())
                unSuspend();
            return returnValues;

        }
        return null;
    }

    @Override
    public void handleDisconnection() {
        System.out.println("Disconnesso!");
        this.serverInterface = null;
        this.executor.shutdownNow();
        try {
            this.executor.awaitTermination(3, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.out.println("Heartbeat terminato prima del previsto.");
        }
        System.out.println("Heartbeat terminato!");
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("provo a riconnettermi");
        tryToReconnect();
    }

    private void tryToReconnect() {
        this.reconnect(idClient, 0);
        initNewExecutor();
        startHeartbeating(idClient);
    }

}