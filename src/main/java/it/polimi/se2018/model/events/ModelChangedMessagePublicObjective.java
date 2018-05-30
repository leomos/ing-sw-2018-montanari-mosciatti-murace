package it.polimi.se2018.model.events;

public class ModelChangedMessagePublicObjective {

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
}
