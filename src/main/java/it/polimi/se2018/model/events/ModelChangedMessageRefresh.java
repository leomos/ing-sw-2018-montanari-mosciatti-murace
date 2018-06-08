package it.polimi.se2018.model.events;

import it.polimi.se2018.model.GamePhase;

public class ModelChangedMessageRefresh extends ModelChangedMessage{

    GamePhase gamePhase;

    String idPlayerPlaying;

    public ModelChangedMessageRefresh(GamePhase gamePhase, String idPlayerPlaying){
        this.gamePhase = gamePhase;
        this.idPlayerPlaying = idPlayerPlaying;
    }

    public GamePhase getGamePhase() {
        return gamePhase;
    }

    public String getIdPlayerPlaying() {
        return idPlayerPlaying;
    }
}
