package it.polimi.se2018.view;

import it.polimi.se2018.model.events.ModelChangedMessage;
import it.polimi.se2018.model.events.PlayerMessage;
import it.polimi.se2018.network.ClientInterface;
import it.polimi.se2018.network.server.Room;
import it.polimi.se2018.utils.Observable;
import it.polimi.se2018.utils.Observer;

import java.util.ArrayList;

public class    VirtualView extends Observable<PlayerMessage> implements Observer<ModelChangedMessage>{

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

    public ArrayList<Integer> getPositionInPatternCard(int clientId){
        return room.getPositionInPatternCard(clientId);
    }

    public ArrayList<Integer> getIncrementedValue(int clientId) {return room.getIncrementedValue(clientId);}

    public Integer getDieFromDiceArena(int clientId) { return room.getDieFromDiceArena(clientId);}

    public ArrayList<Integer> getDieFromRoundTrack(int clientId) { return  room.getDieFromRoundTrack(clientId);}

    public Integer getValueForDie(int clientId) { return  room.getValueForDie(clientId);}

    public ArrayList<Integer> getDoublePositionInPatternCard(int clientId) { return room.getDoublePositionInPatternCard(clientId);}

    public ArrayList<Integer> getSinglePositionInPatternCard(int clientId, ArrayList<Integer> listOfAvailablePositions) { return room.getSinglePositionInPatternCard(clientId, listOfAvailablePositions); }

    public void block(int clientId) { room.block(clientId);}

    public void free(int clientId) { room.free(clientId);}

}
