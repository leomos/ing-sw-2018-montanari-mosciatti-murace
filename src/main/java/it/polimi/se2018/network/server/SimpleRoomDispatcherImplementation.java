package it.polimi.se2018.network.server;

import it.polimi.se2018.model.events.HeartbeatMessage;
import it.polimi.se2018.network.ClientInterface;
import it.polimi.se2018.network.ConnectedClient;
import it.polimi.se2018.network.RoomDispatcherInterface;

import java.util.*;
import java.util.concurrent.*;
import java.util.logging.Logger;

public class SimpleRoomDispatcherImplementation implements RoomDispatcherInterface {

    private static final Logger LOGGER = Logger.getLogger(SimpleRoomDispatcherImplementation.class.getName());

    private Set<Room> rooms;

    private Set<ConnectedClient> connectedClients;

    private Queue<ConnectedClient> currentClientsWaiting;

    private int id = 0;

    private int roomCountdownLength;

    private int refreshRate;

    private int turnCountDownLength;

    private boolean roomDispatcherRunning;

    private HeartbeatHandler heartbeatHandler;

    private Map<Integer, Room> clientRoomMap;



    public SimpleRoomDispatcherImplementation(int roomCountdownLength, int refreshRate, int turnCountdownLength) {
        this.roomCountdownLength = roomCountdownLength;
        this.refreshRate = refreshRate;
        this.turnCountDownLength = turnCountdownLength;
        this.roomDispatcherRunning = true;
        this.currentClientsWaiting = new ConcurrentLinkedQueue<>();
        this.connectedClients = new HashSet<>();
        this.rooms = new HashSet<>();
        this.heartbeatHandler = new HeartbeatHandler(250, 750, this);
        this.clientRoomMap = new HashMap<>();
    }

    @Override
    public void run() {
        heartbeatHandler.start();
        while(roomDispatcherRunning) {
            boolean roomStartable = true;
            LOGGER.info("Waiting for at least 2 clients.");
            while (getNumberOfActiveClientsWaiting() < 2) {
                try {
                    TimeUnit.SECONDS.sleep(this.refreshRate);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            LOGGER.info("Two clients or more are waiting.");
            LOGGER.info("Starting the countdown.");
            try {
                for (int i = 0; i < roomCountdownLength; i++) {
                    TimeUnit.SECONDS.sleep(1);
                    if(getNumberOfActiveClientsWaiting() < 2) {
                        i = roomCountdownLength + 1;
                        roomStartable = false;
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(roomStartable) {
                LOGGER.info("Countdown ended, starting the room.");
                ConnectedClient c;
                Set<ConnectedClient> clients = new HashSet<>();
                int i = 0;
                while ((c = currentClientsWaiting.poll()) != null && i < 4) {
                    if(!c.isInactive()) {
                        i++;
                        clients.add(c);
                    }
                }

                startNewRoom(clients);
                LOGGER.info("Room started with " + clients.size() + " clients.");
            } else {
                LOGGER.info("Countdown interrupted: not enough players.");
            }
        }
    }

    private synchronized void startNewRoom(Set<ConnectedClient> clients) {
        Room room = new Room(this.turnCountDownLength);

        room.addPlayers(clients);

        clients.forEach(client -> {
            clientRoomMap.put(client.getId(), room);
            connectedClients.add(client);
        });

        room.start();

        rooms.add(room);

    }

    //todo: problema : stampa anche quelli che diventano inactive
    @Override
    public synchronized Integer handleClient(ClientInterface clientInterface, String name) {
        id++;
        ConnectedClient client = new ConnectedClient(id, name, clientInterface);
        LOGGER.info("New client to dispatch: " + id + " " + name);
        System.out.println("CLIENTS ACTUALLY WAITING: ");
        currentClientsWaiting.add(client);

        currentClientsWaiting.forEach(c -> {
            System.out.println(c.toString());
        });
        return id;
    }

    @Override
    public Set<ConnectedClient> getAllConnectedClients() {
        return connectedClients;
    }

    @Override
    public synchronized void stopDispatcher() {
        rooms.forEach(room -> {
            try {
                room.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        heartbeatHandler.stopHeartbeatRunning();

        roomDispatcherRunning = false;
    }

    @Override
    public Room getRoomForId(int id) {
        return clientRoomMap.get(id);
    }

    @Override
    public synchronized void heartbeat(HeartbeatMessage heartbeatMessage) {
        heartbeatHandler.heartbeat(heartbeatMessage);
    }

    public void setConnectedClientSuspended(int id) {
        if(clientRoomMap.containsKey(id)) {
            clientRoomMap.get(id).handleClientDisconnection(id);
        } else {
            ConnectedClient faulty;
            currentClientsWaiting.forEach(wc -> {
                if(wc.getId() == id)
                    wc.setInactive(true);
            });

        }
    }

    public Boolean reconnectClient(ClientInterface clientInterface, int id) {
        return getRoomForId(id).reconnectPlayer(clientInterface, id);
    }

    public void sendGameStateToReconnectedClient(int id) {
        getRoomForId(id).sendGameStateToReconnectedPlayer(id);
    }

    private int getNumberOfActiveClientsWaiting() {
        return(int) currentClientsWaiting.stream()
                .filter(wc -> !wc.isInactive())
                .count();
    }
}
