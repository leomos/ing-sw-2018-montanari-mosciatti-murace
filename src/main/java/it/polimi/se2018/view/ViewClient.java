package it.polimi.se2018.view;

import it.polimi.se2018.model.events.HeartbeatMessage;
import it.polimi.se2018.model.events.ModelChangedMessage;
import it.polimi.se2018.network.ServerInterface;

import java.io.IOException;
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

    public ArrayList<Integer> getDoublePositionInPatternCard(){ return null;}

    public Integer getValueForDie(){
        return 0;
    }

    public Integer askForPatternCard(){
        return null;
    }

    public Boolean block() {return true;}

    public Boolean free() {return true;}

    public abstract void setIdClient(int idClient);

    public void startHeartbeating(int id) {
        task = () -> {
            try {
                HeartbeatMessage heartbeatMessage = new HeartbeatMessage(id, Instant.now());
                serverInterface.sendHeartbeat(heartbeatMessage);
            } catch (IOException e) {
                handleDisconnection();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        };

        int initialDelay = 0;
        int period = 1;
        executor.scheduleAtFixedRate(task, initialDelay, period, TimeUnit.SECONDS);
        System.out.println("Heartbeat started for client " + id);
    }

    public void handleDisconnection() {
        System.out.println("Problema di connessione!");
    }

}
