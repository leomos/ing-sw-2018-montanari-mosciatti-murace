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

        if(model.checkToolCard(idPlayer, idToolCard)) {

            view.block(idPlayer);

            if (idToolCard == 1) {

                ArrayList<Integer> dieToIncrement = view.getIncrementedValue(idPlayer);
                model.incrementOrDecrementDieValue(idPlayer, dieToIncrement.get(0), dieToIncrement.get(1), idToolCard);

            } else if (idToolCard == 2) {

                ArrayList<Integer> positions = view.getPositionInPatternCard(idPlayer);
                model.moveDieInsidePatternCard(idPlayer, positions.get(0), positions.get(1), positions.get(2), positions.get(3), false, true, 2);

            } else if (idToolCard == 3) {

                ArrayList<Integer> positions = view.getPositionInPatternCard(idPlayer);
                model.moveDieInsidePatternCard(idPlayer, positions.get(0), positions.get(1), positions.get(2), positions.get(3), true, false, 3);

            } else if (idToolCard == 4) {

                /*
                ArrayList<Integer> startingPosition1 = view.getPositionInPatternCard(playerMessageToolCard.getPlayer());
                ArrayList<Integer> finalPosition1 = view.getPositionInPatternCard(playerMessageToolCard.getPlayer());
        
                model.moveDieInsidePatternCard(idPlayer, startingPosition1.get(0), startingPosition1.get(1), finalPosition1.get(0), finalPosition1.get(1), false, false, 4);

                ArrayList<Integer> startingPosition2 = view.getPositionInPatternCard(playerMessageToolCard.getPlayer());
                ArrayList<Integer> finalPosition2 = view.getPositionInPatternCard(playerMessageToolCard.getPlayer());

                model.moveDieInsidePatternCard(idPlayer, startingPosition2.get(0), startingPosition2.get(1), finalPosition2.get(0), finalPosition2.get(1), false, false, 4);
                */
            } else if (idToolCard == 5) {

                Integer idDieDiceArena = view.getDieFromDiceArena(idPlayer);
                Integer idDieRoundTrack = view.getDieFromRoundTrack(idPlayer);

                //todo: non salva bene i round
                model.swapDieAmongRoundTrackAndDiceArena(idPlayer, idDieRoundTrack, idDieDiceArena, idToolCard);
            } else if(idToolCard == 6) {

                //todo gestire errore nella prima interazione in modo che non invochi la seconda istruzione
                Integer idDie = view.getDieFromDiceArena(idPlayer);

                model.rollDieAgain(idPlayer, idDie, idToolCard);

                //todo: check che possa essere piazzato e solo in caso fai queste due istruzioni
                ArrayList<Integer> position = view.getSinglePositionInPatternCard(idPlayer);

                model.setDieInPatternCardFromDiceArena(idPlayer, idDie, position.get(0), position.get(1), false, idToolCard);

            } else if (idToolCard == 7) {

                model.rerollDiceArena(idPlayer, idToolCard);

            } else if (idToolCard == 8) {

                model.giveConsecutiveRoundsToPlayer(idPlayer, idToolCard);

            } else if(idToolCard == 9) {

                Integer idDie = view.getDieFromDiceArena(idPlayer);
                ArrayList<Integer> positions = view.getSinglePositionInPatternCard(idPlayer);

                model.setDieInPatternCardFromDiceArena(idPlayer, idDie, positions.get(0), positions.get(1), true, idToolCard);

            }else if(idToolCard == 10){

                int dieToTurn = view.getDieFromDiceArena(idPlayer);
                model.turnDieAround(idPlayer, dieToTurn ,idToolCard);
            } else if(idToolCard == 11){

                //todo dice bag non vuota

                int idDie = view.getDieFromDiceArena(idPlayer);
                int newIdDie = model.swapDieWithDieFromDiceBag(idPlayer, idDie, idToolCard);

                int value = view.getValueForDie(idPlayer);
                //MANDA ARRAY DI POSIZIONI
                ArrayList<Integer> position = view.getSinglePositionInPatternCard(idPlayer);
                model.giveValueToDie(idPlayer, newIdDie, value);

                model.setDieInPatternCardFromDiceArena(idPlayer, newIdDie, position.get(0), position.get(1), false, idToolCard);
            }

            view.free(idPlayer);
        }
    }
}
