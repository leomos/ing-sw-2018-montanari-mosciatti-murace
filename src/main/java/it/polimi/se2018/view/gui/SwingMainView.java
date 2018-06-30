package it.polimi.se2018.view.gui;

import it.polimi.se2018.model.GamePhase;
import it.polimi.se2018.model.events.*;
import it.polimi.se2018.view.ViewClient;

import static it.polimi.se2018.model.GamePhase.ENDGAMEPHASE;
import static it.polimi.se2018.model.GamePhase.GAMEPHASE;

public class SwingMainView extends ViewClient {

    private int idClient;

    SwingPhase swingPhase;

    private int idPlayerPlaying;

    private boolean canIPlay = true;

    private GamePhase gamePhase = GamePhase.SETUPPHASE;

    private boolean clientSuspended = false;

    public SwingMainView(){
    }

    public void setIdClient(int idClient){
        this.idClient = idClient;
        swingPhase = new PatternCardsFrame(idClient);
    }

    @Override
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
                    swingPhase = new PatternCardsFrame(this.idClient);
                if(gamePhase == ENDGAMEPHASE) ;
                    //japne = new (this.idClient);
            }else {
                swingPhase.print();
                if(((ModelChangedMessageRefresh) message).getIdPlayerPlaying() != null) {
                    swingPhase.update(message);
                    idPlayerPlaying = Integer.parseInt(((ModelChangedMessageRefresh) message).getIdPlayerPlaying());
                    if(idPlayerPlaying == idClient && canIPlay) {
                        System.out.println("It's your turn");
                        System.out.println("/help: get List of moves");
                    }
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

    public Integer askForPatternCard()  {
        return swingPhase.askForPatternCard();
    }

}
