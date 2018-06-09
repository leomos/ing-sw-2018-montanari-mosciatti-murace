package it.polimi.se2018.controller;

import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.events.PlayerMessageEndTurn;

public class EndTurnController  {

    Model model;

    public EndTurnController(Model model){
        this.model = model;
    }

    public void execute(PlayerMessageEndTurn playerMessageEndTurn){

        int idPlayer = playerMessageEndTurn.getPlayer();

        model.endTurn(playerMessageEndTurn);
    }
}
