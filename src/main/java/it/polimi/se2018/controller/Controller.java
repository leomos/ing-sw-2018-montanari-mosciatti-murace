package it.polimi.se2018.controller;

import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.events.*;
import it.polimi.se2018.network.visitor.MessageVisitorImplementationController;
import it.polimi.se2018.utils.Observer;
import it.polimi.se2018.view.VirtualView;

public class Controller implements Observer<PlayerMessage> {

    protected Model model;

    private VirtualView view;

    private ToolCardController toolCardController;

    private DieController dieController;

    private SetUpController setUpController;

    private EndTurnController endTurnController;

    private AfkController afkController;

    private MessageVisitorImplementationController messageVisitorImplementationController;

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
        this.afkController = new AfkController(model);
        this.messageVisitorImplementationController = new MessageVisitorImplementationController(this);
    }

    /**
     * @param /view network that sends the message
     * @param playerMessage message containing the information about player's move
     */
    @Override
    public synchronized void update(PlayerMessage playerMessage) {
        playerMessage.accept(messageVisitorImplementationController);
    }

    public void execute(PlayerMessageDie playerMessageDie) {
        this.dieController.execute(playerMessageDie);
    }

    public void execute(PlayerMessageToolCard playerMessageToolCard) {
        this.toolCardController.execute(playerMessageToolCard, view);
    }

    public void execute(PlayerMessageEndTurn playerMessageEndTurn) {
        this.endTurnController.execute(playerMessageEndTurn);
    }

    public void execute(PlayerMessageSetup playerMessageSetup) {
        this.setUpController.execute(playerMessageSetup);
    }

    public void execute(PlayerMessageNotAFK playerMessageNotAFK) {
        this.afkController.execute(playerMessageNotAFK);
    }


}
