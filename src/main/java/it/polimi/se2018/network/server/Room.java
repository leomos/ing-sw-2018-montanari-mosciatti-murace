package it.polimi.se2018.network.server;

import it.polimi.se2018.controller.Controller;
import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.events.Message;
import it.polimi.se2018.model.events.ModelChangedMessage;
import it.polimi.se2018.model.events.ModelChangedMessageConnected;
import it.polimi.se2018.model.events.PlayerMessageSetup;
import it.polimi.se2018.network.ConnectedClient;
import it.polimi.se2018.network.client.ClientInterface;
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

    public Room(VirtualView virtualView) {
        this.virtualView = virtualView;
    }

    public void initGame() {
        for(ClientInterface clientInterface: virtualView.getClientInterfaceList()) {
            id++;
            ModelChangedMessage modelChangedMessageConnected = new ModelChangedMessageConnected(id);
            try {
                clientInterface.update(modelChangedMessageConnected);
                String name = clientInterface.askForName();
                clientsList.put(id, name);
                System.out.println(id + " " + name);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void start() {
        //while(gamePlaying) {
            Model model = new Model();
            Controller controller = new Controller(model);
            model.register(virtualView);
            virtualView.register(controller);
            controller.addView(virtualView);
            //need to add view to controller!!

            model.initSetup(clientsList);

            //patterncard choise

            for(ClientInterface clientInterface: virtualView.getClientInterfaceList()){
                try {
                    PlayerMessageSetup data = clientInterface.askForPatternCard();
                    virtualView.callNotify(data);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            model.initGame(clientsList);

            //start rounds

            for(int i = 0; i < clientsList.size() * 2 * 10; i++) //SBAGLIATO?
                for(ClientInterface clientInterface: virtualView.getClientInterfaceList()){
                    PlayerMessage data;
                    try {
                        do {
                            data = clientInterface.askForMove();
                            virtualView.callNotify(data);
                        }
                        while(data instanceof PlayerMessageEndTurn);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }





        //}
    }

    public void addPlayers(Set<ConnectedClient> players) {
        this.players = players;
    }

    public void notifyView(Message playerMessage) {
        System.out.println("arrivato " + playerMessage.toString());
        //view.notify((PlayerMessage) playerMessage);
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


}
