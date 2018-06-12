package it.polimi.se2018.model.events;

import it.polimi.se2018.network.visitor.MessageVisitorInterface;

public class ModelChangedMessageTokensLeft extends ModelChangedMessage implements Message {

    private String idPlayer;

    private String tokensLeft;

    public ModelChangedMessageTokensLeft(String idPlayer, String tokensLeft){
        this.idPlayer = idPlayer;
        this.tokensLeft = tokensLeft;
    }

    public String getIdPlayer() {
        return idPlayer;
    }

    public String getTokensLeft() {
        return tokensLeft;
    }

    @Override
    public void accept(MessageVisitorInterface messageVisitorInterface) {
        messageVisitorInterface.visitModelChangedMessageTokensLeft(this);
    }
}
