package it.polimi.se2018.model.toolcards;

public class ToolCard {

    private int toolCardId;

    private String name;

    private String description;

    private boolean isUsedAtLeastOnce;

    public ToolCard(int toolCardId, String name, String description) {
        this.toolCardId = toolCardId;
        this.name = name;
        this.description = description;
        this.isUsedAtLeastOnce = false;
    }

    public String getName() {
        return name;
    }

    public String getDescription( ){return description; }

    public boolean isUsedAtLeastOnce() {
        return isUsedAtLeastOnce;
    }

    public void setUsedAtLeastOnce(boolean usedAtLeastOnce){
        isUsedAtLeastOnce = usedAtLeastOnce;
    }

    public void setUsed() {
        isUsedAtLeastOnce = true;
    }

    public int getToolCardId() {
        return toolCardId;
    }

    public int cost(){
        if (isUsedAtLeastOnce)
            return 2;
        else
            return 1;

    }
}
