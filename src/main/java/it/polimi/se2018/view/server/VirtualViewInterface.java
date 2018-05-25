package it.polimi.se2018.view.server;

import it.polimi.se2018.model.events.PlayerMessage;
import it.polimi.se2018.view.client.ClientInterface;

public interface VirtualViewInterface {

    public void notify(PlayerMessage playerMessage);

    public void addClient(ClientInterface clientInterface);

}
