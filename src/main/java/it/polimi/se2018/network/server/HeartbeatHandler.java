package it.polimi.se2018.network.server;

import it.polimi.se2018.model.events.HeartbeatMessage;

import java.time.Duration;
import java.time.Instant;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * A process controlling that clients are still connected to the server
 */
public class HeartbeatHandler extends Thread {

    private int controlRateInMillieconds;

    private int disconnectedThresholdInMilliseconds;

    private HashMap<Integer, Instant> heartbeats;

    private boolean heartbeatRunning = true;

    private SimpleRoomDispatcherImplementation simpleRoomDispatcherImplementation;


    /**
     * Constructs a new HeatbeatHandler with specific parameters
     * @param controlRateInMillieconds milliseconds to wait before controlling again the heartbeats
     * @param disconnectedThresholdInMilliseconds threshold in milliseconds for marking a client as disconnected
     * @param simpleRoomDispatcherImplementation dispatcher to manage connections and disconnections
     */
    public HeartbeatHandler(int controlRateInMillieconds, int disconnectedThresholdInMilliseconds, SimpleRoomDispatcherImplementation simpleRoomDispatcherImplementation) {
        this.controlRateInMillieconds = controlRateInMillieconds;
        this.heartbeats = new HashMap<>();
        this.disconnectedThresholdInMilliseconds = disconnectedThresholdInMilliseconds;
        this.simpleRoomDispatcherImplementation = simpleRoomDispatcherImplementation;
    }

    /**
     * Runs while heartbeatRunning is true.
     * Every cycle controls that the heartbeats' offsets with the current moment is not above the threshold,
     * otherwise notifies the dispatcher and removes the client from the map (see heartbeat).
     */
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
                    //e.printStackTrace();
                }
            }
            try {
                TimeUnit.MILLISECONDS.sleep(controlRateInMillieconds);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * When a heartbeat arrives it updates the map containing the last heartbeats with the instants in which the arrived
     * @param heartbeatMessage message of the heartbeat
     */
    public synchronized void heartbeat(HeartbeatMessage heartbeatMessage) {
        Instant receivedInstant = Instant.now();
        heartbeats.put(heartbeatMessage.getId(), receivedInstant);
    }

    /**
     *  Sets heartbeatRunning to false
     */
    public void stopHeartbeatRunning() {
        heartbeatRunning = false;
    }

}
