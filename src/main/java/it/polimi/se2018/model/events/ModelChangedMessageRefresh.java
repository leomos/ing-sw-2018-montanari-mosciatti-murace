package it.polimi.se2018.model.events;

import it.polimi.se2018.model.GamePhase;

public class ModelChangedMessageRefresh extends ModelChangedMessage{

    GamePhase gamePhase;

    public ModelChangedMessageRefresh(GamePhase gamePhase){
        this.gamePhase = gamePhase;
    }

    public GamePhase getGamePhase() {
        return gamePhase;
    }

}
