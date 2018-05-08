package it.polimi.se2018.model.toolcards;

public class ToolCardContainer {

    private static final int NUMBER_OF_TOOLCARDS = 12;

    private ToolCard[] toolCards = new ToolCard[NUMBER_OF_TOOLCARDS];

    public ToolCard getToolCard(int id) {
            return toolCards[id];
    }

}
