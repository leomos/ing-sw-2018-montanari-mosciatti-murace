package it.polimi.se2018.model.events;

import it.polimi.se2018.network.visitor.MessageVisitorInterface;

public class PlayerMessageEndTurn extends PlayerMessage {

    /**
     * Message generated by the view and sent it to the Virtual View when the player want to end his own turn
     * @param player player id finishing his turn
     */
    public PlayerMessageEndTurn(int player){
        this.player = player;
    }

    @Override
    public void accept(MessageVisitorInterface messageVisitorInterface) {
        messageVisitorInterface.visitPlayerMessageEndTurn(this);
    }
}
