package it.polimi.se2018.model.toolcards;

import it.polimi.se2018.model.container.DiceContainer;
import it.polimi.se2018.utils.Database;

import java.util.ArrayList;

public class ToolCardContainer {

    private static final int NUMBER_OF_TOOLCARDS = 12;

    private ArrayList<ToolCard> toolCards;

    public ToolCard getToolCard(int id) {
        return toolCards.get(id);
    }

    public ToolCardContainer() {
        DiceContainer diceContainer = new DiceContainer();
        Database database = new Database(diceContainer);

        this.toolCards = database.loadToolCards();
    }
}