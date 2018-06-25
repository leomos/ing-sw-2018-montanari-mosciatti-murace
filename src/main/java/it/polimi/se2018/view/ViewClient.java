package it.polimi.se2018.view;

import it.polimi.se2018.model.events.HeartbeatMessage;
import it.polimi.se2018.model.events.ModelChangedMessage;
import it.polimi.se2018.network.ServerInterface;

import java.rmi.RemoteException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

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

    public ArrayList<Integer> getDieFromRoundTrack() {
        return null;
    }

    public Integer getDieFromDiceArena() { return null;}

    public ArrayList<Integer> getIncrementedValue() {
        return null;
    }

    public ArrayList<Integer> getPositionInPatternCard(){
        return null;
    }

    public ArrayList<Integer> getSinglePositionInPatternCard(ArrayList<Integer> listOfAvailablePositions){
        return null;
    }

    public Integer getValueForDie(){
        return 0;
    }

    public Integer askForPatternCard(){
        return null;
    }

    public void block() {return;}

    public void free() {return;}

    public void startHeartbeating(int id) {
        AtomicReference<HeartbeatMessage> heartbeatMessage = null;
        task = () -> {
            try {
                System.out.println("Sent hb");
                heartbeatMessage.set(new HeartbeatMessage(id, Instant.now()));
                serverInterface.sendHeartbeat(heartbeatMessage.get());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        };

        int initialDelay = 0;
        int period = 1;
        executor.scheduleAtFixedRate(task, initialDelay, period, TimeUnit.SECONDS);
        System.out.println("Heartbeat started for client " + id);
    }

}
