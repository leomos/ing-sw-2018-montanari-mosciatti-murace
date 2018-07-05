package it.polimi.se2018.network.server;

import it.polimi.se2018.controller.Controller;
import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.events.*;
import it.polimi.se2018.network.ClientInterface;
import it.polimi.se2018.network.ConnectedClient;
import it.polimi.se2018.network.visitor.MessageVisitorImplementationUpdate;
import it.polimi.se2018.network.visitor.MessageVisitorInterface;
import it.polimi.se2018.view.VirtualView;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.stream.Stream;

public class Room extends Thread {

    private Model model;

    private VirtualView view;

    private Controller controller;

    private Set<ConnectedClient> players;

    private MessageVisitorInterface messageVisitorUpdate;

    private int turnCountdownLength;

    private boolean isRunning = true;


    private Stream<ConnectedClient> activePlayers() {
        return players.stream()
                .filter(player -> !player.isInactive());
    }

    private Stream<ConnectedClient> inactivePlayers() {
        return players.stream()
                .filter(player -> player.isInactive());
    }

    private ConnectedClient connectedClientById(int id) {
        return activePlayers()
                .filter(player -> player.getId() == id)
                .findFirst()
                .get();
    }

    public ConnectedClient inactiveClientById(int id) {
        return inactivePlayers()
                .filter(player -> player.getId() == id)
                .findFirst()
                .get();
    }

    private HashMap<Integer, String> createClientsMap() {
        HashMap<Integer, String> clientsMap = new HashMap<>();
        players.forEach(player -> {
            clientsMap.put(player.getId(), player.getName());
        });
        return clientsMap;
    }

    public Room(int turnCountdownLength) {
        this.turnCountdownLength = turnCountdownLength;
    }

    @Override
    public void run() {
        HashMap<Integer, String> clientsMap = createClientsMap();
        messageVisitorUpdate = new MessageVisitorImplementationUpdate(this);
        model = new Model(clientsMap, turnCountdownLength);
        controller = new Controller(model);
        view = new VirtualView();
        model.register(view);
        view.register(controller);
        controller.addView(view);

        view.setRoom(this);

        model.initSetup();
    }

    public void addPlayers(Set<ConnectedClient> players) {
        this.players = players;
    }

    public void notifyView(Message playerMessage) {
        playerMessage.accept(messageVisitorUpdate);
        view.callNotify((PlayerMessage) playerMessage);
    }

    public void updatePlayers(Message updateMessage) {

        ((MessageVisitorImplementationUpdate)messageVisitorUpdate).setCurrentPlayerPlayingId(model.currentPlayerPlaying());

        players.forEach((player) -> {
            ((MessageVisitorImplementationUpdate)messageVisitorUpdate).setPlayer(player);
            updateMessage.accept(messageVisitorUpdate);
        });
    }

    public ArrayList<Integer> getPositionInPatternCard(int idClient){
        try {
            return connectedClientById(idClient)
                    .getClientInterface()
                    .getPositionInPatternCard();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Integer> getIncrementedValue(int idClient) {
        try {
            return connectedClientById(idClient)
                    .getClientInterface()
                    .getIncrementedValue();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Integer> getDoublePositionInPatternCard(int idClient){
        try {
            return connectedClientById(idClient)
                    .getClientInterface()
                    .getDoublePositionInPatternCard();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }


    public ArrayList<Integer> getSinglePositionInPatternCard(int idClient, ArrayList<Integer> listOfAvailablePositions){
        try {
            return connectedClientById(idClient)
                    .getClientInterface()
                    .getSinglePositionInPatternCard(listOfAvailablePositions);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public Integer getDieFromDiceArena(int idClient){
        try {
            return connectedClientById(idClient)
                    .getClientInterface()
                    .getDieFromDiceArena();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Integer getValueForDie(int idClient){
        try {
            return connectedClientById(idClient)
                    .getClientInterface()
                    .getValueForDie();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Integer> getDieFromRoundTrack(int idClient){
        try {
            return connectedClientById(idClient)
                    .getClientInterface()
                    .getDieFromRoundTrack();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void block(int idClient){
        try {
            connectedClientById(idClient)
                    .getClientInterface()
                    .block();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void free(int idClient){
        try {
            connectedClientById(idClient)
                    .getClientInterface()
                    .free();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public synchronized void handleClientDisconnection(int id) {
        System.out.println("Client " + id + " disconnected!");
        connectedClientById(id).setInactive(true);

        model.setPlayerSuspended(id, true);

        if(model.currentPlayerPlaying() == id) {
            Message message = new PlayerMessageEndTurn(id);
            notifyView(message);
        }
    }

    public Boolean reconnectPlayer(ClientInterface clientInterface, int id) {
        if(!isRunning) return false;
        ConnectedClient reconnectedClient = inactivePlayers()
                .filter(player -> player.getId() == id)
                .findFirst()
                .get();
        reconnectedClient.setInactive(false);
        reconnectedClient.setClientInterface(clientInterface);
        model.setPlayerSuspended(id, false);
        return true;
    }

    public void sendGameStateToReconnectedPlayer(int id) {
        model.updatePlayerThatCameBackIntoTheGame(id);
    }

    public void dispose() {
        isRunning = false;
    }
}
