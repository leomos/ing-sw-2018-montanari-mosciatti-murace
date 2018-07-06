package it.polimi.se2018.view.gui;

import it.polimi.se2018.model.GamePhase;
import it.polimi.se2018.model.events.*;
import it.polimi.se2018.network.visitor.MessageVisitorImplementationViewGui;
import it.polimi.se2018.view.ViewClient;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static it.polimi.se2018.model.GamePhase.*;

public class ViewClientGUI extends ViewClient {

    private int idClient;

    private int idPlayerPlaying;

    private SwingPhase swingPhase;

    private GamePhase gamePhase;

    private MessageVisitorImplementationViewGui messageVisitorImplementationView;

    public ViewClientGUI(String host, int socketPort, int rmiPort, int connectionType){
        super(host, socketPort, rmiPort, 1, connectionType);
        this.messageVisitorImplementationView = new MessageVisitorImplementationViewGui(this);
    }

    public void setIdClient(int idClient){
        this.idClient = idClient;
    }

    public SwingPhase getSwingPhase() {
        return swingPhase;
    }

    @Override
    public synchronized void update(ModelChangedMessage message) {
        message.accept(messageVisitorImplementationView);
    }

    public void update(ModelChangedMessageMoveFailed messageMoveFailed) {
        if(messageMoveFailed.getPlayer() == idClient) {
            new MoveFailedFrame(messageMoveFailed.getErrorMessage());
        }
    }

    public void update(ModelChangedMessageNewEvent modelChangedMessageNewEvent){
        if(modelChangedMessageNewEvent.getPlayer() == idClient && this.gamePhase == GAMEPHASE) {
            new NewEventFrame(modelChangedMessageNewEvent.getMessage());
        }
    }

    public void update(ModelChangedMessageChangeGamePhase modelChangedMessageChangeGamePhase) {
        gamePhase = modelChangedMessageChangeGamePhase.getGamePhase();
        if(gamePhase == SETUPPHASE) {
            swingPhase = new ViewClientGUISetup(this.idClient);
            swingPhase.setServerInterface(this.serverInterface);
            swingPhase.setViewClientGUI(this);
        }
        if (gamePhase == GAMEPHASE) {
            swingPhase.close();
            swingPhase = new ViewClientGUIGame(this.idClient);
            swingPhase.setServerInterface(this.serverInterface);
            swingPhase.setViewClientGUI(this);

        }
        if (gamePhase == ENDGAMEPHASE) {
                swingPhase.close();
                swingPhase = new EndGameFrame(this.idClient);
                swingPhase.setViewClientGUI(this);

        }
    }

    public void update(ModelChangedMessageRefresh modelChangedMessageRefresh) {
        if(modelChangedMessageRefresh.getIdPlayerPlaying() != null && modelChangedMessageRefresh.getIdPlayerPlaying() != idPlayerPlaying) {
            idPlayerPlaying = modelChangedMessageRefresh.getIdPlayerPlaying();
        }
        swingPhase.update(modelChangedMessageRefresh);
        swingPhase.print();

    }

    public void update(ModelChangedMessagePlayerAFK modelChangedMessagePlayerAFK) {
        if (modelChangedMessagePlayerAFK.getPlayer()==idClient && gamePhase == GAMEPHASE) {
            SuspendFrame frame;
            swingPhase.close();
            frame = new SuspendFrame();
            frame.addWindowListener(new WindowListener() {
                @Override
                public void windowOpened(WindowEvent e) {

                }

                @Override
                public void windowClosing(WindowEvent e) {

                }

                @Override
                public void windowClosed(WindowEvent e) {
                    PlayerMessageNotAFK messageNotAFK = new PlayerMessageNotAFK(idClient);
                    try {
                        serverInterface.notify(messageNotAFK);
                    } catch (RemoteException e1) {
                        e1.printStackTrace();
                    }
                }

                @Override
                public void windowIconified(WindowEvent e) {

                }

                @Override
                public void windowDeiconified(WindowEvent e) {

                }

                @Override
                public void windowActivated(WindowEvent e) {

                }

                @Override
                public void windowDeactivated(WindowEvent e) {

                }
            });
        }
        else
            new MoveFailedFrame("Player " + modelChangedMessagePlayerAFK.getPlayer() + " is now suspended");

    }

    public void update(ModelChangedMessageConnected modelChangedMessageConnected) {
        if(modelChangedMessageConnected.getIdClient() != idClient)
            new PlayerReconnectedFrame(modelChangedMessageConnected.getIdClient());
    }

    /**
     * Method needed for tool cards
     * @return player choice or empty array list if the player's turn finished while he was choosing the values
     */
    @Override
    public ArrayList<Integer> getPositionInPatternCard(){
        if(idPlayerPlaying == idClient){

            return swingPhase.getPositionInPatternCard();

        }
        return null;
    }

    /**
     * Method needed for tool cards
     * @return player choice or empty array list if the player's turn finished while he was choosing the values
     */
    @Override
    public ArrayList<Integer> getSinglePositionInPatternCard(ArrayList<Integer> listOfAvailablePositions){
        if(idPlayerPlaying == idClient) {

            return swingPhase.getSinglePositionInPatternCard(listOfAvailablePositions);

        }
        return null;
    }

    /**
     * Method needed for tool cards
     * @return player choice or empty array list if the player's turn finished while he was choosing the values
     */
    @Override
    public ArrayList<Integer> getIncrementedValue() {
        if (idPlayerPlaying == idClient) {

            return swingPhase.getIncrementedValue();

        }
        return null;
    }

    /**
     * Method needed for tool cards
     * @return player choice or -1 if the player's turn finished while he was choosing the values
     */
    @Override
    public Integer getDieFromDiceArena(){
        if(idPlayerPlaying == idClient) {

            return swingPhase.getDieFromDiceArena();

        }
        return null;
    }

    /**
     * Method needed for tool cards
     * @return player choice or empty array list if the player's turn finished while he was choosing the values
     */
    @Override
    public ArrayList<Integer> getDieFromRoundTrack(){
        if(idPlayerPlaying == idClient) {

            return  swingPhase.getDieFromRoundTrack();

        }
        return null;
    }

    /**
     * Method needed for tool cards
     * @return player choice or -1 if the player's turn finished while he was choosing the values
     */
    @Override
    public Integer getValueForDie(){
        if(idPlayerPlaying == idClient) {

            return swingPhase.getValueForDie();

        }
        return null;

    }

    /**
     * Method needed for tool card
     * @return player choice or empty array list if the player's turn finished while he was choosing the values
     */
    @Override
    public ArrayList<Integer> getDoublePositionInPatternCard(){
        if(idPlayerPlaying == idClient) {

            return swingPhase.getDoublePositionInPatternCard();


        }
        return null;
    }

    @Override
    public void handleDisconnection() {
        swingPhase.close();
        System.out.println("Disconnesso!");
        this.serverInterface = null;
        this.executor.shutdownNow();
        try {
            this.executor.awaitTermination(3, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.out.println("Heartbeat terminato prima del previsto.");
        }
        System.out.println("Heartbeat terminato!");

        boolean c = true;
        while(c) {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("provo a riconnettermi");
            c = tryToReconnect();
        }
    }

    private boolean tryToReconnect() {
        if(this.reconnect(idClient, this.connectionType)) {
            initNewExecutor();
            startHeartbeating(idClient);
            return false;
        } else {
            System.out.println("Room chiusa");
            return true;
        }
    }

}
