package it.polimi.se2018.controller;

import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.events.PlayerMessageToolCard;
import it.polimi.se2018.view.VirtualView;

import java.util.ArrayList;

public class ToolCardController implements Runnable{

    private Model model;

    private int idPlayer;

    private int idToolCard;

    private VirtualView view;

    public ToolCardController(Model model){
        this.model = model;
    }

    public void execute(PlayerMessageToolCard playerMessageToolCard, VirtualView view){
        this.idPlayer = playerMessageToolCard.getPlayer();
        this.idToolCard = playerMessageToolCard.getToolCard();
        this.view = view;

        if(model.checkToolCard(idPlayer, idToolCard)) {

            view.block(idPlayer);

            Thread t = new Thread(this);
            t.start();


        }
    }

    @Override
    public void run() {
        switch (idToolCard){

            case 1:
                ArrayList<Integer> dieToIncrement = view.getIncrementedValue(idPlayer);
                if(dieToIncrement.size() != 0)
                    model.incrementOrDecrementDieValue(idPlayer, dieToIncrement.get(0), dieToIncrement.get(1), idToolCard);
                break;
            case 2:
                if(model.checkMovementPossibility(idPlayer)) {
                    ArrayList<Integer> positions = view.getPositionInPatternCard(idPlayer);
                    if(positions.size() != 0)
                        model.moveDieInsidePatternCard(idPlayer, positions, false, true, 2);
                }
                break;
            case 3:
                if(model.checkMovementPossibility(idPlayer)) {
                    ArrayList<Integer> positions = view.getPositionInPatternCard(idPlayer);
                    System.out.println(positions);
                    if(positions.size() != 0)
                        model.moveDieInsidePatternCard(idPlayer, positions, true, false, 3);
                }
                break;
            case 4:
                if(model.checkMovementPossibility(idPlayer)) {
                    ArrayList<Integer> positions1 = view.getPositionInPatternCard(idPlayer);

                    if(positions1.size() != 0) {
                        ArrayList<Integer> positions2 = view.getPositionInPatternCard(idPlayer);

                        if (positions2.size() != 0)
                            model.moveTwoDiceInsidePatternCard(idPlayer, positions1, positions2, 4);
                    }
                }
                break;
            case 5:
                if(model.checkRoundIsPastSecond(idPlayer) && model.checkPlayerCanPlaceDie(idPlayer)) {

                    Integer idDieDiceArena = view.getDieFromDiceArena(idPlayer);

                    if(idDieDiceArena != -1) {

                        ArrayList<Integer> idDieRoundTrack = view.getDieFromRoundTrack(idPlayer);

                        if(idDieRoundTrack.size() != 0)
                            model.swapDieAmongRoundTrackAndDiceArena(idPlayer, idDieRoundTrack.get(1), idDieRoundTrack.get(0), idDieDiceArena, idToolCard);
                    }
                }
                break;
            case 6:
                if(model.checkPlayerCanPlaceDie(idPlayer)) {

                    Integer idDie = view.getDieFromDiceArena(idPlayer);

                    if(idDie != -1) {

                        model.rollDieAgain(idPlayer, idDie, idToolCard);
                        ArrayList<Integer> list = model.checkAvailablePositions(idPlayer, idDie);

                        if (list.size() != 0) {
                            ArrayList<Integer> position = view.getSinglePositionInPatternCard(idPlayer, list);

                            model.setDieInPatternCardFromDiceArena(idPlayer, idDie, position.get(0), position.get(1), false, idToolCard);
                        }
                    }
                }
                break;
            case 7:
                model.rerollDiceArena(idPlayer, idToolCard);
                break;
            case 8:
                model.giveConsecutiveRoundsToPlayer(idPlayer, idToolCard);
                break;
            case 9:
                if(model.checkPlayerCanPlaceDie(idPlayer)) {
                    Integer idDie = view.getDieFromDiceArena(idPlayer);
                    if(idDie != -1) {
                        ArrayList<Integer> positions = view.getSinglePositionInPatternCard(idPlayer, new ArrayList<Integer>());

                        if(positions.size() != 0)
                            model.setDieInPatternCardFromDiceArena(idPlayer, idDie, positions.get(0), positions.get(1), true, idToolCard);
                    }
                }
                break;
            case 10:
                int dieToTurn = view.getDieFromDiceArena(idPlayer);
                if(dieToTurn != -1)
                    model.turnDieAround(idPlayer, dieToTurn ,idToolCard);
                break;
            case 11:
                if(model.checkEnoughDiceInDiceBag(idPlayer) && model.checkPlayerCanPlaceDie(idPlayer)) {

                    int idDie = view.getDieFromDiceArena(idPlayer);
                    if(idDie != -1) {
                        int newIdDie = model.swapDieWithDieFromDiceBag(idPlayer, idDie);
                        int value = view.getValueForDie(idPlayer);

                        if(value != -1) {
                            model.giveValueToDie(idDie, newIdDie, value);
                            ArrayList<Integer> list = model.checkAvailablePositions(idPlayer, idDie);

                            if (list.size() != 0) {
                                ArrayList<Integer> position = view.getSinglePositionInPatternCard(idPlayer, list);
                                model.setDieInPatternCardFromDiceArena(idPlayer, idDie, position.get(0), position.get(1), false, idToolCard);
                            }
                        }
                    }
                }
                break;

            case 12:

                if(model.checkMovementPossibility(idPlayer)) {
                    ArrayList<Integer> positions = view.getDoublePositionInPatternCard(idPlayer);
                    if(positions.size() != 0) {
                        ArrayList<Integer> positions1 = new ArrayList<Integer>();
                        ArrayList<Integer> positions2 = new ArrayList<Integer>();
                        for (int i = 0; i < 4; i++)
                            positions1.add(positions.get(i));
                        if(positions.size() != 4)
                           for (int i = 4; i < 8; i++)
                                positions2.add(positions.get(i));

                        if (model.checkDiceColor(idPlayer, positions1, positions2)){
                            if (positions.size() == 4)
                                model.moveDieInsidePatternCard(idPlayer, positions1, false, false, idToolCard);
                            else
                                model.moveTwoDiceInsidePatternCard(idPlayer, positions1, positions2, 4);

                        }
                    }
                }
                break;
        }

        view.free(idPlayer);
    }
}
