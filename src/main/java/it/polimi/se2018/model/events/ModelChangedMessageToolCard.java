package it.polimi.se2018.model.events;

import it.polimi.se2018.network.visitor.MessageVisitorInterface;

public class ModelChangedMessageToolCard extends ModelChangedMessage implements Message {

    private int idToolCard;

    private String name;

    private String description;

    private int cost;

    /**
     * Message created by the model and put in a notify when the game sends the tool cards in play or when the
     * cost of a tool card changes from 1 to 2
     * @param idToolCard tool card id
     * @param name tool card name
     * @param description tool card description
     * @param cost tool card cost
     */
    public ModelChangedMessageToolCard (int idToolCard, String name, String description, int cost){
        this.idToolCard = idToolCard;
        this.name = name;
        this.description = description;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public int getIdToolCard() {
        return idToolCard;
    }

    public String getDescription() {
        return description;
    }

    public int getCost() {
        return cost;
    }

    @Override
    public void accept(MessageVisitorInterface messageVisitorInterface) {
        messageVisitorInterface.visitModelChangedMessageToolCard(this);
    }
}
