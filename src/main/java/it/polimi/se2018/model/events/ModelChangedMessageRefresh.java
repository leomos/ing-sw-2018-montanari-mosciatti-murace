package it.polimi.se2018.model.events;

import it.polimi.se2018.model.GamePhase;
import it.polimi.se2018.network.visitor.MessageVisitorInterface;

public class ModelChangedMessageRefresh extends ModelChangedMessage implements Message {

    GamePhase gamePhase;

    String idPlayerPlaying;

    public ModelChangedMessageRefresh(GamePhase gamePhase, String idPlayerPlaying){
        this.gamePhase = gamePhase;
        this.idPlayerPlaying = idPlayerPlaying;
    }

    public GamePhase getGamePhase() {
        return gamePhase;
    }

    @Override
    public void accept(MessageVisitorInterface messageVisitorInterface) {
        messageVisitorInterface.visitModelChangedMessageRefresh(this);
    }
    public String getIdPlayerPlaying() {
        return idPlayerPlaying;
    }
}
