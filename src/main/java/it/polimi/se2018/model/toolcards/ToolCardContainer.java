package it.polimi.se2018.model.toolcards;

import it.polimi.se2018.model.container.DiceContainer;
import it.polimi.se2018.utils.Database;

import java.util.ArrayList;

public class ToolCardContainer {

    private static final int NUMBER_OF_TOOLCARDS = 12;

    private ArrayList<ToolCard> toolCards;

    private ArrayList<Integer> toolCardsInPlay = new ArrayList<>();

    public ToolCardContainer() {
        DiceContainer diceContainer = new DiceContainer();
        Database database = new Database(diceContainer);

        this.toolCards = database.loadToolCards();
    }

    public ToolCard getToolCard(int id) {
        return toolCards.get(id);
    }

    public void setToolCardInPlay(int toolCardId) {
        toolCardsInPlay.add(toolCardId);
    }

    /* TODO: eccezione? */
    public ToolCard getToolCardInPlay(int id) {
        if(toolCardsInPlay.contains(id)) {
            return toolCards.get(id);
        } else {
            return null;
        }
    }
}