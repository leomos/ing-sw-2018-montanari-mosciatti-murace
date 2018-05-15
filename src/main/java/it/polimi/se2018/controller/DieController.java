package it.polimi.se2018.controller;

import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.events.PlayerMessageDie;

public class DieController extends Controller {

    public DieController(Model model){
        super(model);
    }

    /**
     * performs the player move by modifying the model
     * @param playerMessageDie message passed from controller containing the information required to perform the move
     * @throws
     */
    /* TODO: tests, check if there is a die in adjacent position + exception*/
    public void execute(PlayerMessageDie playerMessageDie) {

        int idPlayer = playerMessageDie.getPlayer();
        int idDie = playerMessageDie.getDie();
        int x = playerMessageDie.getPosition()[0];
        int y = playerMessageDie.getPosition()[1];

        this.model.setDieInPatternCard(idPlayer, idDie, x, y, false, false);

    }
}