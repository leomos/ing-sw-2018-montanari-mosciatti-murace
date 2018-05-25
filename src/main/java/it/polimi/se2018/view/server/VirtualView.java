package it.polimi.se2018.view.server;

import it.polimi.se2018.model.events.ModelChangedMessage;
import it.polimi.se2018.model.events.PlayerMessage;
import it.polimi.se2018.utils.Observable;
import it.polimi.se2018.utils.Observer;
import it.polimi.se2018.view.client.ClientInterface;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class VirtualView extends Observable<PlayerMessage> implements Observer<ModelChangedMessage>{

    private ArrayList<ClientInterface> clientInterfaceList = new ArrayList<>();

    public void update(ModelChangedMessage modelChangedMessage){
        for(ClientInterface i: clientInterfaceList) {
            try {
                i.update(modelChangedMessage);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void notify(PlayerMessage playerMessage){

    }

    public void addClient(ClientInterface clientInterface){
        clientInterfaceList.add(clientInterface);
        System.out.println("Client added");
    }


}
