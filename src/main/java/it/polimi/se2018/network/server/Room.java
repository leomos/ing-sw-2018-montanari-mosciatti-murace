package it.polimi.se2018.network.server;

import it.polimi.se2018.controller.Controller;
import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.events.*;
import it.polimi.se2018.network.client.ClientInterface;
import it.polimi.se2018.view.VirtualView;

import java.rmi.RemoteException;
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
            Model model = new Model();
            Controller controller = new Controller(model);
            model.register(virtualView);
            virtualView.register(controller);

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


}
