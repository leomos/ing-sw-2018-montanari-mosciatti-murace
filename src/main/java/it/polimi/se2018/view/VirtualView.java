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

    public Integer getDieFromDiceArena(int idClient) { return room.getDieFromDiceArena(idClient);}

    public ArrayList<Integer> getDieFromRoundTrack(int idClient) { return  room.getDieFromRoundTrack(idClient);}

    public Integer getValueForDie(int idClient) { return  room.getValueForDie(idClient);}

    public ArrayList<Integer> getDoublePositionInPatternCard(int idClient) { return room.getDoublePositionInPatternCard(idClient);}

    public ArrayList<Integer> getSinglePositionInPatternCard(int idClient, ArrayList<Integer> listOfAvailablePositions) { return room.getSinglePositionInPatternCard(idClient, listOfAvailablePositions); }

    public void block(int idClient) { room.block(idClient);}

    public void free(int idClient) { room.free(idClient);}

}
