package it.polimi.se2018.view;

import it.polimi.se2018.model.events.ModelChangedMessage;
import it.polimi.se2018.model.events.PlayerMessage;
import it.polimi.se2018.network.ClientInterface;
import it.polimi.se2018.utils.Observable;
import it.polimi.se2018.utils.Observer;

import java.rmi.RemoteException;
import java.util.ArrayList;

//import it.polimi.se2018.network.server.Room;

public class VirtualView extends Observable<PlayerMessage> implements Observer<ModelChangedMessage>{

    private ArrayList<ClientInterface> clientInterfaceList = new ArrayList<>();

    public void addClientInterface(ClientInterface clientInterface) {
        this.clientInterfaceList.add(clientInterface);
    }

    public void update(ModelChangedMessage modelChangedMessage){
        for(ClientInterface i: clientInterfaceList) {
            try {
                i.update(modelChangedMessage);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }


    public void callNotify(PlayerMessage playerMessage){
        notify(playerMessage);
    }

    public ArrayList<ClientInterface> getClientInterfaceList() {
        return clientInterfaceList;
    }

    public ArrayList<Integer> getPositionInPatternCard(int idClient){
        ArrayList<Integer> app = new ArrayList<Integer>();
        ArrayList<Integer> correctClientInterface = new ArrayList<Integer>();
        for(ClientInterface i: clientInterfaceList) {
                try {
                    app = i.getPositionInPatternCard();
                    if(app != null)
                        correctClientInterface = app;
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
        }
        return correctClientInterface;
    }
}
