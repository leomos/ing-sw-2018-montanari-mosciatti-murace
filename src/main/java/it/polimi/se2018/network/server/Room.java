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
        //while(gamePlaying) {
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
            //patterncard choise

        /*
            players.parallelStream().forEach(player -> {
                try {
                    Integer m = player.getClientInterface().askForPatternCard();
                    PlayerMessage playerMessage = new PlayerMessageSetup(player.getId(), m);
                    view.callNotify(playerMessage);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            });
            */


            //model.initGame();

            //start rounds
        //}
    }

    public void addPlayers(Set<ConnectedClient> players) {
        this.players = players;
    }

    public void notifyView(Message playerMessage) {

        //se il messaggio è un notAFK, allora setto il giocatore in attivo e gli mando un refresh
        //così si "riaggiorna" con ciò che è successo in sua assenza;
        //bisogna togliere INSTANCE OF

        if(playerMessage instanceof PlayerMessageNotAFK) {
            players.forEach((player) -> {
                if (((PlayerMessageNotAFK) playerMessage).getPlayer() == player.getId()) {
                    player.setInactive(false);

                    model.updatePlayerThatCameBackIntoTheGame(player.getId());
                }
            });
        }

        view.callNotify((PlayerMessage) playerMessage);
    }



    public void updatePlayers(Message updateMessage) {

        //se il messaggio è playerAFK, vuol dire che il time out del timerSuspendThreshold è scaduto, quindi metto il giocatore in
        //AFK inoltrando solo a lui questo tipo di messaggio. a dire la verità lo inoltra due volte?
        //bisogna togliere il INSTANCE OF e si può mettere nel for sotto volendo

        /*if(updateMessage instanceof ModelChangedMessagePlayerAFK){
            players.forEach((player) -> {

                if(((ModelChangedMessagePlayerAFK) updateMessage).getPlayer().equals(Integer.toString(player.getId()))) {
                    try {
                        player.setInactive(true);
                        System.out.println("giocatore INACTIVE");
                        player.getClientInterface().update((ModelChangedMessage) updateMessage);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }

            });
        }*/

        ((MessageVisitorImplementationUpdate)messageVisitorUpdate).setCurrentPlayerPlayingId(model.currentPlayerPlaying());

        players.forEach((player) -> {
            ((MessageVisitorImplementationUpdate)messageVisitorUpdate).setPlayer(player);
            updateMessage.accept(messageVisitorUpdate);
            /*try {
                if(!player.isInactive()) {
                    player.getClientInterface().update((ModelChangedMessage) updateMessage);
                } else {
                    if(player.getId() == model.currentPlayerPlaying()) {
                        updateMessage.accept(messageVisitorUpdate);
                    }
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }*/
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
}
