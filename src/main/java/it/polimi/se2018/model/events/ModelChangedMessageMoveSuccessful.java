package it.polimi.se2018.model.events;

import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.Table;

public class ModelChangedMessageMoveSuccessful extends ModelChangedMessage{

    private Table table;

    private Player player;

    public ModelChangedMessageMoveSuccessful(Table table, Player player){
        this.table = table;
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public Table getTable() {
        return table;
    }
}
