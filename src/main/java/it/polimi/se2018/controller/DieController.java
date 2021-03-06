package it.polimi.se2018.controller;

import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.events.PlayerMessageDie;

public class DieController  {

    private Model model;

    public DieController(Model model){
        this.model = model;
    }

    /**
     * performs the player move by modifying the model
     * @param playerMessageDie message passed from controller containing the information required to perform the move
     * @throws
     */
    public void execute(PlayerMessageDie playerMessageDie) {

        int idPlayer = playerMessageDie.getPlayerId();

        if(model.isMyTurn(idPlayer)) {
            int idDie = playerMessageDie.getDie();
            int x = playerMessageDie.getX_position();
            int y = playerMessageDie.getY_position();

            this.model.setDieInPatternCardFromDiceArena(idPlayer, idDie, x, y, false, -1);
        }
    }
}
