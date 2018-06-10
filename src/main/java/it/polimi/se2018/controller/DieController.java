package it.polimi.se2018.controller;

import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.container.DiceContainerUnsupportedIdException;
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
    /* TODO: tests, check if there is a die in adjacent position + exception*/
    public void execute(PlayerMessageDie playerMessageDie) throws DiceContainerUnsupportedIdException {

        int idPlayer = playerMessageDie.getPlayerId();
        int idDie = playerMessageDie.getDie();
        int x = playerMessageDie.getPosition()[0];
        int y = playerMessageDie.getPosition()[1];

        this.model.setDieInPatternCard(idPlayer, idDie, x, y, false, false);

    }
}
