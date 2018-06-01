package it.polimi.se2018.model.events;

public class ModelChangedMessagePrivateObjective extends ModelChangedMessage {

    private String idPlayer;

    private String idPrivateObjective;

    private String name;

    private String description;

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
}

