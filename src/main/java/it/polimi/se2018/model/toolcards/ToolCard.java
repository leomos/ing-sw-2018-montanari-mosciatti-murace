package it.polimi.se2018.model.toolcards;

public class ToolCard {

    private int toolCardId;

    private String name;

    private String description;

    private int tokensUsed;

    public String getName() {
        return name;
    }

    public String getDescription( ){return description; }

    public int getTokensUsed() {
        return tokensUsed;
    }

    public void setTokensUsed(int tokensUsed){ this.tokensUsed = tokensUsed;}

    public int getToolCardId() {
        return toolCardId;
    }
}
