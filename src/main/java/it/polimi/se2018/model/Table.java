package it.polimi.se2018.model;

import it.polimi.se2018.model.container.DiceContainer;
import it.polimi.se2018.model.objectives.*;
import it.polimi.se2018.model.rounds.RoundTrack;
import it.polimi.se2018.model.toolcards.ToolCard;
import it.polimi.se2018.model.toolcards.ToolCardContainer;
import it.polimi.se2018.utils.Database;

import java.util.ArrayList;
import java.util.Collections;

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


    /* TODO: COSTRUTTORE che crei ogni attributo e che setti i Players + controllare se tipo PLayer  ok */
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

    private void setPublicObjective(){
        ArrayList<Integer> publicObjectiveList = new ArrayList<>();
        for (Integer i = 0; i < 10; i++)
            publicObjectiveList.add(i);

        Collections.shuffle(publicObjectiveList);

        for(int j = 0; j < 3; j++)
            switch(publicObjectiveList.get(j)) {
                case 0: this.publicObjectives.add(new PublicObjective1(diceContainer)); break;
                case 1: this.publicObjectives.add(new PublicObjective2(diceContainer)); break;
                case 2: this.publicObjectives.add(new PublicObjective3(diceContainer)); break;
                case 3: this.publicObjectives.add(new PublicObjective4(diceContainer)); break;
                case 4: this.publicObjectives.add(new PublicObjective5(diceContainer)); break;
                case 5: this.publicObjectives.add(new PublicObjective6(diceContainer)); break;
                case 6: this.publicObjectives.add(new PublicObjective7(diceContainer)); break;
                case 7: this.publicObjectives.add(new PublicObjective8(diceContainer)); break;
                case 8: this.publicObjectives.add(new PublicObjective9(diceContainer)); break;
                case 9: this.publicObjectives.add(new PublicObjective10(diceContainer)); break;
                default: break;
            }
    }

    private void setToolCards(){
        ArrayList<Integer> toolCardsList = new ArrayList<>();
        for (Integer i = 0; i < 10; i++)
            toolCardsList.add(i);

        Collections.shuffle(toolCardsList);

        for(int j = 0; j < 3; j++)
            this.toolCardContainer.setToolCardInPlay(j);
    }


    /* TODO: toString */
    public String toString(){
        return null;
    }
}
