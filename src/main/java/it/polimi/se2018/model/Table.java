package it.polimi.se2018.model;

import it.polimi.se2018.model.container.DiceContainer;
import it.polimi.se2018.model.objectives.*;
import it.polimi.se2018.model.rounds.RoundTrack;
import it.polimi.se2018.model.toolcards.ToolCardContainer;
import it.polimi.se2018.utils.Database;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Table {

    private ArrayList<PublicObjective> publicObjectives = new ArrayList<PublicObjective>();

    private ToolCardContainer toolCardContainer;

    private DiceArena diceArena;

    private Database database;

    private RoundTrack roundTrack;

    private DiceContainer diceContainer;

    private ArrayList<PatternCard> patternCards = new ArrayList<PatternCard>();

    private ArrayList<PrivateObjective> privateObjectives = new ArrayList<PrivateObjective>();

    private ArrayList<Player> players = new ArrayList<Player>();

    private Scoreboard scoreboard;

    /* TODO: COSTRUTTORE che crei ogni attributo e che setti i Players + controllare se tipo PLayer  ok */
    public Table(HashMap<Integer, String> HM){
        for(Integer key : HM.keySet()) {
            this.players.add(new Player(key, HM.get(key)));
        }
        this.diceContainer = new DiceContainer();
        this.toolCardContainer = new ToolCardContainer(diceContainer);
        this.diceArena = new DiceArena(players.size() * 2 + 1, diceContainer);
        this.roundTrack = new RoundTrack(players);
        this.database = new Database(diceContainer);

        this.patternCards = database.loadPatternCard();
        this.privateObjectives = database.loadPrivateObjective();

        this.setPatternCardsToPlayer();
        this.setPrivateObjectiveToPlayers();
        this.setPublicObjective();
        this.setToolCards();
    }

    public int getNumberOfPlayers() {
        return this.players.size();
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

    public DiceContainer getDiceContainer() {
        return diceContainer;
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
            toolCardContainer.setToolCardInPlay(toolCardsList.get(j));
    }

    private void setPrivateObjectiveToPlayers(){
        ArrayList<Integer> privateObjectiveList = new ArrayList<>();
        for (Integer i = 0; i < 5; i++)
            privateObjectiveList.add(i);

        Collections.shuffle(privateObjectiveList);

        for(int j = 0; j < players.size(); j++)
            players.get(j).setPrivateObjective(privateObjectives.get(privateObjectiveList.get(j)));
    }

    private void setPatternCardsToPlayer(){
        ArrayList<Integer> patternCardsList = new ArrayList<>();
        for (Integer i = 0; i < 12; i++)
            patternCardsList.add(i);

        Collections.shuffle(patternCardsList);

        for(int j = 0; j < players.size(); j++){
            ArrayList<PatternCard> patternCardsToPlayer = new ArrayList<>();
            int val = patternCardsList.get(j);
            patternCardsToPlayer.add(patternCards.get(val));
            patternCardsToPlayer.add(patternCards.get(val + 12));
            val = patternCardsList.get(11 - j);
            patternCardsToPlayer.add(patternCards.get(val));
            patternCardsToPlayer.add(patternCards.get(val + 12));
            players.get(j).setPatternCards(patternCardsToPlayer);
        }
    }

    /* TODO: toString */
    public String toString(){
        return null;
    }
}
