package it.polimi.se2018.view.console;

import it.polimi.se2018.model.events.*;

import java.util.ArrayList;

public abstract class ViewClientConsolePrint {

    protected String printColor(char dieColor) {
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

    protected String printDie(char dieColor, char value) {
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

    protected boolean isColor (char c) {
        return (c=='r') || (c=='y') || (c=='g') || (c=='b') || (c=='p') || (c=='w');
    }

    protected void printPatternCard(String idPlayer, ModelChangedMessagePatternCard messagePatternCard, ModelChangedMessageDiceOnPatternCard messageDice) {
        String ANSI_WHITE = "\u001b[4m" + "\u001B[107m";
        String ANSI_RESET = "\u001b[0m";
        String ANSI_GREY = "\u001b[4m" + "\u001B[47m";

        int n = 0, m = 0;
        String s = "";

        System.out.print("ID player " + idPlayer + "\n");
        System.out.println("Name\t" + messagePatternCard.getName() + "\tDifficulty\t" + messagePatternCard.getDifficulty());
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
                    char color = messageDice.getRepresentation().charAt(n+2);
                    char value = messageDice.getRepresentation().charAt(n+3);

                    s = s + printDie(color, value) + "|";
                }
                n += 4;
            }
            m = m + 5;
            s = s + "\n";
        }
        System.out.println(s);
    }

    protected void printRoundTrack(ModelChangedMessageRound message) {

        int app = Integer.parseInt(message.getIdRound()) + 1;
        System.out.print("\nROUND " + app  + ":\t");
        if(message.getRepresentation().length() == 0)
            System.out.println("\tCURRENT ROUND\t");
        for (int j=0; j<message.getRepresentation().length(); j+=4) {
            char color = message.getRepresentation().charAt(j+2);
            char value = message.getRepresentation().charAt(j+3);

            System.out.print("\t " + j/4 + " ");
            System.out.print(printDie(color, value));
        }
    }

    protected void printDiceArena(ModelChangedMessageDiceArena message) {
        System.out.print("\n\nDICEARENA:\t");
        for (int j=0; j<message.getRepresentation().length(); j+=4) {
            char color = message.getRepresentation().charAt(j+2);
            char value = message.getRepresentation().charAt(j+3);

            System.out.print("\t " + j/4 + " ");
            System.out.print(printDie(color, value));
        }
        System.out.print("\n");
    }

    protected void printPrivateObjective(ModelChangedMessagePrivateObjective message) {
        System.out.print("PRIVATE OBJECTIVES: "
                + "\tNAME: " + message.getName()
                + "\tDESCRIPTION: " + message.getDescription() + "\n");
    }

    protected void printPublicObjective(ModelChangedMessagePublicObjective message) {
        System.out.print("\nPUBLIC OBJECTIVE: ");

        System.out.print("\tNAME: " + message.getName()
                + "\tDESCRIPTION: " + message.getDescription());
    }

    protected void printToolCards(ModelChangedMessageToolCard message) {
        System.out.print("\nTOOLCARDS:");

        System.out.print("\tID: " + message.getIdToolCard()
                + "\tNAME: " + message.getName()
                + "\tDESCRIPTION: " + message.getDescription()
                + "\tCOST: " + message.getCost());
    }

    protected void printPatternCard(ModelChangedMessagePatternCard messagePatternCard) {
        String ANSI_WHITE = "\u001b[4m" + "\u001B[107m";
        String ANSI_RESET = "\u001b[0m";
        String ANSI_GREY = "\u001b[4m" + "\u001B[47m";

        int m = 0;
        String s = "";

        System.out.print("ID PATTERNCARD: " + messagePatternCard.getIdPatternCard() + "\n");
        System.out.println("Name\t" + messagePatternCard.getName() + "\tDifficulty\t" + messagePatternCard.getDifficulty());
        System.out.print(ANSI_GREY + "   |" + ANSI_RESET);
        for (int i=0; i<5; i++) {
            System.out.print(ANSI_GREY + " " + i + " |" + ANSI_RESET);
        }
        System.out.print("\n");

        for (int i=0; i<4; i++) {
            s = s + ANSI_GREY + " " + i + " |" + ANSI_RESET;
            for (int j=0; j<5; j++) {
                char c = messagePatternCard.getRepresentation().charAt(j+m);

                if (isColor(c))
                    s = s + "\u001b[4m" + printColor(c);
                else if (c == '0')
                    s = s + ANSI_WHITE + "   " + ANSI_RESET;
                else
                    s = s + "\u001b[4m" + printDie('w', c);
                s = s + "|";
            }
            s = s + "\n";
            m += 5;
        }
        System.out.println(s);
    }

    public abstract void update(ModelChangedMessage message);

    public abstract void print();

    public abstract Integer askForPatternCard(String s);

    public abstract ArrayList<Integer> getPositionInPatternCard();

    public abstract ArrayList<Integer> getSinglePositionInPatternCard(ArrayList<Integer> listOfAvailablePositions);

    public abstract ArrayList<Integer> getIncrementedValue();

    public abstract Integer getDieFromDiceArena();

    public abstract ArrayList<Integer> getDieFromRoundTrack();

    public abstract Integer getValueForDie();

    public abstract ArrayList<Integer> getDoublePositionInPatternCard();

    public abstract PlayerMessage getMainMove(String s);

    public abstract void setSuspended(boolean suspended);

}
