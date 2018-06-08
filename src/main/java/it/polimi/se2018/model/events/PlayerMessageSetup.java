package it.polimi.se2018.model.events;

import it.polimi.se2018.network.visitor.MessageVisitorInterface;

import java.io.Serializable;

public class PlayerMessageSetup extends PlayerMessage implements Serializable, Message {

    private int player;

    private int patternCardId;

    public PlayerMessageSetup(int player, int patternCardId){
        this.player = player;
        this.patternCardId = patternCardId;
    }

    public int getPlayer() {
        return player;
    }

    public int getPatternCardId() {
        return patternCardId;
    }

    @Override
    public void accept(MessageVisitorInterface messageVisitorInterface) {
        messageVisitorInterface.visitPlayerMessageSetup(this);
    }
}
