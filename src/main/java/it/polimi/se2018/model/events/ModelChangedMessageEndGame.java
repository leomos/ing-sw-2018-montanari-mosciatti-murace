package it.polimi.se2018.model.events;

import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.Scoreboard;
import it.polimi.se2018.network.visitor.MessageVisitorInterface;

public class ModelChangedMessageEndGame extends ModelChangedMessage implements Message {

    private Player player;

    private Scoreboard scoreboard;

    public ModelChangedMessageEndGame(Player player, Scoreboard scoreboard){
        this.player = player;
        this.scoreboard = scoreboard;
    }

    public Player getPlayer() {
        return player;
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    @Override
    public void accept(MessageVisitorInterface messageVisitorInterface) {
        messageVisitorInterface.visitModelChangedMessageEndGame(this);
    }
}
