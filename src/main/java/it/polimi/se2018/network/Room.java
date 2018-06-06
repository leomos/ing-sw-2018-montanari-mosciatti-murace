package it.polimi.se2018.network;

import it.polimi.se2018.controller.Controller;
import it.polimi.se2018.model.Model;
import it.polimi.se2018.view.VirtualView;

import java.util.ArrayList;
import java.util.List;

public class Room extends Thread {

    private Model model;

    private VirtualView view;

    private Controller controller;

    private List<ClientInterface> clientInterfaces;

    public Room() {
        clientInterfaces = new ArrayList<>();
    }

    @Override
    public void run() {

    }

    public void addClient(ClientInterface clientInterface) {
        clientInterfaces.add(clientInterface);
    }

}
