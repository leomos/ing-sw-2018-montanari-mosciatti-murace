package it.polimi.se2018.model.events;

import it.polimi.se2018.model.GamePhase;
import it.polimi.se2018.network.visitor.MessageVisitorInterface;

public class ModelChangedMessageChangeGamePhase extends ModelChangedMessage {

    private GamePhase gamePhase;

    public ModelChangedMessageChangeGamePhase(GamePhase gamePhase){
        this.gamePhase = gamePhase;
    }


    public GamePhase getGamePhase() {
        return gamePhase;
    }

    @Override
    public void accept(MessageVisitorInterface messageVisitorInterface) {
        messageVisitorInterface.visitModelChangedMessageChangeGamePhase(this);
    }
}
