package it.polimi.se2018.model.toolcards;

import it.polimi.se2018.model.container.DiceContainer;
import it.polimi.se2018.utils.Database;

import java.util.ArrayList;

/* TODO: test and documentation */

public class ToolCardContainer {

    private static final int NUMBER_OF_TOOLCARDS = 12;

    private ArrayList<ToolCard> toolCards = new ArrayList<>();

    private ArrayList<Integer> toolCardsInPlay = new ArrayList<>();

    public ToolCardContainer(DiceContainer diceContainer) {
        Database database = new Database(diceContainer);
        this.toolCards = database.loadToolCards();
    }

    public ToolCard getToolCard(int id) {
        return toolCards.get(id);
    }

    public void setToolCardInPlay(int toolCardId) {
        this.getToolCard(toolCardId).setInGame(true);
    }

    public ArrayList<ToolCard> getToolCardInPlay() {
        ArrayList<ToolCard> toolCardsInGame= new ArrayList<ToolCard>();
        for( int i = 0; i < 12; i++)
            if(toolCards.get(i).isInGame())
                toolCardsInGame.add(toolCards.get(i));

        return toolCardsInGame;
    }

    public ToolCard getToolCardInPlay(int id)
    {
        if(toolCardsInPlay.contains(id)) {
            return toolCards.get(id);

        }
        return null;
    }
}