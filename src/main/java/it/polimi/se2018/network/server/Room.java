package it.polimi.se2018.network.server;

import it.polimi.se2018.controller.Controller;
import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.events.PlayerMessage;
import it.polimi.se2018.network.ClientInterface;
import it.polimi.se2018.view.VirtualView;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class Room {

    private Model model;

    private VirtualView view;

    private Controller controller;

    private List<ClientInterface> clientInterfaces;

    public Room() {
        clientInterfaces = new ArrayList<>();
    }

    public void start() {
        System.out.println("Room started!");
        for(ClientInterface clientInterface : clientInterfaces) {
            try {
                System.out.println(clientInterface.getDieFromPatternCard());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {

    }

    public void addClient(ClientInterface clientInterface) {
        clientInterfaces.add(clientInterface);
        try {
            clientInterface.setRoom(this);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void notifyView(Object playerMessage) {
        System.out.println("arrivato " + playerMessage.toString());
        //view.notify((PlayerMessage) playerMessage);
    }

}
