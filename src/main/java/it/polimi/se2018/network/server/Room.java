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
import java.util.logging.Logger;
import java.util.stream.Stream;


/**
 * A process that puts in communication various clients, creates the Model, the Controller and the VirtualView.
 */
public class Room extends Thread {

    private static final Logger LOGGER = Logger.getLogger(SimpleRoomDispatcherImplementation.class.getName());

    private Model model;

    private VirtualView view;

    private Controller controller;

    private Set<ConnectedClient> players;

    private MessageVisitorInterface messageVisitorUpdate;

    private int turnCountdownLength;

    private boolean isRunning = true;


    /**
     * Returns and ArrayList of the ConnectedClients in players with isInactive == false
     * @return and ArrayList of the ConnectedClients in players with isInactive == false
     */
    private ArrayList<ConnectedClient> activePlayers() {
        ArrayList<ConnectedClient> result = new ArrayList<>();
        for(ConnectedClient c : players) {
            if(!c.isInactive())
                result.add(c);
        }
        return result;
    }

    /**
     * Returns and ArrayList of the ConnectedClients in players with isInactive == true
     * @return and ArrayList of the ConnectedClients in players with isInactive == true
     */
    private Stream<ConnectedClient> inactivePlayers() {
        return players.stream()
                .filter(player -> player.isInactive());
    }

    /**
     * Returns an active ConnectedClient with a specific id in players
     * @param id integer representing the id to search for
     * @return an active ConnectedClient with a specific id in players
     * @throws IllegalStateException when there are no active players
     */
    private ConnectedClient connectedClientById(int id) throws IllegalStateException {
        for (ConnectedClient c : activePlayers()) {
            if(c.getId() == id) return c;
        }
        throw new IllegalStateException();
    }

    /**
     * Returns an inactive ConnectedClient with a specific id in players
     * @param id integer representing the id to search for
     * @return an inactive ConnectedClient with a specific id in players
     * @throws IllegalStateException when there are no inactive players
     */
    public ConnectedClient inactiveClientById(int id) {
        return inactivePlayers()
                .filter(player -> player.getId() == id)
                .findFirst()
                .get();
    }


    /**
     * Returns a map of the players where the key is the id, and value is the name of the player
     * @return a map of the players where the key is the id, and value is the name of the player
     */
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


    /**
     * Creates model, controller and view, does reciprocal registration for observer pattern and initialize model
     * setup phase
     */
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


    /**
     * Notifies view created in run
     * @param playerMessage message to pass to the view
     */
    public void notifyView(Message playerMessage) {
        playerMessage.accept(messageVisitorUpdate);
        view.callNotify((PlayerMessage) playerMessage);
    }


    /**
     * Sends an UpdateMessage to every player
     * @param updateMessage
     */
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
        } catch (IllegalStateException e) {
            LOGGER.severe("No connected clients left in room.");
        }
        return new ArrayList<>();
    }

    public ArrayList<Integer> getIncrementedValue(int idClient) {
        try {
            return connectedClientById(idClient)
                    .getClientInterface()
                    .getIncrementedValue();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            LOGGER.severe("No connected clients left in room.");
        }
        return new ArrayList<>();
    }

    public ArrayList<Integer> getDoublePositionInPatternCard(int idClient){
        try {
            return connectedClientById(idClient)
                    .getClientInterface()
                    .getDoublePositionInPatternCard();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            LOGGER.severe("No connected clients left in room.");
        }
        return new ArrayList<>();
    }


    public ArrayList<Integer> getSinglePositionInPatternCard(int idClient, ArrayList<Integer> listOfAvailablePositions){
        try {
            return connectedClientById(idClient)
                    .getClientInterface()
                    .getSinglePositionInPatternCard(listOfAvailablePositions);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            LOGGER.severe("No connected clients left in room.");
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
        }  catch (IllegalStateException e) {
            LOGGER.severe("No connected clients left in room.");
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
        } catch (IllegalStateException e) {
            LOGGER.severe("No connected clients left in room.");
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
        } catch (IllegalStateException e) {
            LOGGER.severe("No connected clients left in room.");
        }
        return new ArrayList<>();
    }

    public void block(int idClient){
        try {
            connectedClientById(idClient)
                    .getClientInterface()
                    .block();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            LOGGER.severe("No connected clients left in room.");
        }
    }

    public void free(int idClient){
        try {
            connectedClientById(idClient)
                    .getClientInterface()
                    .free();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            LOGGER.severe("No connected clients left in room.");
        }
    }


    /**
     * Called by the dispatcher to notify the model about the disconnection of a client
     * @param id id of the player that has disconnected
     */
    public synchronized void handleClientDisconnection(int id) {
        System.out.println("Client " + id + " disconnected!");
        connectedClientById(id).setInactive(true);

        model.setPlayerSuspended(id, true);

        if(model.currentPlayerPlaying() == id) {
            Message message = new PlayerMessageEndTurn(id);
            notifyView(message);
        }
    }

    /**
     * Called by the dispatcher when a client tries to reconnect.
     * Notifies the model about the riconnection.
     * @param clientInterface interface of the client that wants to reconnect
     * @param id id of the player that is trying to reconnect
     * @return true if the clients is able to reconnect, false otherwise
     */
    public Boolean  reconnectPlayer(ClientInterface clientInterface, int id) {
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


    /**
     * Sends all the necessary information to the client to get back in the game
     * @param id id of the player that reconnected
     */
    public void sendGameStateToReconnectedPlayer(int id) {
        model.updatePlayerThatCameBackIntoTheGame(id);
    }


    /**
     * Stops the room
     */
    public void dispose() {
        isRunning = false;
    }
}
