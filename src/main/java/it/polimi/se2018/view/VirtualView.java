package it.polimi.se2018.view;

import it.polimi.se2018.model.events.ModelChangedMessage;
import it.polimi.se2018.model.events.PlayerMessage;
import it.polimi.se2018.network.ClientInterface;
import it.polimi.se2018.network.server.Room;
import it.polimi.se2018.utils.Observable;
import it.polimi.se2018.utils.Observer;

import java.util.ArrayList;

//import it.polimi.se2018.network.server.Room;

public class VirtualView extends Observable<PlayerMessage> implements Observer<ModelChangedMessage>{

    private ArrayList<ClientInterface> clientInterfaceList = new ArrayList<>();

    private Room room;


    public void addClientInterface(ClientInterface clientInterface) {
        this.clientInterfaceList.add(clientInterface);
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public void update(ModelChangedMessage modelChangedMessage){
        room.updatePlayers(modelChangedMessage);
    }

    public void callNotify(PlayerMessage playerMessage){
        notify(playerMessage);
    }

    public ArrayList<ClientInterface> getClientInterfaceList() {
        return clientInterfaceList;
    }

    public ArrayList<Integer> getPositionInPatternCard(int idClient){
        return room.getPositionInPatternCard(idClient);
    }

    public ArrayList<Integer> getIncrementedValue(int idClient) {return room.getIncrementedValue(idClient);}
}
