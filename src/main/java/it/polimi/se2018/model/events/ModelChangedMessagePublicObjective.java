package it.polimi.se2018.model.events;

import it.polimi.se2018.network.visitor.MessageVisitorInterface;

public class ModelChangedMessagePublicObjective extends ModelChangedMessage implements Message {

    private int idPublicObjective;

    private String name;

    private String description;

    /**
     * Message created by the model and put in a notify when the game sends the players the public objective in game
     * @param idPublicObjective public objective id
     * @param name public objective name
     * @param description public objective description
     */
    public ModelChangedMessagePublicObjective (int idPublicObjective, String name, String description){
        this.idPublicObjective = idPublicObjective;
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public int getIdPublicObjective() {
        return idPublicObjective;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public void accept(MessageVisitorInterface messageVisitorInterface) {
        messageVisitorInterface.visitModelChangedMessagePublicObjective(this);
    }
}
