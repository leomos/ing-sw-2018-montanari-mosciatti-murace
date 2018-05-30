package it.polimi.se2018.view;

import it.polimi.se2018.model.GamePhase;
import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.PatternCard;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.container.DiceContainer;
import it.polimi.se2018.model.container.DiceContainerUnsupportedIdException;
import it.polimi.se2018.model.container.Die;
import it.polimi.se2018.model.container.DieColor;
import it.polimi.se2018.model.events.*;
import it.polimi.se2018.model.objectives.PublicObjective;
import it.polimi.se2018.model.toolcards.ToolCard;
import it.polimi.se2018.model.toolcards.ToolCardNotInPlayException;

import java.util.ArrayList;
import java.util.Scanner;

import static it.polimi.se2018.model.container.DieColor.*;

public class ViewClientConsole extends ViewClient {

    private int id;

    public String printColor(char dieColor) {
        String s = "";
        String ANSI_RED = "\u001B[101m";
        String ANSI_GREEN = "\u001B[102m";
        String ANSI_YELLOW = "\u001B[103m";
        String ANSI_BLUE = "\u001B[104m";
        String ANSI_PURPLE = "\u001B[105m";
        String ANSI_RESET = "\u001b[0m";

        if (dieColor=='r')
            s = s + ANSI_RED + "   " + ANSI_RESET;
        if (dieColor=='g')
            s = s + ANSI_GREEN + "   " + ANSI_RESET;
        if (dieColor=='y')
            s = s + ANSI_YELLOW + "   " + ANSI_RESET;
        if (dieColor=='b')
            s = s + ANSI_BLUE + "   " + ANSI_RESET;
        if (dieColor=='p')
            s = s + ANSI_PURPLE + "   " + ANSI_RESET;
        return s;
    }

    public String printDie(char dieColor, char value) {
        String s = "";
        String ANSI_RED = "\u001B[101m";
        String ANSI_GREEN = "\u001B[102m";
        String ANSI_YELLOW = "\u001B[103m";
        String ANSI_BLUE = "\u001B[104m";
        String ANSI_PURPLE = "\u001B[105m";
        String ANSI_RESET = "\u001b[0m";
        String ANSI_WHITE = "\u001B[107m";

        if (dieColor=='r')
            s = s + ANSI_RED + " " + value + " " + ANSI_RESET;
        if (dieColor=='g')
            s = s + ANSI_GREEN + " " + value + " " + ANSI_RESET;
        if (dieColor=='y')
            s = s + ANSI_YELLOW + " " + value + " " + ANSI_RESET;
        if (dieColor=='b')
            s = s + ANSI_BLUE + " " + value + " " + ANSI_RESET;
        if (dieColor=='p')
            s = s + ANSI_PURPLE + " " + value + " " + ANSI_RESET;
        if (dieColor=='w')
            s = s + ANSI_WHITE + " " + value + " " + ANSI_RESET;
        return s;
    }

    public boolean isColor (char c) {
        return (c=='r') || (c=='y') || (c=='g') || (c=='b') || (c=='p') || (c=='w');
    }

    public void printPatternCard(ModelChangedMessagePatternCard messagePatternCard, ModelChangedMessageDice messageDice) {
        String ANSI_WHITE = "\u001b[4m" + "\u001B[107m";
        String ANSI_RESET = "\u001b[0m";
        String ANSI_GREY = "\u001b[4m" + "\u001B[47m";

        int n = 0, m = 0;
        String s = "";

        System.out.print("PLAYER " + messagePatternCard.getIdPlayer() + ": PATTERNCARD\n");
        System.out.print(ANSI_GREY + "   |" + ANSI_RESET);
        for (int i=0; i<5; i++) {
            System.out.print(ANSI_GREY + " " + i + " |" + ANSI_RESET);
        }
        System.out.print("\n");

        for (int i=0; i<4; i++) {
            s = s + ANSI_GREY + " " + i + " |" + ANSI_RESET;
            for (int j=0; j<5; j++) {
                if (messageDice.getRepresentation().charAt(n)=='*') {
                    char c = messagePatternCard.getRepresentation().charAt(m+j);

                    if (isColor(c))
                        s = s + "\u001b[4m" + printColor(c);
                    else if (c == '0')
                        s = s + ANSI_WHITE + "   " + ANSI_RESET;
                    else
                        s = s + "\u001b[4m" + printDie('w', c);
                    s = s + "|";
                } else {
                    char color = messageDice.getRepresentation().charAt(n);
                    char value = messageDice.getRepresentation().charAt(n+1);

                    s = s + printDie(color, value) + "|";
                }
                n += 2;
            }
            m = m + 5;
            s = s + "\n";
        }
        System.out.println(s);
    }

    public void printRoundTrack(ModelChangedMessageRound message) {

        System.out.print("ROUND " + message.getIdRound() + ":\t");
        for (int j=0; j<message.getRepresentation().length(); j+=4) {
            char color = message.getRepresentation().charAt(j+2);
            char value = message.getRepresentation().charAt(j+3);

            System.out.print("\t " + message.getRepresentation().charAt(j) + message.getRepresentation().charAt(j+1) + " ");
            System.out.print(printDie(color, value));
        }
    }

    public void printDiceArena(ModelChangedMessageDiceArena message) {
        System.out.print("\n\nDICEARENA:\t");
        for (int j=0; j<message.getRepresentation().length(); j+=4) {
            char color = message.getRepresentation().charAt(j+2);
            char value = message.getRepresentation().charAt(j+3);

            System.out.print("\t " + message.getRepresentation().charAt(j) + message.getRepresentation().charAt(j+1) + " ");
            System.out.print(printDie(color, value));
        }
        System.out.print("\n");
    }

    public void printPrivateObjective(ModelChangedMessagePrivateObjective message) {
        System.out.print("\nPRIVATE OBJECTIVES: "
                + "\tNAME: " + message.getName()
                + "\tDESCRIPTION: " + message.getDescription() + "\n");
    }

    public void printPublicObjective(ModelChangedMessagePublicObjective message) {
        System.out.print("\nPUBLIC OBJECTIVE: ");

        System.out.print("\tNAME: " + message.getName()
                + "\tDESCRIPTION: " + message.getDescription() + "\n");
    }

    public void printToolCards(ModelChangedMessageToolCard message) {
        System.out.print("\nTOOLCARDS:");

        System.out.print("\tID: " + message.getIdToolCard()
                + "\tNAME: " + message.getName()
                + "\tDESCRIPTION: " + message.getDescription()
                + "\tCOST: " + message.getCost());
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

    public int getDieFromPatternCard(){
        int dieId = -1;

        System.out.println("Insert ID die");
        Scanner input = new Scanner(System.in);
        String s = input.nextLine();
        dieId = Integer.parseInt(s);
        input.close();

        return dieId;
    }

    public int getDieFromRoundTrack(){
        int dieId = -1;

        System.out.println("Insert ID die");
        Scanner input = new Scanner(System.in);
        String s = input.nextLine();
        dieId = Integer.parseInt(s);
        input.close();

        return dieId;
    }

    public boolean getIncrementedValue(){
        boolean i = false;

        System.out.println("Insert 1 to increment value, 0 to decrement");
        Scanner input = new Scanner(System.in);
        String s = input.nextLine();
        int k = Integer.parseInt(s);
        if(k == 1)
            i = true;
        else
            i = false;
        input.close();

        return i;
    }
}