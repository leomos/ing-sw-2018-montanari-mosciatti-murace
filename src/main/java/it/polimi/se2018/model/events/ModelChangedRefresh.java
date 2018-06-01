package it.polimi.se2018.model.events;

import it.polimi.se2018.model.GamePhase;

public class ModelChangedRefresh {

    GamePhase gamePhase;

    public ModelChangedRefresh(GamePhase gamePhase){
        this.gamePhase = gamePhase;
    }

    public GamePhase getGamePhase() {
        return gamePhase;
    }

}
