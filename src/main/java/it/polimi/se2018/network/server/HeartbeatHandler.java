package it.polimi.se2018.network.server;

import it.polimi.se2018.model.events.HeartbeatMessage;

import java.time.Duration;
import java.time.Instant;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class HeartbeatHandler extends Thread {

    private int controlRateInMillieconds;

    private int disconnectedThresholdInMilliseconds;

    private HashMap<Integer, Instant> heartbeats;

    private boolean heartbeatRunning = true;

    private SimpleRoomDispatcherImplementation simpleRoomDispatcherImplementation;

    public HeartbeatHandler(int controlRateInMillieconds, int disconnectedThresholdInMilliseconds, SimpleRoomDispatcherImplementation simpleRoomDispatcherImplementation) {
        this.controlRateInMillieconds = controlRateInMillieconds;
        this.heartbeats = new HashMap<>();
        this.disconnectedThresholdInMilliseconds = disconnectedThresholdInMilliseconds;
        this.simpleRoomDispatcherImplementation = simpleRoomDispatcherImplementation;
    }

    @Override
    public void run() {
        while (heartbeatRunning) {
            System.out.println(heartbeats);
            synchronized (this) {
                try {
                    for (Map.Entry<Integer, Instant> heartbeat : heartbeats.entrySet()) {
                        Integer id = heartbeat.getKey();
                        Instant received = heartbeat.getValue();
                        //TODO: controllo che non sia disconnesso
                        if (Duration.between(received, Instant.now()).toMillis() > disconnectedThresholdInMilliseconds) {
                            simpleRoomDispatcherImplementation.setConnectedClientSuspended(id);
                            heartbeats.remove(id);
                            System.out.println(heartbeats);
                        }
                    }
                } catch (ConcurrentModificationException e) {
                    e.printStackTrace();
                }
            }
            try {
                TimeUnit.MILLISECONDS.sleep(controlRateInMillieconds);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void heartbeat(HeartbeatMessage heartbeatMessage) {
        Instant receivedInstant = Instant.now();
        heartbeats.put(heartbeatMessage.getId(), receivedInstant);
    }

    public void stopHeartbeatRunning() {
        heartbeatRunning = false;
    }

}
