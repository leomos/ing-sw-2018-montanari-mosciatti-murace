package it.polimi.se2018.view;

import it.polimi.se2018.model.GamePhase;
import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.PatternCard;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.container.DiceContainer;
import it.polimi.se2018.model.container.DiceContainerUnsupportedIdException;
import it.polimi.se2018.model.container.Die;
import it.polimi.se2018.model.container.DieColor;
import it.polimi.se2018.model.objectives.PublicObjective;
import it.polimi.se2018.model.rounds.Round;
import it.polimi.se2018.model.toolcards.ToolCard;
import it.polimi.se2018.model.toolcards.ToolCardNotInPlayException;

import static it.polimi.se2018.model.container.DieColor.*;

public class ViewClientConsole extends ViewClient {
    private int id;

    public String printColor(DieColor dieColor) {
        String s = "";
        String ANSI_RED = "\u001B[41m";
        String ANSI_GREEN = "\u001B[42m";
        String ANSI_YELLOW = "\u001B[103m";
        String ANSI_BLUE = "\u001B[44m";
        String ANSI_PURPLE = "\u001B[105m";
        String ANSI_RESET = "\u001b[4m" + "\u001b[0m";

        if (dieColor==RED)
            s = s + ANSI_RED + "   " + ANSI_RESET;
        if (dieColor==GREEN)
            s = s + ANSI_GREEN + "   " + ANSI_RESET;
        if (dieColor==YELLOW)
            s = s + ANSI_YELLOW + "   " + ANSI_RESET;
        if (dieColor==BLUE)
            s = s + ANSI_BLUE + "   " + ANSI_RESET;
        if (dieColor==PURPLE)
            s = s + ANSI_PURPLE + "   " + ANSI_RESET;
        return s;
    }

    public String printColor(DieColor dieColor, int value) {
        String s = "";
        String ANSI_RED = "\u001B[41m";
        String ANSI_GREEN = "\u001B[42m";
        String ANSI_YELLOW = "\u001B[103m";
        String ANSI_BLUE = "\u001B[44m";
        String ANSI_PURPLE = "\u001B[105m";
        String ANSI_RESET = "\u001b[4m" + "\u001b[0m";

        if (dieColor==RED)
            s = s + ANSI_RED + " " + value + " " + ANSI_RESET;
        if (dieColor==GREEN)
            s = s + ANSI_GREEN + " " + value + " " + ANSI_RESET;
        if (dieColor==YELLOW)
            s = s + ANSI_YELLOW + " " + value + " " + ANSI_RESET;
        if (dieColor==BLUE)
            s = s + ANSI_BLUE + " " + value + " " + ANSI_RESET;
        if (dieColor==PURPLE)
            s = s + ANSI_PURPLE + " " + value + " " + ANSI_RESET;
        return s;
    }

