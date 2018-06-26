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

                if(model.checkMovementPossibility(idPlayer)) {
                    ArrayList<Integer> positions = view.getPositionInPatternCard(idPlayer);
                    model.moveDieInsidePatternCard(idPlayer, positions, false, true, 2);
                }

            } else if (idToolCard == 3) {

                if(model.checkMovementPossibility(idPlayer)) {
                    ArrayList<Integer> positions = view.getPositionInPatternCard(idPlayer);
                    model.moveDieInsidePatternCard(idPlayer, positions, true, false, 3);
                }

            } else if (idToolCard == 4) {

                if(model.checkMovementPossibility(idPlayer)) {
                    ArrayList<Integer> positions1 = view.getPositionInPatternCard(idPlayer);
                    ArrayList<Integer> positions2 = view.getPositionInPatternCard(idPlayer);

                    model.moveTwoDiceInsidePatternCard(idPlayer, positions1, positions2, 4);
                }

            } else if (idToolCard == 5) {

                if(model.checkRoundIsPastSecond(idPlayer)) {

                    Integer idDieDiceArena = view.getDieFromDiceArena(idPlayer);
                    ArrayList<Integer> idDieRoundTrack = view.getDieFromRoundTrack(idPlayer);

                    model.swapDieAmongRoundTrackAndDiceArena(idPlayer, idDieRoundTrack.get(1), idDieRoundTrack.get(0), idDieDiceArena, idToolCard);
                }
            } else if(idToolCard == 6) {

                Integer idDie = view.getDieFromDiceArena(idPlayer);

                model.rollDieAgain(idPlayer, idDie, idToolCard);
                ArrayList<Integer> list = model.checkAvailablePositions(idPlayer, idDie);

                if(list.size() != 0) {
                    ArrayList<Integer> position = view.getSinglePositionInPatternCard(idPlayer, list);

                    model.setDieInPatternCardFromDiceArena(idPlayer, idDie, position.get(0), position.get(1), false, idToolCard);
                }
            } else if (idToolCard == 7) {

                model.rerollDiceArena(idPlayer, idToolCard);

            } else if (idToolCard == 8) {

                model.giveConsecutiveRoundsToPlayer(idPlayer, idToolCard);

            } else if(idToolCard == 9) {

                Integer idDie = view.getDieFromDiceArena(idPlayer);
                ArrayList<Integer> positions = view.getSinglePositionInPatternCard(idPlayer, new ArrayList<Integer>());

                model.setDieInPatternCardFromDiceArena(idPlayer, idDie, positions.get(0), positions.get(1), true, idToolCard);

            }else if(idToolCard == 10){

                int dieToTurn = view.getDieFromDiceArena(idPlayer);
                model.turnDieAround(idPlayer, dieToTurn ,idToolCard);
            } else if(idToolCard == 11){

                if(model.checkEnoughDiceInDiceBag(idPlayer)) {

                    int idDie = view.getDieFromDiceArena(idPlayer);
                    int newIdDie = model.swapDieWithDieFromDiceBag(idPlayer, idDie, idToolCard);

                    int value = view.getValueForDie(idPlayer);
                    model.giveValueToDie(idPlayer, idDie, newIdDie, value);
                    ArrayList<Integer> list = model.checkAvailablePositions(idPlayer, idDie);

                    if (list.size() != 0) {
                        ArrayList<Integer> position = view.getSinglePositionInPatternCard(idPlayer, list);
                        model.setDieInPatternCardFromDiceArena(idPlayer, idDie, position.get(0), position.get(1), false, idToolCard);
                    }
                }

            } else if(idToolCard == 12){

                //todo: gestire lo spostamento di un solo dado meglio -> creare nuova invocazione diversa per la view solo per questa toolCard

                if(model.checkMovementPossibility(idPlayer)) {

                    ArrayList<Integer> positions1 = view.getPositionInPatternCard(idPlayer);
                    ArrayList<Integer> positions2 = view.getPositionInPatternCard(idPlayer);

                    if (positions2.get(0) == -1) {

                        model.moveDieInsidePatternCard(idPlayer, positions1, false, false, idToolCard);

                    } else if (model.checkDiceColor(idPlayer, positions1, positions2)) {

                        model.moveTwoDiceInsidePatternCard(idPlayer, positions1, positions2, 4);

                    }
                }
            }

            view.free(idPlayer);
        }
    }
}
