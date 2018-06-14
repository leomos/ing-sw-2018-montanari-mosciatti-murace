package it.polimi.se2018.view;

import it.polimi.se2018.model.events.HeartbeatMessage;
import it.polimi.se2018.model.events.ModelChangedMessage;
import it.polimi.se2018.network.ServerInterface;

import java.time.Instant;
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
        return null;
    }

    public Integer getDieFromRoundTrack() {
        return null;
    }

    public Integer getDieFromDiceArena() { return null;}

    public ArrayList<Integer> getIncrementedValue() {
        return null;
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
                HeartbeatMessage heartbeatMessage = new HeartbeatMessage(id, Instant.now());
                serverInterface.sendHeartbeat(heartbeatMessage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        int initialDelay = 0;
        int period = 1;
        executor.scheduleAtFixedRate(task, initialDelay, period, TimeUnit.SECONDS);
        System.out.println("Heartbeat started for client " + id);
    }

}
