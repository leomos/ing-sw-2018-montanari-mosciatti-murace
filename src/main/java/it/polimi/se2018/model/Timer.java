package it.polimi.se2018.model;

import java.util.concurrent.TimeUnit;

public class Timer extends Thread{

    private boolean stopTimer = false;

    private Model model;

    public Timer(Model model){
        this.model = model;
    }

    @Override
    public void run(){

        stopTimer = false;
        int i = 0;

        while(!stopTimer && i < 20) {

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            i++;
        }
        System.out.println("fine timer");

        if(i == 20)
            model.timesUp();


    }

    public void stopTimer(){

        stopTimer = true;

    }


}
