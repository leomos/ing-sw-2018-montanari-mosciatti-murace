package it.polimi.se2018.model.events;

import it.polimi.se2018.network.visitor.MessageVisitorInterface;

public class ModelChangedMessageToolCard extends ModelChangedMessage implements Message {

    private String idToolCard;

    private String name;

    private String description;

    private String cost;

    /**
     * Message created by the model and put in a notify when the game sends the tool cards in play or when the
     * cost of a tool card changes from 1 to 2
     * @param idToolCard tool card id
     * @param name tool card name
     * @param description tool card description
     * @param cost tool card cost
     */
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

    @Override
    public void accept(MessageVisitorInterface messageVisitorInterface) {
        messageVisitorInterface.visitModelChangedMessageToolCard(this);
    }
}
