package it.polimi.se2018.model;

import it.polimi.se2018.model.container.DiceContainer;
import it.polimi.se2018.model.objectives.PrivateObjective;
import it.polimi.se2018.model.objectives.PublicObjective;
import it.polimi.se2018.model.rounds.RoundTrack;
import it.polimi.se2018.model.toolcards.ToolCardContainer;
import it.polimi.se2018.utils.Database;

import java.util.ArrayList;

public class Table {

    private ArrayList<PublicObjective> publicObjectives;

    private ToolCardContainer toolCardContainer;

    private DiceArena diceArena;

    private Database database;

    private RoundTrack roundTrack;

    private DiceContainer diceContainer;

    private ArrayList<PatternCard> patternCards;

    private ArrayList<PrivateObjective> privateObjectives;

    private ArrayList<Player> players;

    private Scoreboard scoreboard;


    /* TODO: COSTRUTTORE che crei ogni attributo e che setti i Players + controllare se tipo PLayer  ok
    public Table(int[] idPlayer, String[] name){
        for(int i = 0; i < idPlayer.length; i++) {
            this.players.add(new Player(idPlayer[i], name[i]))
        }
        this.toolCardContainer = new ToolCardContainer();
        this.diceArena = new DiceArena(players.size() * 2 + 1);
        this.roundTrack = new RoundTrack(players);
        this.diceContainer = new DiceContainer();
        this.database = new Database(diceContainer);

        this.patternCards = database.loadPatternCard();
        this.privateObjectives = database.loadPrivateObjective();

        this.setPatternCards();
        this.setPrivateObjective();
        this.setPublicObjective();
        this.setToolCards();
    }
    */

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
        return players.get(i);
    }

    public PublicObjective getPublicObjective(int i) {
        return publicObjectives.get(i);
    }

    public Database getDatabase() {
        return database;
    }

    public PatternCard getPatternCards(int i) {
        return patternCards.get(i);
    }



    /* TODO: toString */
    public String toString(){
        return null;
    }
}
