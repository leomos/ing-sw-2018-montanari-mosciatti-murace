package it.polimi.se2018.network.server;

import it.polimi.se2018.controller.Controller;
import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.events.*;
import it.polimi.se2018.network.ConnectedClient;
import it.polimi.se2018.view.VirtualView;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class Room {

    private Model model;

    private VirtualView view;

    private Controller controller;

    private Set<ConnectedClient> players;

    private Map<Integer, Long> heartbeats;

    public void start() {
        //while(gamePlaying) {
            HashMap<Integer, String> clientsMap = createClientsMap();
            heartbeats = new HashMap<>();
            model = new Model(clientsMap);
            controller = new Controller(model);
            view = new VirtualView();
            model.register(view);
            view.register(controller);
            controller.addView(view);
            //need to add view to controller!!
            players.forEach(player -> {
                view.addClientInterface(player.getClientInterface());
            });

            model.initSetup();

            heartbeats = initHeartbeats();
            //patterncard choise

            players.forEach(player -> {
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

            /* TODO: non usare PlayerMessage */
            /*for(int i = 0; i < clientsMap.size() * 2 * 10; i++) //SBAGLIATO?
                for(ClientInterface clientInterface: view.getClientInterfaceList()){
                    PlayerMessage data;
                    try {
                        do {
                            data = clientInterface.askForMove();
                            view.callNotify(data);
                        }
                        while(data instanceof PlayerMessageEndTurn);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }*/





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

    private HashMap<Integer, String> createClientsMap() {
        HashMap<Integer, String> clientsMap = new HashMap<>();
        players.forEach(player -> {
            clientsMap.put(player.getId(), player.getName());
        });
        return clientsMap;
    }

    public void heartbeat(int id) {
        System.out.println(id + " | " + (System.nanoTime()-heartbeats.get(id)));
        heartbeats.put(id, System.nanoTime());

    }

    private HashMap<Integer, Long> initHeartbeats() {
        HashMap<Integer, Long> heartbeats = new HashMap<>();
        players.forEach(player -> {
            heartbeats.put(player.getId(), Long.valueOf(0));
        });
        return heartbeats;
    }

}
