package it.polimi.se2018.network.server;

import it.polimi.se2018.model.events.HeartbeatMessage;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

public class HeartbeatHandler extends Thread {

    private HashMap<Integer, Optional<HeartbeatMessage>> heartbeats;

    private List<Integer> waitingFirstHeartbeat;

    public HeartbeatHandler() {
        this.heartbeats = new HashMap<>();
        this.waitingFirstHeartbeat = new CopyOnWriteArrayList<>();
    }

    public void addHeartbeater(int id) {
        heartbeats.put(id, Optional.empty());
    }

    public void heartbeat(HeartbeatMessage heartbeatMessage) {
        heartbeats.put(heartbeatMessage.getId(), Optional.of(heartbeatMessage));
    }
}
