package it.polimi.se2018.controller;

import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.events.PlayerMessageToolCard;
import it.polimi.se2018.view.VirtualView;

import java.util.ArrayList;

public class ToolCardController{

    private Model model;

    public ToolCardController(Model model){
        this.model = model;
    }

    public void execute(PlayerMessageToolCard playerMessageToolCard, VirtualView view){
        int idPlayer = playerMessageToolCard.getPlayer();
        int idToolCard = playerMessageToolCard.getToolcard();

        System.out.println("arrivato nel controller");
        if(model.checkToolCard(idPlayer, idToolCard)){

            if (idToolCard == 1) {

                //boolean incremented_value = view.getIncrementedValue();

            } else if (idToolCard == 2) {
                //controllo ci siano ALMENO un dado su tutta la patterCard -> serve un metodo per contarli su patternCard
                ArrayList<Integer> positions = view.getPositionInPatternCard(playerMessageToolCard.getPlayer());
                System.out.println(positions);
                model.moveDieInsidePatternCard(idPlayer, positions.get(0), positions.get(1), positions.get(2), positions.get(3), false, true, 2);
            } else if (idToolCard == 3) {
                ArrayList<Integer> positions = view.getPositionInPatternCard(playerMessageToolCard.getPlayer());

                model.moveDieInsidePatternCard(idPlayer, positions.get(0), positions.get(1), positions.get(2), positions.get(3), true, false, 3);

            } else if (idToolCard == 4) {
                /* NON MI PIACE CHE INVOCHI DUE VOLTE LA VIEW
                ArrayList<Integer> startingPosition1 = view.getPositionInPatternCard(playerMessageToolCard.getPlayer());
                ArrayList<Integer> finalPosition1 = view.getPositionInPatternCard(playerMessageToolCard.getPlayer());

                model.moveDieInsidePatternCard(idPlayer, startingPosition1.get(0), startingPosition1.get(1), finalPosition1.get(0), finalPosition1.get(1), false, false, 4);

                ArrayList<Integer> startingPosition2 = view.getPositionInPatternCard(playerMessageToolCard.getPlayer());
                ArrayList<Integer> finalPosition2 = view.getPositionInPatternCard(playerMessageToolCard.getPlayer());

                model.moveDieInsidePatternCard(idPlayer, startingPosition2.get(0), startingPosition2.get(1), finalPosition2.get(0), finalPosition2.get(1), false, false, 4);
                */
            }
        }
    }
}
