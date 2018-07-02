package it.polimi.se2018.view.gui;

import it.polimi.se2018.model.GamePhase;
import it.polimi.se2018.model.events.*;
import it.polimi.se2018.view.ViewClient;

import java.rmi.RemoteException;

import static it.polimi.se2018.model.GamePhase.*;

public class SwingMainView extends ViewClient {

    private int idClient;

    SwingPhase swingPhase;

    private int idPlayerPlaying;

    private boolean canIPlay = true;

    private GamePhase gamePhase;

    private boolean clientSuspended = false;

    public SwingMainView(){
    }

    public void setIdClient(int idClient){
        this.idClient = idClient;
    }

    @Override
    public synchronized void update(ModelChangedMessage message){
        if(message instanceof ModelChangedMessageMoveFailed){
            if(((ModelChangedMessageMoveFailed) message).getPlayer().equals(Integer.toString(idClient))) {
                new MoveFailedFrame(((ModelChangedMessageMoveFailed) message).getErrorMessage());
            }
        } else if(message instanceof ModelChangedMessageNewEvent){
            if(((ModelChangedMessageNewEvent) message).getPlayer().equals(Integer.toString(idClient))){
                System.out.println("NEW EVENT: " + ((ModelChangedMessageNewEvent) message).getMessage());
            }
        }
        else if(message instanceof ModelChangedMessageChangeGamePhase) {
            gamePhase = ((ModelChangedMessageChangeGamePhase) message).getGamePhase();
            if (gamePhase == SETUPPHASE) {
                swingPhase = new PatternCardsFrame(this.idClient);
                swingPhase.setServerInterface(this.serverInterface);
            }
            if (gamePhase == GAMEPHASE) {
                swingPhase = new ViewClientGUIGame(this.idClient);
                swingPhase.setServerInterface(this.serverInterface);
            }
            if (gamePhase == ENDGAMEPHASE)
                new EndGameFrame(this.idClient);
        }
        else if(message instanceof ModelChangedMessageRefresh){
            swingPhase.print();
            if(((ModelChangedMessageRefresh) message).getIdPlayerPlaying() != null) {
                swingPhase.update(message);
                idPlayerPlaying = Integer.parseInt(((ModelChangedMessageRefresh) message).getIdPlayerPlaying());
                if(idPlayerPlaying == idClient && canIPlay) {
                    new TurnFrame();
                }
            }

        } else if(message instanceof ModelChangedMessagePlayerAFK){
            if(((ModelChangedMessagePlayerAFK) message).getPlayer().equals(Integer.toString(idClient))) {
                System.out.println(((ModelChangedMessagePlayerAFK) message).getMessage());

                clientSuspended = true;

            }
        }

        else {
            swingPhase.update(message);
        }
    }

    @Override
    public Integer askForPatternCard()  {
        try {
            serverInterface.notify(new PlayerMessageSetup(idClient, swingPhase.askForPatternCard()));
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return null;
    }

}
