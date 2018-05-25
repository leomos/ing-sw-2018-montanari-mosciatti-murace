package it.polimi.se2018.view.client;

import it.polimi.se2018.model.events.ModelChangedMessage;

public interface ClientInterface {

    public void update(ModelChangedMessage modelChangedMessage);
}
