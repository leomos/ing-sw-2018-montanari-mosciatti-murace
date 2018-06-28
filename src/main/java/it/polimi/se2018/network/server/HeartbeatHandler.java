package it.polimi.se2018.network.server;

import it.polimi.se2018.model.events.HeartbeatMessage;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class HeartbeatHandler extends Thread {

    private int controlRateInSeconds;

    private int disconnectedThreshold;

    private HashMap<Integer, Instant> heartbeats;

    private boolean heartbeatRunning = true;

    private SimpleRoomDispatcherImplementation simpleRoomDispatcherImplementation;

    public HeartbeatHandler(int controlRateInSeconds, int disconnectedThreshold, SimpleRoomDispatcherImplementation simpleRoomDispatcherImplementation) {
        this.controlRateInSeconds = controlRateInSeconds;
        this.heartbeats = new HashMap<>();
        this.disconnectedThreshold = disconnectedThreshold;
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
                        if (Duration.between(received, Instant.now()).getSeconds() > disconnectedThreshold) {
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
                TimeUnit.SECONDS.sleep(controlRateInSeconds);
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
