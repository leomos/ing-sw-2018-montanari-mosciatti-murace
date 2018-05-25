package it.polimi.se2018.view.server;

import it.polimi.se2018.model.events.ModelChangedMessage;
import it.polimi.se2018.model.events.PlayerMessage;
import it.polimi.se2018.utils.Observable;
import it.polimi.se2018.utils.Observer;
import it.polimi.se2018.view.client.ClientInterface;

import java.util.ArrayList;

public class VirtualView extends Observable<PlayerMessage> implements Observer<ModelChangedMessage>{

    private ArrayList<ClientInterface> clientInterfacesList = new ArrayList<>();

    public void update(ModelChangedMessage modelChangedMessage){

    }

    public void notify(PlayerMessage playerMessage){

    }

    public void addClient(ClientInterface clientInterface){
        clientInterfacesList.add(clientInterface);
    }


}
