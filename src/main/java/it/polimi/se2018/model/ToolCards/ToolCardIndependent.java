package it.polimi.se2018.model.ToolCards;

public abstract class ToolCardIndependent implements ToolCard {
    protected String description;
    protected int id;

    public String getDescription () { return description; }
}
