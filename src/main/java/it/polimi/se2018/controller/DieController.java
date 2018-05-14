package it.polimi.se2018.controller;

import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.container.DiceContainerUnsupportedIdException;
import it.polimi.se2018.model.events.PlayerMessageDie;

public class DieController extends Controller {

    public DieController(Model model){
        super(model);
    }

    /**
     * checks if the die placement is available and in that case performs the player move by modifying the model
     * @param playerMessageDie message passed from controller containing the information required to perform the move
     * @throws
     */
    /* TODO: tests, check if there is a die in adjacent position + exception*/
    public void execute(PlayerMessageDie playerMessageDie) {

        int idPlayer = playerMessageDie.getPlayer();
        int idDie = playerMessageDie.getDie();
        int x = playerMessageDie.getPosition()[0];
        int y = playerMessageDie.getPosition()[1];

        try {
            if (this.model.getTable().getPlayers(idPlayer).getChosenPatternCard().checkProximityCellsValidity(idDie, x, y))
                this.model.getTable().getPlayers(idPlayer).getChosenPatternCard()
                        .getPatternCardCell(x, y)
                        .setRolledDieId(idDie, false, false);

        } catch (DiceContainerUnsupportedIdException o){

        }
    }
}
