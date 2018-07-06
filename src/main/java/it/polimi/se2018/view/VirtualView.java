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

    /**
     * Forwards the messages from model to view
     * @param modelChangedMessage message forwarded
     */
    public void update(ModelChangedMessage modelChangedMessage){
        room.updatePlayers(modelChangedMessage);
    }

    /**
     * Forwards the messages from view to model
     * @param playerMessage message forwarded
     */
    public void callNotify(PlayerMessage playerMessage){
        notify(playerMessage);
    }

    /**
     * method invoked by the controller on view for tool cards
     * @param clientId client id using the tool card
     * @return values chosen by the player
     */
    public ArrayList<Integer> getPositionInPatternCard(int clientId){
        return room.getPositionInPatternCard(clientId);
    }

    /**
     * method invoked by the controller on view for tool cards
     * @param clientId client id using the tool card
     * @return values chosen by the player
     */
    public ArrayList<Integer> getIncrementedValue(int clientId) {return room.getIncrementedValue(clientId);}

    /**
     * method invoked by the controller on view for tool cards
     * @param clientId client id using the tool card
     * @return values chosen by the player
     */
    public Integer getDieFromDiceArena(int clientId) { return room.getDieFromDiceArena(clientId);}

    /**
     * method invoked by the controller on view for tool cards
     * @param clientId client id using the tool card
     * @return values chosen by the player
     */
    public ArrayList<Integer> getDieFromRoundTrack(int clientId) { return  room.getDieFromRoundTrack(clientId);}

    /**
     * method invoked by the controller on view for tool cards
     * @param clientId client id using the tool card
     * @return values chosen by the player
     */
    public Integer getValueForDie(int clientId) { return  room.getValueForDie(clientId);}

    /**
     * method invoked by the controller on view for tool cards
     * @param clientId client id using the tool card
     * @return values chosen by the player
     */
    public ArrayList<Integer> getDoublePositionInPatternCard(int clientId) { return room.getDoublePositionInPatternCard(clientId);}

    /**
     * method invoked by the controller on view for tool cards
     * @param clientId client id using the tool card
     * @return values chosen by the player
     */
    public ArrayList<Integer> getSinglePositionInPatternCard(int clientId, ArrayList<Integer> listOfAvailablePositions) { return room.getSinglePositionInPatternCard(clientId, listOfAvailablePositions); }

    /**
     * method invoked by the controller on view for tool cards
     * @param clientId client id using the tool card
     * @return values chosen by the player
     */
    public void block(int clientId) { room.block(clientId);}

    /**
     * method invoked by the controller on view for tool cards
     * @param clientId client id using the tool card
     * @return values chosen by the player
     */
    public void free(int clientId) { room.free(clientId);}

}
