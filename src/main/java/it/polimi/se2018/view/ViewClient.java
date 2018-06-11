package it.polimi.se2018.view;

import it.polimi.se2018.model.events.ModelChangedMessage;
import it.polimi.se2018.network.ServerInterface;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public abstract class ViewClient {

    private ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    private Runnable task;

    protected ServerInterface serverInterface;

    public void update(ModelChangedMessage modelChangedMessage) {
    }


    public void setServerInterface(ServerInterface serverInterface) {
        this.serverInterface = serverInterface;
    }

    public String askForName() { return null; }

    public Integer getDieFromPatternCard() {
        return 0;
    }

    public Integer getDieFromRoundTrack() {
        return 0;
    }

    public Boolean getIncrementedValue() {
        return true;
    }

    public ArrayList<Integer> getPositionInPatternCard(){
        return null;
    }

    public Integer getValueForDie(){
        return 0;
    }

    public Integer askForPatternCard(){
        return null;
    }

    public void startHeartbeating(int id) {

        task = () -> {
            try {
                serverInterface.sendHeartbeet(id);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        };

        int initialDelay = 0;
        int period = 1;
        executor.scheduleAtFixedRate(task, initialDelay, period, TimeUnit.SECONDS);
    }

}
