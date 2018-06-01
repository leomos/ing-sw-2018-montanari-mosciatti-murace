package it.polimi.se2018.view;

import it.polimi.se2018.model.GamePhase;
import it.polimi.se2018.model.events.ModelChangedMessage;
import it.polimi.se2018.model.events.ModelChangedMessageRefresh;

import java.util.Scanner;

public class ViewClientConsole extends ViewClient {

    private int idClient;

    private GamePhase gamePhase = GamePhase.SETUPPHASE;

    private ViewClientConsolePrint viewClientConsolePrint = new ViewClientConsoleSetup(idClient);

    public ViewClientConsole(){

    }


    public void update(ModelChangedMessage message){
        if(message instanceof ModelChangedMessageRefresh) {
            if (((ModelChangedMessageRefresh) message).getGamePhase() != gamePhase)
                gamePhase = ((ModelChangedMessageRefresh) message).getGamePhase(); //cambia anche la viewClientConsolePrint

            viewClientConsolePrint.print();
        } else {
            viewClientConsolePrint.update(message);
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