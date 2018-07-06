package it.polimi.se2018.model;

import it.polimi.se2018.model.player.Player;
import it.polimi.se2018.model.player.PlayerHasAlreadySetDieThisTurnException;
import it.polimi.se2018.model.container.DiceContainer;
import it.polimi.se2018.model.container.Die;
import it.polimi.se2018.model.container.DieRolledValueOutOfBoundException;
import it.polimi.se2018.model.rounds.DieNotPresentException;
import it.polimi.se2018.model.rounds.RoundTrack;
import it.polimi.se2018.model.rounds.RoundTrackNotInSecondPartOfRoundException;

import java.util.ArrayList;
import java.util.Collections;


public class DiceArena {

    private ArrayList<Integer> arena = new ArrayList<Integer>();

    private DiceContainer diceContainer;

    private int numberOfDice;

    private String representation;

    public DiceArena(int numberOfDice , DiceContainer diceContainer) {
        this.diceContainer = diceContainer;
        this.numberOfDice = numberOfDice;
    }

    public ArrayList<Integer> getArena() {
        return arena;
    }

    /**
     * before sending the representation of the dice in the draft pool, it call the update representation method
     * @return the representation of the dice in the draft pool
     */
    public String getRepresentation() {
        this.updateRepresentation();
        return representation;
    }

    /**
     * This method is invoked every time a round starts. It rolls n dice into the arena where n is the number of players
     * in the game multiplied by 2 and added 1
     */
    public void rollDiceIntoArena()  {
        arena.clear();
        ArrayList<Integer> diceToRoll = diceContainer.getUnrolledDice();
        Collections.shuffle(diceToRoll);
        for(int i = 0; i < numberOfDice; i++){
            diceContainer.getDie(diceToRoll.get(i)).roll();
            arena.add(diceToRoll.get(i));
        }
    }

    /**
     * This method is needed for tool card n. 11, where you have to remove one single die from the draft pool
     * @param idDie dieId that needs to be removed from the draft pool
     */
    public void removeDieFromDiceArena(int idDie) {
        for(int i = 0; i < arena.size(); i++)
            if(arena.get(i) == idDie)
                arena.remove(i);
    }


    /**
     * This method is needed for tool card n.11 where you have to roll one single die in the draft pool after removing one.
     * The new die is set in the same position where the old one was.
     * @param positionInDiceArena position of the die removed at the beginning of tool card 11
     * @param actualIdDie dieId that is gonna be set in the draft pool
     * @param value value chosen by the player to give to the die
     */
    public void rollOneDieIntoDiceArena(int positionInDiceArena, int actualIdDie, int value){
        try {
            diceContainer.getDie(actualIdDie).setRolledValue(value);
        } catch (DieRolledValueOutOfBoundException e) {
            e.printStackTrace();
        }
        arena.add(positionInDiceArena, actualIdDie);
    }

    /**
     * This method is used in tool card n.7 and it re-rolls all the dice into the draft pool
     * @param roundTrack needed to check if it is past first round
     * @param player player id performing the move needed to check whether he placed a die already
     * @throws RoundTrackNotInSecondPartOfRoundException when the player tries to use tool card 7 during the first turn
     * @throws PlayerHasAlreadySetDieThisTurnException when the player tries to use tool card 7 after setting a die
     */
    public void rerollDiceArena(RoundTrack roundTrack, Player player) throws RoundTrackNotInSecondPartOfRoundException, PlayerHasAlreadySetDieThisTurnException {
        if(roundTrack.isCurrentRoundInSecondPhase()) {
            if(!player.hasSetDieThisTurn()) {
                for (int i = 0; i < arena.size(); i++) {
                        diceContainer.getDie(arena.get(i)).roll();
                }
            } else{
                throw new PlayerHasAlreadySetDieThisTurnException();
            }
        } else {
            throw new RoundTrackNotInSecondPartOfRoundException();
        }
    }

    /**
     * This method is used for tool card n. 5 where the player wants to swap a die among draft pool and round track
     * @param dieIdToRemove id die to remove from the draft pool
     * @param dieIdToAdd id die to add to the draft pool
     * @throws DieNotPresentException if dieIdToRemove is not present in the draft pool
     */
    public void swapDie(int dieIdToRemove, int dieIdToAdd) throws DieNotPresentException {
        if(!(arena.contains(dieIdToRemove))) throw new DieNotPresentException();
        arena.set(arena.indexOf(dieIdToRemove), dieIdToAdd);
    }

    /**
     * Creates a single string representation of the draft pool that is gonna get sent to the player. For each die, it prints
     * the id, followed by the color, followed by the rolled value of the die
     */
    private void updateRepresentation(){
        representation = "";
        for(int i = 0; i < arena.size(); i++){
            Die d = null;
            d = diceContainer.getDie(arena.get(i));
            if(arena.get(i) < 10)
                representation = representation + "0";
            representation = representation + arena.get(i).toString() + d.getColorChar() + d.getRolledValue();
        }

    }

}