package it.polimi.se2018.controller;

import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.events.PlayerMessageToolCard;
import it.polimi.se2018.view.View;

import java.util.HashMap;

public class ToolCardController extends Controller {

    public ToolCardController(Model model){
        super(model);
    }

    public void execute(PlayerMessageToolCard playerMessageToolCard, HashMap<Integer, View> view){
        int player = playerMessageToolCard.getPlayer();
        int toolCardId = playerMessageToolCard.getToolcard().getToolCardId();
        View actualView = view.get(player);

        /*TODO: check segnalini prima di chiedere dati */

        if(toolCardId == 2) {
            int[] startingPosition = actualView.getDiePosition();
            int[] finalPosition = actualView.getDiePosition();

            model.moveDieInsidePatternCard(player, startingPosition[0], startingPosition[1], finalPosition[0], finalPosition[1], false, true, 2);
        } else if(toolCardId == 3) {
            int[] startingPosition = actualView.getDiePosition();
            int[] finalPosition = actualView.getDiePosition();

            model.moveDieInsidePatternCard(player, startingPosition[0], startingPosition[1], finalPosition[0], finalPosition[1], true, false, 3);
        } else if(toolCardId == 4) {
            int[] startingPosition1 = actualView.getDiePosition();
            int[] finalPosition1 = actualView.getDiePosition();

            int[] startingPosition2 = actualView.getDiePosition();
            int[] finalPosition2 = actualView.getDiePosition();

            model.moveDieInsidePatternCard(player, startingPosition1[0], startingPosition1[1], finalPosition1[0], finalPosition1[1], false, false,4);
            model.moveDieInsidePatternCard(player, startingPosition2[0], startingPosition2[1], finalPosition2[0], finalPosition2[1], false, false,4);
        }
    }
}
