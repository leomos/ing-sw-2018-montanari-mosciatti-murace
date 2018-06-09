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

        if(model.checkToolCard(idPlayer, idToolCard)){

            if (idToolCard == 1) {

                //boolean incremented_value = view.getIncrementedValue();

            } else if (idToolCard == 2) {
                //controllo ci siano ALMENO due dadi su tutta la patterCard -> serve un metodo per contarli su patternCard
                //problema se muovi il primo dado
                //problema se muovi in una posizione che diventa isolata SOLO dopo lo spostamento
                ArrayList<Integer> startingPosition = view.getPositionInPatternCard(playerMessageToolCard.getPlayer());
                ArrayList<Integer> finalPosition = view.getPositionInPatternCard(playerMessageToolCard.getPlayer());

                model.moveDieInsidePatternCard(idPlayer, startingPosition.get(0), startingPosition.get(1), finalPosition.get(0), finalPosition.get(1), false, true, 2);
            } else if (idToolCard == 3) {
                ArrayList<Integer> startingPosition = view.getPositionInPatternCard(playerMessageToolCard.getPlayer());
                ArrayList<Integer> finalPosition = view.getPositionInPatternCard(playerMessageToolCard.getPlayer());

                model.moveDieInsidePatternCard(idPlayer, startingPosition.get(0), startingPosition.get(1), finalPosition.get(0), finalPosition.get(1), true, false, 3);
            } else if (idToolCard == 4) {
                ArrayList<Integer> startingPosition1 = view.getPositionInPatternCard(playerMessageToolCard.getPlayer());
                ArrayList<Integer> finalPosition1 = view.getPositionInPatternCard(playerMessageToolCard.getPlayer());

                model.moveDieInsidePatternCard(idPlayer, startingPosition1.get(0), startingPosition1.get(1), finalPosition1.get(0), finalPosition1.get(1), false, false, 4);

                ArrayList<Integer> startingPosition2 = view.getPositionInPatternCard(playerMessageToolCard.getPlayer());
                ArrayList<Integer> finalPosition2 = view.getPositionInPatternCard(playerMessageToolCard.getPlayer());

                model.moveDieInsidePatternCard(idPlayer, startingPosition2.get(0), startingPosition2.get(1), finalPosition2.get(0), finalPosition2.get(1), false, false, 4);
            }
        }
    }
}
