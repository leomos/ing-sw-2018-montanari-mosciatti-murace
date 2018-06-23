package it.polimi.se2018.network.server;

import it.polimi.se2018.controller.Controller;
import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.events.Message;
import it.polimi.se2018.model.events.ModelChangedMessage;
import it.polimi.se2018.model.events.PlayerMessage;
import it.polimi.se2018.model.events.PlayerMessageSetup;
import it.polimi.se2018.network.ConnectedClient;
import it.polimi.se2018.view.VirtualView;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Room {

    private Model model;

    private VirtualView view;

    private Controller controller;

    private Set<ConnectedClient> players;


    public void start() {
        //while(gamePlaying) {
            HashMap<Integer, String> clientsMap = createClientsMap();
            model = new Model(clientsMap);
            controller = new Controller(model);
            view = new VirtualView();
            model.register(view);
            view.register(controller);
            controller.addView(view);

            view.setRoom(this);

            model.initSetup();
            //patterncard choise

            players.parallelStream().forEach(player -> {
                try {
                    Integer m = player.getClientInterface().askForPatternCard();
                    PlayerMessage playerMessage = new PlayerMessageSetup(player.getId(), m);
                    view.callNotify(playerMessage);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            });

            System.out.println("Starting game in model");

            model.initGame();

            //start rounds
        //}
    }

    public void stop() {

    }

    public void addPlayers(Set<ConnectedClient> players) {
        this.players = players;
    }

    public void notifyView(Message playerMessage) {
        System.out.println("arrivato " + playerMessage.toString());
        view.callNotify((PlayerMessage) playerMessage);
    }



    public void updatePlayers(Message updateMessage) {
        players.forEach((player) -> {
            try {
                player.getClientInterface().update((ModelChangedMessage) updateMessage);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }

    public ArrayList<Integer> getPositionInPatternCard(int idClient){
        for(ConnectedClient player : players) {
            if(player.getId() == idClient) {
                try {
                    return player.getClientInterface().getPositionInPatternCard();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public ArrayList<Integer> getIncrementedValue(int idClient){
        for(ConnectedClient player : players) {
            if(player.getId() == idClient) {
                try {
                    return player.getClientInterface().getIncrementedValue();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public ArrayList<Integer> getSinglePositionInPatternCard(int idClient){
        for(ConnectedClient player : players) {
            if(player.getId() == idClient) {
                try {
                    return player.getClientInterface().getSinglePositionInPatternCard();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public Integer getDieFromDiceArena(int idClient){
        for(ConnectedClient player : players) {
            if(player.getId() == idClient) {
                try {
                    return player.getClientInterface().getDieFromDiceArena();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public Integer getValueForDie(int idClient){
        for(ConnectedClient player : players) {
            if(player.getId() == idClient) {
                try {
                    return player.getClientInterface().getValueForDie();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public Integer getDieFromRoundTrack(int idClient){
        for(ConnectedClient player : players) {
            if(player.getId() == idClient) {
                try {
                    return player.getClientInterface().getDieFromRoundTrack();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public void block(int idClient){
        for(ConnectedClient player : players) {
            if(player.getId() == idClient) {
                try {
                    player.getClientInterface().block();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void free(int idClient){
        for(ConnectedClient player : players) {
            if(player.getId() == idClient) {
                try {
                    player.getClientInterface().free();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private HashMap<Integer, String> createClientsMap() {
        HashMap<Integer, String> clientsMap = new HashMap<>();
        players.forEach(player -> {
            clientsMap.put(player.getId(), player.getName());
        });
        return clientsMap;
    }
}
