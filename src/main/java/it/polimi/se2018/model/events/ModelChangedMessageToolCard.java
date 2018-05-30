package it.polimi.se2018.model.events;

public class ModelChangedMessageToolCard {

    private String idToolCard;

    private String name;

    private String description;

    private String cost;

    public ModelChangedMessageToolCard (String idToolCard, String name, String description, String cost){
        this.idToolCard = idToolCard;
        this.name = name;
        this.description = description;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public String getIdToolCard() {
        return idToolCard;
    }

    public String getDescription() {
        return description;
    }

    public String getCost() {
        return cost;
    }
}
