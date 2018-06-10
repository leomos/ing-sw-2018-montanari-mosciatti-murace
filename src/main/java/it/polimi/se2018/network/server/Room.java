package it.polimi.se2018.network.server;

import it.polimi.se2018.controller.Controller;
import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.events.Message;
import it.polimi.se2018.model.events.ModelChangedMessage;
import it.polimi.se2018.network.ConnectedClient;
import it.polimi.se2018.view.VirtualView;

import java.rmi.RemoteException;
import java.util.Set;

public class Room {

    private Model model;

    private VirtualView view;

    private Controller controller;

    private Set<ConnectedClient> players;


    public void start() {
        /* TODO: start game procedures */
        System.out.println("Room started!");
        /*players.forEach(player -> {
            try {
                System.out.println(player.getClientInterface().getDieFromPatternCard());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });*/
    }

    public void stop() {

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
