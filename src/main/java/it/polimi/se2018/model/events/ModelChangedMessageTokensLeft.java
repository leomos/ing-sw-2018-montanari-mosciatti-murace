package it.polimi.se2018.model.events;

import it.polimi.se2018.network.visitor.MessageVisitorInterface;

public class ModelChangedMessageTokensLeft extends ModelChangedMessage implements Message {

    private int idPlayer;

    private int tokensLeft;

    /**
     * Message created by the model and put in a notify when the game sends the players how many tokens he has left
     * @param idPlayer player id
     * @param tokensLeft tokens left to the player
     */
    public ModelChangedMessageTokensLeft(int idPlayer, int tokensLeft){
        this.idPlayer = idPlayer;
        this.tokensLeft = tokensLeft;
    }

    public int getIdPlayer() {
        return idPlayer;
    }

    public int getTokensLeft() {
        return tokensLeft;
    }

    @Override
    public void accept(MessageVisitorInterface messageVisitorInterface) {
        messageVisitorInterface.visitModelChangedMessageTokensLeft(this);
    }
}
