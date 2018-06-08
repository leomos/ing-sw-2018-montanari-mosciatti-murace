package it.polimi.se2018.model.events;

import it.polimi.se2018.model.Player;
import it.polimi.se2018.network.visitor.MessageVisitorInterface;

public class PlayerMessageEndTurn extends PlayerMessage implements Message {

    private Player player;

    public PlayerMessageEndTurn(Player player){
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public void accept(MessageVisitorInterface messageVisitorInterface) {
        messageVisitorInterface.visitPlayerMessageEndTurn(this);
    }
}
