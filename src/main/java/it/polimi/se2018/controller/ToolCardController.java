package it.polimi.se2018.controller;

import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.container.DiceContainerUnsupportedIdException;
import it.polimi.se2018.model.events.PlayerMessageToolCard;
import it.polimi.se2018.view.ViewClient;

import java.util.HashMap;

public class ToolCardController{

    private Model model;

    public ToolCardController(Model model){
        this.model = model;
    }

    public void execute(PlayerMessageToolCard playerMessageToolCard, HashMap<Integer, ViewClient> view) throws DiceContainerUnsupportedIdException {
        int player = playerMessageToolCard.getPlayer();
        int toolCardId = playerMessageToolCard.getToolcard().getToolCardId();
        ViewClient actualView = view.get(player);

        /*TODO: check segnalini prima di chiedere dati */
        if( toolCardId == 1){

            boolean incremented_value = actualView.getIncrementedValue();

        }else if(toolCardId == 2) {
            Integer[] startingPosition = actualView.getPositionInPatternCard();
            Integer[] finalPosition = actualView.getPositionInPatternCard();

            model.moveDieInsidePatternCard(player, startingPosition[0], startingPosition[1], finalPosition[0], finalPosition[1], false, true, 2);
        } else if(toolCardId == 3) {
            Integer[] startingPosition = actualView.getPositionInPatternCard();
            Integer[] finalPosition = actualView.getPositionInPatternCard();

            model.moveDieInsidePatternCard(player, startingPosition[0], startingPosition[1], finalPosition[0], finalPosition[1], true, false, 3);
        } else if(toolCardId == 4) {
            Integer[] startingPosition1 = actualView.getPositionInPatternCard();
            Integer[] finalPosition1 = actualView.getPositionInPatternCard();

            Integer[] startingPosition2 = actualView.getPositionInPatternCard();
            Integer[] finalPosition2 = actualView.getPositionInPatternCard();

            model.moveDieInsidePatternCard(player, startingPosition1[0], startingPosition1[1], finalPosition1[0], finalPosition1[1], false, false,4);
            model.moveDieInsidePatternCard(player, startingPosition2[0], startingPosition2[1], finalPosition2[0], finalPosition2[1], false, false,4);
        }
    }
}
