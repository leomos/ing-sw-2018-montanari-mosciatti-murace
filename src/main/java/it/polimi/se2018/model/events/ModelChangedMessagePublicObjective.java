package it.polimi.se2018.model.events;

import it.polimi.se2018.network.visitor.MessageVisitorInterface;

public class ModelChangedMessagePublicObjective extends ModelChangedMessage implements Message {

    private String idPublicObjective;

    private String name;

    private String description;

    public ModelChangedMessagePublicObjective (String idPublicObjective, String name, String description){
        this.idPublicObjective = idPublicObjective;
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getIdPublicObjective() {
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
