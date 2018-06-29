package it.polimi.se2018.model;

import java.util.concurrent.TimeUnit;

public class Timer extends Thread{

    public static final int timer = 40;

    private boolean stopTimer = false;

    private Model model;

    public Timer(Model model){
        this.model = model;
    }

    @Override
    public void run(){

        stopTimer = false;
        int i = 0;
        System.out.println("è cominciato un nuovo timer");
        while(!stopTimer && i < timer) {



            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            i++;
        }

        if(i >= timer) {
            System.out.println("è scaduto il timer!");
            model.timesUp();
        }


    }

    public void stopTimer(){

        stopTimer = true;
        System.out.println("ho stoppato il timer!");

    }


}
