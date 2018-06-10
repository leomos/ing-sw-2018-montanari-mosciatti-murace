package it.polimi.se2018.controller;

import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.container.DiceContainerUnsupportedIdException;
import it.polimi.se2018.model.events.*;
import it.polimi.se2018.utils.Observer;
import it.polimi.se2018.view.VirtualView;

public class Controller implements Observer<PlayerMessage> {

    protected Model model;

    private VirtualView view;

    private ToolCardController toolCardController;

    private DieController dieController;

    private SetUpController setUpController;

    private EndTurnController endTurnController;

    /**
     * @param view
     */
    /* TODO: tests */
    public void addView(VirtualView view){
        this.view = view;
    }

    public Controller(Model model){
        this.model = model;
        this.dieController = new DieController(model);
        this.setUpController = new SetUpController(model);
        this.endTurnController = new EndTurnController(model);
        this.toolCardController = new ToolCardController(model);
    }

    /**
     * @param /view network that sends the message
     * @param playerMessage message containing the information about player's move
     */
    /* TODO: tests, finish update without instanceOf(). */
    @Override
    public void update(PlayerMessage playerMessage){

        if(playerMessage instanceof PlayerMessageSetup){
            PlayerMessageSetup playerMessageSetup = (PlayerMessageSetup) playerMessage;

            setUpController.execute(playerMessageSetup);

        }


        if(playerMessage instanceof PlayerMessageDie) {
            PlayerMessageDie playerMessageDie = (PlayerMessageDie) playerMessage;
            try {
                this.dieController.execute(playerMessageDie);
            } catch (DiceContainerUnsupportedIdException e) {
                e.printStackTrace();
            }
        }

        if(playerMessage instanceof PlayerMessageToolCard) {
            PlayerMessageToolCard playerMessageToolCard = (PlayerMessageToolCard) playerMessage;
            this.toolCardController.execute(playerMessageToolCard, view);
        }

        if(playerMessage instanceof PlayerMessageEndTurn) {
            PlayerMessageEndTurn playerMessageEndTurn = (PlayerMessageEndTurn) playerMessage;
            this.endTurnController.execute(playerMessageEndTurn);
        }

    }
}
