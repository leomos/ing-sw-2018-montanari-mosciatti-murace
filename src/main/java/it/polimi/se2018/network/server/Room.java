package it.polimi.se2018.network.server;

import it.polimi.se2018.controller.Controller;
import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.events.ModelChangedMessage;
import it.polimi.se2018.model.events.ModelChangedMessageConnected;
import it.polimi.se2018.model.events.PlayerMessageSetup;
import it.polimi.se2018.network.ClientInterface;
import it.polimi.se2018.view.VirtualView;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

public class Room extends Thread {
    private HashMap<Integer, String> clientsList = new HashMap<>();
    private VirtualView virtualView;
    private Model model;
    private Controller controller;

    private boolean gamePlaying = true;
    private int id = 0;

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

    public void run() {
        //while(gamePlaying) {
            System.out.println("arrivato");
            Model model = new Model();
            Controller controller = new Controller(model);
            model.register(virtualView);
            virtualView.register(controller);

            model.init(clientsList);

            for(ClientInterface clientInterface: virtualView.getClientInterfaceList()){
                try {
                    ArrayList<Integer> data = clientInterface.askForPatternCard();
                    virtualView.notify(new PlayerMessageSetup(data.get(0),
                                        data.get(1)));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }




        //}
    }


}
