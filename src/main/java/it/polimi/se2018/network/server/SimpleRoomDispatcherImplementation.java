package it.polimi.se2018.network.server;

import it.polimi.se2018.model.events.HeartbeatMessage;
import it.polimi.se2018.network.ClientInterface;
import it.polimi.se2018.network.ConnectedClient;
import it.polimi.se2018.network.RoomDispatcherInterface;
import it.polimi.se2018.network.client.Client;

import java.util.*;
import java.util.concurrent.*;
import java.util.logging.Logger;

public class SimpleRoomDispatcherImplementation implements RoomDispatcherInterface {

    private static final Logger LOGGER = Logger.getLogger(SimpleRoomDispatcherImplementation.class.getName());

    private Set<Room> rooms;

    private Set<ConnectedClient> connectedClients;

    private Queue<ConnectedClient> currentClientsWaiting;

    private int id = 0;

    private int countdownLength;

    private int refreshRate;

    private boolean roomDispatcherRunning;

    private HeartbeatHandler heartbeatHandler;

    private Map<Integer, Room> clientRoomMap;


    public SimpleRoomDispatcherImplementation(int countdownLength, int refreshRate) {
        this.countdownLength = countdownLength;
        this.refreshRate = refreshRate;
        this.roomDispatcherRunning = true;
        this.currentClientsWaiting = new ConcurrentLinkedQueue<>();
        this.connectedClients = new HashSet<>();
        this.rooms = new HashSet<>();
        this.heartbeatHandler = new HeartbeatHandler(2, 5, this);
        this.clientRoomMap = new HashMap<>();
    }

    @Override
    public void run() {
        heartbeatHandler.start();
        while(roomDispatcherRunning) {
            LOGGER.info("Waiting for at least 2 clients.");
            while (currentClientsWaiting.size() < 2) {
                try {
                    TimeUnit.SECONDS.sleep(this.refreshRate);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            LOGGER.info("Two clients or more are waiting.");
            LOGGER.info("Starting the countdown.");
            try {
                TimeUnit.SECONDS.sleep(countdownLength);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LOGGER.info("Countdown ended, starting the room.");
            ConnectedClient c;
            Set<ConnectedClient> clients = new HashSet<>();
            int i = 0;
            while ((c = currentClientsWaiting.poll()) != null && i < 4) {
                i++;
                clients.add(c);
            }

            startNewRoom(clients);

            LOGGER.info("Room started with " + clients.size() + " clients.");
        }
    }

    private synchronized void startNewRoom(Set<ConnectedClient> clients) {
        Room room = new Room();

        room.addPlayers(clients);

        clients.forEach(client -> {
            clientRoomMap.put(client.getId(), room);
            connectedClients.add(client);
        });

        room.start();

        rooms.add(room);

    }

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
        clientRoomMap.get(id).handleClientDisconnection(id);
    }

    public Boolean reconnectClient(ClientInterface clientInterface, int id) {
        return getRoomForId(id).reconnectPlayer(clientInterface, id);
    }
}
