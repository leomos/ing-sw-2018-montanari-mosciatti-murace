package it.polimi.se2018.model.events;

import it.polimi.se2018.network.visitor.MessageVisitorInterface;

public class ModelChangedMessageRefresh extends ModelChangedMessage implements Message {

    private static final long serialVersionUID = 2000L;

    String idPlayerPlaying;

    public ModelChangedMessageRefresh(String idPlayerPlaying){
        this.idPlayerPlaying = idPlayerPlaying;
    }

    @Override
    public void accept(MessageVisitorInterface messageVisitorInterface) {
        messageVisitorInterface.visitModelChangedMessageRefresh(this);
    }
    public String getIdPlayerPlaying() {
        return idPlayerPlaying;
    }
}
