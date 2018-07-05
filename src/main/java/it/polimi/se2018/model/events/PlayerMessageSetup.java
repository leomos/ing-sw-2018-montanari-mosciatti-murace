package it.polimi.se2018.model.events;

import it.polimi.se2018.network.visitor.MessageVisitorInterface;

import java.io.Serializable;

public class PlayerMessageSetup extends PlayerMessage implements Serializable, Message {

    private int patternCardId;

    /**
     * Message generated by the view and sent it to the Virtual View when the player chooses one of the 4 pattern cards
     * available during the SETUP PHASE
     * @param player player choosing the pattern card
     * @param patternCardId pattern card id chosen by the player
     */
    public PlayerMessageSetup(int player, int patternCardId){
        super(player);
        this.patternCardId = patternCardId;
    }

    public int getPatternCardId() {
        return patternCardId;
    }

    @Override
    public void accept(MessageVisitorInterface messageVisitorInterface) {
        messageVisitorInterface.visitPlayerMessageSetup(this);
    }
}
