package it.polimi.se2018.model;

import java.util.concurrent.TimeUnit;

public class Timer implements Runnable {

    /* Singleton implementation */
    /*
    private static volatile Timer timerInstance;

    private Timer() {
        if(timerInstance != null) {
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }

    public static Timer getInstance() {
        if(timerInstance == null) {
            synchronized (Timer.class) {
                if(timerInstance == null) {
                    timerInstance = new Timer();
                }
            }
        }
        return timerInstance;
    }
    */
    /*end Singleton implementation */

    private Thread thread;

    public static final int timer = 30;

    private boolean stopTimer = false;

    private Model model;

    private int i;

    @Override
    public void run(){

        do {

            i = 0;
            System.out.println("è cominciato un nuovo timer");
            while (i < timer && !stopTimer) {


                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                i++;
            }



            if (i >= timer) {
                System.out.println("è scaduto il timer!");
                model.timesUp();
            }

        }while(!stopTimer);

        System.out.println("IL TIMER THREAD è CHIUSO!!!");

    }

    public void setModel(Model model) {
        this.model = model;
    }

    public void reStartTimer(){

        i = 0;
        System.out.println("ho restartato il timer: " + i);

    }

    public void stopTimer(){

        stopTimer = true;
        System.out.println("ho stoppato il timer!");
    }

    public void startTimer() {
        thread = new Thread(this);
        thread.start();
    }


}
