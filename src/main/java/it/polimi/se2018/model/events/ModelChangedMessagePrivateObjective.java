package it.polimi.se2018.model.events;

public class ModelChangedMessagePrivateObjective {

    private String idPrivateObjective;

    private String name;

    private String description;

    public ModelChangedMessagePrivateObjective (String idPrivateObjective, String name, String description){
        this.idPrivateObjective = idPrivateObjective;
        this.name = name;
        this.description = description;
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

