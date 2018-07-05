package it.polimi.se2018.model.toolcards;

import it.polimi.se2018.model.container.DiceContainer;
import it.polimi.se2018.utils.Database;

import java.util.ArrayList;

public class ToolCardContainer {

    private static final int NUMBER_OF_TOOLCARDS = 12;

    private ArrayList<ToolCard> toolCards = new ArrayList<>();

    /**
     * The tool cards container creates the array list containing the tool cards from the database
     * @param diceContainer dice container needed for the database
     * @param database database where the tool cards name and descriptions are stored
     */
    public ToolCardContainer(DiceContainer diceContainer, Database database) {
        this.toolCards = database.loadToolCards();
    }

    public ToolCard getToolCard(int id) {
        return toolCards.get(id);
    }

    public void setToolCardInPlay(int toolCardId) {
        this.getToolCard(toolCardId).setInGame(true);
    }

    /**
     * @return the array list containing all the 3 tool cards that are on the table this game
     */
    public ArrayList<ToolCard> getToolCardInPlay() {
        ArrayList<ToolCard> toolCardsInGame= new ArrayList<ToolCard>();
        for( int i = 0; i < 12; i++)
            if(toolCards.get(i).isInGame())
                toolCardsInGame.add(toolCards.get(i));

        return toolCardsInGame;
    }

}