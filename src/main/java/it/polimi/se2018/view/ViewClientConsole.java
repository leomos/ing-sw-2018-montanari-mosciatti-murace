package it.polimi.se2018.view;

import it.polimi.se2018.model.GamePhase;
import it.polimi.se2018.model.events.ModelChangedMessage;
import it.polimi.se2018.model.events.ModelChangedMessageConnected;
import it.polimi.se2018.model.events.ModelChangedMessageRefresh;

import java.util.Scanner;

import static it.polimi.se2018.model.GamePhase.GAMEPHASE;

public class ViewClientConsole extends ViewClient {

    private int idClient;

    private GamePhase gamePhase = GamePhase.SETUPPHASE;

    private ViewClientConsolePrint viewClientConsolePrint = new ViewClientConsoleSetup(idClient);

    public ViewClientConsole(){
    }


    public void update(ModelChangedMessage message){
        if(message instanceof ModelChangedMessageConnected){
            this.idClient = ((ModelChangedMessageConnected) message).getIdClient();
        }
        else if(message instanceof ModelChangedMessageRefresh) {
            if (((ModelChangedMessageRefresh) message).getGamePhase() != gamePhase) {
                gamePhase = ((ModelChangedMessageRefresh) message).getGamePhase(); //cambia anche per end game
                if(gamePhase == GAMEPHASE)
                    viewClientConsolePrint = new ViewClientConsoleGame(idClient);
            }
            viewClientConsolePrint.print();
        } else {
            viewClientConsolePrint.update(message);
        }
    }

    public String askForName(){
        String name = "";

        System.out.println("Insert ID die");
        Scanner input = new Scanner(System.in);
        String s = input.nextLine();
        name = s;
        input.close();

        return name;

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