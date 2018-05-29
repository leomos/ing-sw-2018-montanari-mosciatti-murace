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
import it.polimi.se2018.model.toolcards.ToolCard;
import it.polimi.se2018.model.toolcards.ToolCardNotInPlayException;

import java.util.ArrayList;
import java.util.Scanner;

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

    public String printDie(DieColor dieColor, int value) {
        String s = "";
        String ANSI_RED = "\u001B[41m";
        String ANSI_GREEN = "\u001B[42m";
        String ANSI_YELLOW = "\u001B[103m";
        String ANSI_BLUE = "\u001B[44m";
        String ANSI_PURPLE = "\u001B[105m";
        String ANSI_RESET = "\u001b[4m" + "\u001b[0m";
        String ANSI_WHITE = "\u001B[107m";

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
        if (dieColor==null)
            s = s + ANSI_WHITE + " " + value + " " + ANSI_RESET;
        return s;
    }

    public String printPatternCard(PatternCard patternCard) {
        String ANSI_WHITE = "\u001b[4m" + "\u001B[107m";
        String ANSI_RESET = "\u001b[4m" + "\u001b[0m";
        String ANSI_GREY = "\u001b[4m" + "\u001B[47m";

        DiceContainer diceContainer = new DiceContainer();
        DieColor color;
        int n;
        String s = "";

        System.out.print(ANSI_GREY + "   |" + ANSI_RESET);
        for (int i=0; i<5; i++) {
            System.out.print(ANSI_GREY + " " + i + " |" + ANSI_RESET);
        }
        System.out.print("\n");

        for (int i=0; i<4; i++) {
            s = s + ANSI_GREY + " " + i + " |" + ANSI_RESET;
            for (int j=0; j<5; j++) {
                if (patternCard.getPatternCardCell(j, i).isEmpty()) {
                    color = patternCard.getPatternCardCell(j, i).getColorConstraint();
                    n = patternCard.getPatternCardCell(j, i).getValueConstraint();

                    if (color != null && n == 0)
                        s = s + "\u001b[4m" + printColor(color);
                    if (color == null && n != 0)
                        s = s + "\u001b[4m" + printDie(null, n);
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

                    s = s + printDie(color, n) + "|";
                }
            }
            s = s + "\n";
        }
        return s;
    }

    public void printRoundTrack(Model model) throws DiceContainerUnsupportedIdException {
        System.out.println("\nROUNDTRACK\n");
        DiceContainer diceContainer = model.getTable().getDiceContainer();
        for (int i=0; i<10; i++) {
            System.out.println("ROUND " + i++);
            int[] array = model.getTable().getRoundTrack().getRound(i).getRolledDiceLeft();
            for (int j=0; j<array.length; j++) {
                Die die = diceContainer.getDie(array[j]);
                System.out.println(printDie(die.getColor(), die.getRolledValue()));
            }
        }
    }

    public void printDiceArena(Model model) throws DiceContainerUnsupportedIdException {
        System.out.println("\nDICEARENA\n");
        ArrayList<Integer> dice = model.getTable().getDiceArena().getArena();
        for (int i=0; i<dice.size(); i++) {
            Die die = model.getTable().getDiceContainer().getDie(dice.get(i));
            System.out.println(printDie(die.getColor(), die.getRolledValue()));
        }
    }

    public void printPlayer(Model model) {
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
    }

    public void printPublicObjective(Model model) {
        System.out.println("\nPUBLIC OBJECTIVE: \n");
        for (int i=0; i<3; i++) {
            PublicObjective publicObjective = model.getTable().getPublicObjective(i);
            System.out.println("NAME: " + publicObjective.getName()
                    + "\tDESCRIPTION: " + publicObjective.getDescription());
        }
    }

    public void printToolCards(Model model) throws ToolCardNotInPlayException {
        System.out.println("\nTOOLCARDS: \n");
        for (int i=0; i<3; i++) {
            ToolCard toolCard = model.getTable().getToolCardContainer().getToolCardInPlay().get(i);
            System.out.println("ID: " + toolCard.getToolCardId()
                    + "\tNAME: " + toolCard.getName()
                    + "\tDESCRIPTION: " + toolCard.getDescription());
        }
    }

    public void print(Model model) throws ToolCardNotInPlayException, DiceContainerUnsupportedIdException {
        if (model.getGamePhase() == GamePhase.SETUPPHASE) {


        } else if (model.getGamePhase() == GamePhase.GAMEPHASE) {
            printRoundTrack(model);

            printPlayer(model);

            printDiceArena(model);

            printPublicObjective(model);

            printToolCards(model);
        }
    }

    public int[] getDiePosition(){
        int position[] = null;

        System.out.println("Insert position on PatternCard separated by a space");
        Scanner input = new Scanner(System.in);
        String s = input.nextLine();
        position[0] = Integer.parseInt(s.split(" ")[0]);
        position[1] = Integer.parseInt(s.split(" ")[1]);
        input.close();

        return position;
    }

    public int getDie(){
        int dieId = 0;

        System.out.println("Insert ID die");
        Scanner input = new Scanner(System.in);
        String s = input.nextLine();
        dieId = Integer.parseInt(s);
        input.close();

        return dieId;
    }

    public int getIncrementedValue(){
        int i;

        System.out.println("Insert 1 to increment value, -1 to decrement");
        Scanner input = new Scanner(System.in);
        String s = input.nextLine();
        i = Integer.parseInt(s);
        input.close();

        return i;
    }
}