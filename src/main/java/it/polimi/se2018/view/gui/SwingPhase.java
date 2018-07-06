package it.polimi.se2018.view.gui;

import it.polimi.se2018.model.events.*;
import it.polimi.se2018.network.ServerInterface;

import java.util.ArrayList;

public abstract class SwingPhase {

    protected ServerInterface serverInterface;

    protected ViewClientGUI viewClientGUI;

    public abstract void update(ModelChangedMessagePatternCard message);
    public abstract void update(ModelChangedMessagePrivateObjective message);
    public abstract void update(ModelChangedMessageDiceOnPatternCard message);
    public abstract void update(ModelChangedMessagePublicObjective message);
    public abstract void update(ModelChangedMessageDiceArena message);
    public abstract void update(ModelChangedMessageRound message);
    public abstract void update(ModelChangedMessageTokensLeft message);
    public abstract void update(ModelChangedMessageEndGame message);
    public abstract void update(ModelChangedMessageToolCard message);
    public abstract void update(ModelChangedMessageRefresh message);
    public abstract void update(ModelChangedMessageOnlyOnePlayerLeft message);

    /**
     * Method called when is received a ModelChangedMessageRefresh
     */
    public abstract void print();

    public void setServerInterface(ServerInterface serverInterface) {
        this.serverInterface = serverInterface;
    }

    public void setViewClientGUI(ViewClientGUI viewClientGUI) {
        this.viewClientGUI = viewClientGUI;
    }

    /**
     * Method called when the gamephase changes from SETUPPHASE to GAMEPHASE
     * @return
     */
    public abstract Integer askForPatternCard();

    /**
     * Checks if the move chose by the player is acceptable and generates either a playerMessageDie,
     * a playerMessageToolCard, player playerMessageEndTurn. If the move is not correct, it generates a
     * playerMessage with clientId as -1
     * @return playerMessage if the move was correct otherwise a playerMessage with clientId as -1
     */
    public abstract PlayerMessage getMainMove();

    /**
     * Method needed for pattern card n.2, n.3 and n.4 to get a starting and a final position to move a die
     * @return array list containing the starting and the final positions
     */
    public abstract ArrayList<Integer> getPositionInPatternCard();

    /**
     * Method needed for pattern card so that the player can select one of the dice from the dice arena
     * @return die chosen
     */
    public abstract Integer getDieFromDiceArena();

    /**
     * Method needed for tool card n.1
     * @return array list containing the die chosen and a value to see if he wants to increase or decrease its value
     */
    public abstract ArrayList<Integer> getIncrementedValue();

    /**
     * Get a single position (x and y) that needs to be contained in listOfAvailablePositions.
     * If list of available positions is empty, the player can insert any positions
     * @param listOfAvailablePosition positions available
     * @return array list containing the position chosen by the player
     */
    public abstract ArrayList<Integer> getSinglePositionInPatternCard(ArrayList<Integer> listOfAvailablePosition);

    /**
     * Method needed for tool card n.12 to move either one or two dice
     * @return array list containing the start and the final positions of the movement(s)
     */
    public abstract ArrayList<Integer> getDoublePositionInPatternCard();

    /**
     * Method needed for tool cards
     * @return array list containing the die chosen by the player and in which round it appears
     */
    public abstract ArrayList<Integer> getDieFromRoundTrack();

    /**
     * Method needed for pattern card so that the player can choose which value to give to the die
     * @return value chosen by the player
     */
    public abstract Integer getValueForDie();

    /**
     * Method to close the JFrames open
     */
    public abstract void close();
}