package it.polimi.se2018.controller;

import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.events.PlayerMessageNotAFK;

public class AfkController {

    private Model model;

    public AfkController(Model model){
        this.model = model;
    }

    public void execute(PlayerMessageNotAFK playerMessageNotAFK){
        this.model.setPlayerSuspended(playerMessageNotAFK.getPlayerId(), false);
    }
}
