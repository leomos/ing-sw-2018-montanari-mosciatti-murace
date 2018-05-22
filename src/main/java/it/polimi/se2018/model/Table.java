package it.polimi.se2018.model;

import it.polimi.se2018.model.objectives.PublicObjective;
import it.polimi.se2018.model.rounds.RoundTrack;
import it.polimi.se2018.model.toolcards.ToolCardContainer;

import java.util.ArrayList;

public class Table {

    private int idTable; /* necessario? */

    private ArrayList<PublicObjective> publicObjectives;

    private ToolCardContainer toolCardContainer;

    private DiceArena diceArena;

    private RoundTrack roundTrack;

    private ArrayList<Player> Players;

    private Scoreboard scoreboard;

    /* TODO: costruttore che crei ogni attributo e che setti i Players + controllare se tipo PLayer  ok*/

    public DiceArena getDiceArena() {
        return diceArena;
    }

    public RoundTrack getRoundTrack() {
        return roundTrack;
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    public ToolCardContainer getToolCardContainer() {
        return toolCardContainer;
    }

    public Player getPlayers(int i) {
        return Players.get(i);
    }

    public PublicObjective getPublicObjective(int i) {
        return publicObjectives.get(i);
    }

    /* TODO: toString */
    public String toString(){
        return null;
    }
}
