package it.polimi.se2018.controller;

import it.polimi.se2018.model.Model;
import it.polimi.se2018.view.View;
import it.polimi.se2018.model.events.*;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

public class Controller implements Observer {

    protected Model model;

    private HashMap<Integer, View> view;

    private ToolCardController toolCardController;

    private DieController dieController;

    private SetUpController setUpController;

    private EndTurnController endTurnController;

    /**
     * @param idPlayer
     * @param view
     */
    /* TODO: tests, add for HashMap. */
    public void addView(int idPlayer, View view){
        return;
    }

    public Controller(Model model){
        this.model = model;
        this.dieController = new DieController(model);
        this.setUpController = new SetUpController(model);
        this.endTurnController = new EndTurnController(model);
        this.toolCardController = new ToolCardController(model);
    }

    /**
     * @param view view that sends the message
     * @param playerMessage message containing the information about player's move
     */
    /* TODO: tests, finish update with instanceOf(). */
    @Override
    public void update(Observable view, Object playerMessage){

        if(playerMessage instanceof PlayerMessageDie) {
            PlayerMessageDie playerMessageDie = (PlayerMessageDie) playerMessage;
            this.dieController.execute(playerMessageDie);
        }

    }
}
