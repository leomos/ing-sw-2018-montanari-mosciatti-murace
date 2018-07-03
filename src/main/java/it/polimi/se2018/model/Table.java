package it.polimi.se2018.model;

import it.polimi.se2018.model.container.DiceContainer;
import it.polimi.se2018.model.objectives.*;
import it.polimi.se2018.model.patternCard.PatternCard;
import it.polimi.se2018.model.player.OnlyOnePlayerLeftException;
import it.polimi.se2018.model.player.Player;
import it.polimi.se2018.model.player.PlayersHaveAllChosenAPatternCard;
import it.polimi.se2018.model.rounds.RoundFinishedException;
import it.polimi.se2018.model.rounds.RoundTrack;
import it.polimi.se2018.model.rounds.RoundTrackNoMoreRoundsException;
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

    /**
     * When the class table is constructed, it sets 4 random pattern cards and a private objective to all
     * the player in the room. It also pics 3 random tool cards and 3 random public objective and it rolls
     * n dice into the draft pool where n is the number of player multiplied by 2 and added by one
     * @param HM hash map containing the player id as a key and the name of the players as the value
     */
    public Table(HashMap<Integer, String> HM) {
        for(Integer key : HM.keySet()) {
            this.players.add(new Player(key, HM.get(key)));
        }
        this.diceContainer = new DiceContainer();
        this.database = new Database(diceContainer);
        this.toolCardContainer = new ToolCardContainer(diceContainer, database);
        this.diceArena = new DiceArena(players.size() * 2 + 1, diceContainer);
        this.roundTrack = new RoundTrack(players, diceContainer);

        this.patternCards = database.loadPatternCard();
        this.privateObjectives = database.loadPrivateObjective();

        this.setPatternCardsToPlayer();
        this.setPrivateObjectiveToPlayers();
        this.setPublicObjective();
        this.setToolCards();

        this.diceArena.rollDiceIntoArena();
        try {
            this.getRoundTrack().startNextRound(this);
        } catch (RoundTrackNoMoreRoundsException e) {

        }
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
        for (Player player : players) {
            if (player.getId() == i) return player;
        }
        return null;
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


    /**
     * To set 3 public objective, this method creates a array list containing 10 elements, it shuffles it and then it
     * picks the first three number of the list and creates the public objectives on the table depending on those numbers
     */
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

    /**
     * To set 3 tool cards, this method creates a array list containing 12 elements, it shuffles it and then it
     * picks the first three number of the list and creates the tool cards on the table depending on those numbers
     */
    private void setToolCards(){
        ArrayList<Integer> toolCardsList = new ArrayList<>();
        for (Integer i = 0; i < 12; i++)
            toolCardsList.add(i);

        Collections.shuffle(toolCardsList);

        //TO FORCE 3 PATTERNCARDS FOR TESTS

        toolCardsList.add(0,11);
        toolCardsList.add(1,0);
        toolCardsList.add(2,9);


        for(int j = 0; j < 3; j++)
            toolCardContainer.setToolCardInPlay(toolCardsList.get(j));
    }

    /**
     * This method picks 1 random private objective for each player in the room
     */
    private void setPrivateObjectiveToPlayers(){
        ArrayList<Integer> privateObjectiveList = new ArrayList<>();
        for (Integer i = 0; i < 5; i++)
            privateObjectiveList.add(i);

        Collections.shuffle(privateObjectiveList);

        for(int j = 0; j < players.size(); j++)
            players.get(j).setPrivateObjective(privateObjectives.get(privateObjectiveList.get(j)));
    }

    /**
     * This method sets 4 random pattern cards for each player to choose. It creates and array list long as half as
     * the total number of pattern cards (the number of pattern cards must always be pair because each pattern card
     * has a pattern card in the back). It then shuffles the array list and picks two number: if the number picked for
     * player 1 were 3 and 7 and in total there are 24 pattern cards, the player 1 is gonna have to choose his pattern
     * card between the pattern cards represented by the id 3, 15, 7 and 19.
     * 15 = 3 + (24/2) and 17 = 7 +(24/2)
     *
     */
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

    /**
     * this method was created because otherwise the public objective would always be random.
     * with this method, we can force the the public objective that we want necessary to test
     * the method calculateScore
     * @param listOfPublicObjectiveId list of public objective id that we want to force on the table
     * @param diceContainer dice container necessary to create public objectives
     */
    public void forcePublicObjectiveIntoPlay(ArrayList<Integer> listOfPublicObjectiveId, DiceContainer diceContainer){

        for(int i = 0; i < listOfPublicObjectiveId.size(); i++){
            switch(listOfPublicObjectiveId.get(i)) {
                case 0: this.publicObjectives.add(i, new PublicObjective1(diceContainer)); break;
                case 1: this.publicObjectives.add(i, new PublicObjective2(diceContainer)); break;
                case 2: this.publicObjectives.add(i, new PublicObjective3(diceContainer)); break;
                case 3: this.publicObjectives.add(i, new PublicObjective4(diceContainer)); break;
                case 4: this.publicObjectives.add(i, new PublicObjective5(diceContainer)); break;
                case 5: this.publicObjectives.add(i, new PublicObjective6(diceContainer)); break;
                case 6: this.publicObjectives.add(i, new PublicObjective7(diceContainer)); break;
                case 7: this.publicObjectives.add(i, new PublicObjective8(diceContainer)); break;
                case 8: this.publicObjectives.add(i, new PublicObjective9(diceContainer)); break;
                case 9: this.publicObjectives.add(i, new PublicObjective10(diceContainer)); break;
                default: break;
            }
        }

    }

    /**
     * This method is invoked when a player is set suspended. It checks if there are at least still 2 people playing the
     * game. In case there is only one person, it throw the exception OnlyOnePlayerLeftException so that the game
     * can end
     * @throws OnlyOnePlayerLeftException when only one player is left in the room
     */
    public void checkActivePlayers() throws OnlyOnePlayerLeftException {

        int count = players.size();
        for(int i = 0; i < players.size(); i++) {
            if (players.get(i).isSuspended())
                count--;
        }

        if(count <= 1)
            throw new OnlyOnePlayerLeftException();
    }

    /**
     * This method check if the player who is gonna start the game has disconnected himself from the game or has not
     * picked a pattern card and is the first player to play the first round. In that case, it calls the method end
     * turn so that the other players don't have to wait for another timer because the first player is afk or disconnected
     */
    public void checkPlayerDidNotDisconnectDuringPatternCardSelection(){

        for(Player player : players){
            if(player.getId() == roundTrack.getCurrentRound().getIdPlayerPlaying())
                if(player.isSuspended()) {
                    try {
                        roundTrack.getCurrentRound().setNextPlayer(this);
                    } catch (RoundFinishedException e) {
                        e.printStackTrace();
                    }
                }
        }
    }

    /**
     * This method checks whether all players have chosen the pattern card at the beginning of the game. If one or more
     * player have not done that, the game is not ready to start. This method is invoked every time a pattern card is
     * chosen by a player. When the last player has chosen a pattern card, the method throws the exception
     * PlayersHaveAllChosenAPatternCard and the game is ready to start the main phase
     * @throws PlayersHaveAllChosenAPatternCard when all players have chosen a pattern card
     */
    public void checkAllPlayerHasChosenAPatternCard() throws PlayersHaveAllChosenAPatternCard {

        for(Player player : this.players)
            if(!player.hasChosenPatternCard())
                return;

        throw new PlayersHaveAllChosenAPatternCard();

    }

    /**
     * This method calculates the score of each player by following the game rules which are:
     * + points from public objective
     * + points from private objective
     * + 1 point for tokens left
     * - 1 point for each empty cell in the player's pattern card
     */
    public void calculateScores() {
        this.scoreboard = new Scoreboard(roundTrack.getCurrentRound().getIdPlayerPlaying());
        ArrayList<Integer>  orderedScores = new ArrayList<>();
        HashMap<Player, Integer> playerScore = new HashMap<>();

        for (Player player : players) {
            int result = 0;
            PatternCard patternCard = player.getChosenPatternCard();

            for (int j = 0; j < 3; j++)
                result += publicObjectives.get(j).calculateScore(patternCard);


            result += player.getPrivateObjective().calculateScore(patternCard);

            result = result + player.getTokens() + patternCard.getNumberOfDiceInThePatternCard() - 20;

            orderedScores.add(result);
            player.setFinalScore(result);

            scoreboard.setScore(player.getId(), result, player.getTokens());

        }



    }
}
