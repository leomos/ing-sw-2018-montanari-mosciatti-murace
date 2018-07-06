package it.polimi.se2018.model;

import java.util.concurrent.TimeUnit;

public class Timer implements Runnable {

    private int timer;

    private boolean stopTimer = false;

    private Model model;

    private int i;

    public Timer(int timer) {
        this.timer = timer;
    }

    /**
     * Timer starts at 0 and ends at the value of timer. If a player sends a end turn, the timer is set back to 0.
     * If the timer reaches the end of the while, the method timesup is invoked ant the current player playing is
     * set to suspended
     */
    @Override
    public void run(){

        do {

            i = 0;
            while (i < timer && !stopTimer) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
                i++;
                if(i == timer-15) model.timeIsRunningOut();
            }

            if (i >= timer) {
                System.out.println("è scaduto il timer!");
                model.timesUp();
            }

        }while(!stopTimer);

    }

    public void setModel(Model model) {
        this.model = model;
    }

    /**
     * Timer goes back to 0
     */
    public void reStartTimer(){
        i = 0;
    }

    /**
     * Timer is completely stopped. It happens when the game is over
     */
    public void stopTimer(){
        stopTimer = true;
    }

    /**
     * Starts a thread with the timer
     */
    public void startTimer() {
        Thread thread;
        thread = new Thread(this);
        thread.start();
    }


}
