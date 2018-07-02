package it.polimi.se2018.model.events;

import it.polimi.se2018.network.visitor.MessageVisitorInterface;

public class ModelChangedMessagePrivateObjective extends ModelChangedMessage implements Message {

    private String idPlayer;

    private String idPrivateObjective;

    private String name;

    private String description;

    /**
     * Message created by the model and put in a notify when the game sends the players their own private objective
     * @param idPlayer player id receiving the objective
     * @param idPrivateObjective private objective id
     * @param name private objective name
     * @param description private objective description
     */
    public ModelChangedMessagePrivateObjective (String idPlayer, String idPrivateObjective, String name, String description){
        this.idPlayer = idPlayer;
        this.idPrivateObjective = idPrivateObjective;
        this.name = name;
        this.description = description;
    }

    public String getIdPlayer() {
        return idPlayer;
    }

    public String getName() {
        return name;
    }

    public String getIdPublicObjective() {
        return idPrivateObjective;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public void accept(MessageVisitorInterface messageVisitorInterface) {
        messageVisitorInterface.visitModelChangedMessagePrivateObjective(this);
    }
}