    /* TODO: METTERE I NUMERI DI RIGA E COLONNA SUL BORDO DELLA PATTERNCARD*/
    public String printPatternCard(PatternCard patternCard) {
        String ANSI_WHITE = "\u001b[4m" + "\u001B[107m";
        String ANSI_RED = "\u001b[4m" + "\u001B[41m";
        String ANSI_GREEN = "\u001b[4m" + "\u001B[42m";
        String ANSI_YELLOW = "\u001b[4m" + "\u001B[103m";
        String ANSI_BLUE = "\u001b[4m" + "\u001B[44m";
        String ANSI_PURPLE = "\u001b[4m" + "\u001B[105m";
        String ANSI_RESET = "\u001b[4m" + "\u001b[0m";

        DiceContainer diceContainer = new DiceContainer();
        DieColor color;
        int n;
        String s = "";

        for (int i=0; i<4; i++) {
            for (int j=0; j<5; j++) {
                if (patternCard.getPatternCardCell(j, i).isEmpty()) {
                    color = patternCard.getPatternCardCell(j, i).getColorConstraint();
                    n = patternCard.getPatternCardCell(j, i).getValueConstraint();

                    if (color != null && n == 0)
                        s = s + printColor(color);
                    else {
                        if (n == 1)
                            s = s + ANSI_WHITE + " 1 " + ANSI_RESET;
                        if (n == 2)
                            s = s + ANSI_WHITE + " 2 " + ANSI_RESET;
                        if (n == 3)
                            s = s + ANSI_WHITE + " 3 " + ANSI_RESET;
                        if (n == 4)
                            s = s + ANSI_WHITE + " 4 " + ANSI_RESET;
                        if (n == 5)
                            s = s + ANSI_WHITE + " 5 " + ANSI_RESET;
                        if (n == 6)
                            s = s + ANSI_WHITE + " 6 " + ANSI_RESET;
                    }
                    if (n == 0 && color == null)
                        s = s + ANSI_WHITE + "   " + ANSI_RESET;
                    s = s + "|";

                } else {
                    Die die = null;
                    try {
                        die = diceContainer.getDie(patternCard.getPatternCardCell(j, i).getRolledDieId());
                    } catch (DiceContainerUnsupportedIdException e) {
                        e.printStackTrace();
                    }
                    color = die.getColor();
                    n = die.getRolledValue();

                    if (color == YELLOW)
                        s = s + ANSI_YELLOW + " " + n + " " + ANSI_RESET;
                    if (color == RED)
                        s = s + ANSI_RED + " " + n + " " + ANSI_RESET;
                    if (color == BLUE)
                        s = s + ANSI_BLUE + " " + n + " " + ANSI_RESET;
                    if (color == PURPLE)
                        s = s + ANSI_PURPLE + " " + n + " " + ANSI_RESET;
                    if (color == GREEN)
                        s = s + ANSI_GREEN + " " + n + " " + ANSI_RESET;
                    s = s + "|";
                }
            }
            s = s + "\n";
        }
        return s;
    }

    public void print(Model model) throws ToolCardNotInPlayException, DiceContainerUnsupportedIdException {
        if (model.getGamePhase() == GamePhase.SETUPPHASE) {
        } else if (model.getGamePhase() == GamePhase.GAMEPHASE) {
            System.out.println("\nROUNDTRACK\n");
            DiceContainer diceContainer = model.getTable().getDiceContainer();

            for (int i=0; i<10; i++) {
                System.out.println("ROUND " + i++);
                int[] array = model.getTable().getRoundTrack().getRound(i).getRolledDiceLeft();
                for (int j=0; j<array.length; j++) {
                    Die die = diceContainer.getDie(array[j]);
                    System.out.println(printColor(die.getColor(), die.getRolledValue()));
                }
            }

            Player myPlayer = new Player(0, null);
            for (int i = 0; i < model.getTable().getNumberOfPlayers(); i++) {
                Player player = model.getTable().getPlayers(i);

                if (this.id != player.getId()) {
                    System.out.println("\nPLAYER: " + player.getName() + "\n");
                    System.out.println(printPatternCard(player.getChosenPatternCard()));
                }
                else
                    myPlayer = player;
            }
            System.out.println("\nPLAYER: " + myPlayer.getName() + "\n");
            System.out.println(myPlayer.getChosenPatternCard());

            System.out.println("\nPRIVATE OBJECTIVES: " + "\t" + printColor(myPlayer.getPrivateObjective().getColor())
                    + "\tNAME: " + myPlayer.getPrivateObjective().getName()
                    + "\tDESCRIPTION: \t" + myPlayer.getPrivateObjective().getDescription());

            /*TODO: STAMPARE DICEARENA*/

            System.out.println("\nPUBLIC OBJECTIVE: \n");
            for (int i=0; i<3; i++) {
                PublicObjective publicObjective = model.getTable().getPublicObjective(i);
                System.out.println("NAME: " + publicObjective.getName()
                        + "\tDESCRIPTION: " + publicObjective.getDescription());
            }

            System.out.println("\nTOOLCARDS: \n");
            for (int i=0; i<3; i++) {
                ToolCard toolCard = model.getTable().getToolCardContainer().getToolCardInPlay().get(i);
                System.out.println("ID: " + toolCard.getToolCardId()
                        + "\tNAME: " + toolCard.getName()
                        + "\tDESCRIPTION: " + toolCard.getDescription());
            }
        }
    }
}