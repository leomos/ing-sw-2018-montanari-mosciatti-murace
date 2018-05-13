package it.polimi.se2018.model.Events;

import it.polimi.se2018.model.PatternCard;
import it.polimi.se2018.model.Player;

import java.util.ArrayList;

public class ModelChangedMessageStartSetup extends ModelChangedMessage{

    private Player player;

    private ArrayList<PatternCard> listPatternCard;

    public ModelChangedMessageStartSetup(Player player, ArrayList<PatternCard> listPatternCard){
        this.player = player;
        this.listPatternCard = listPatternCard;
    }

    public Player getPlayer() {
        return player;
    }

    public ArrayList<PatternCard> getListPatternCard() {
        return listPatternCard;
    }
}
