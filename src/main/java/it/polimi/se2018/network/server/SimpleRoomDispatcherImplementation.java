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

    private int countdownLength;

    private int refreshRate;

    private boolean roomDispatcherRunning;

    private HeartbeatHandler heartbeatHandler;


    public SimpleRoomDispatcherImplementation(int countdownLength, int refreshRate) {
        this.countdownLength = countdownLength;
        this.refreshRate = refreshRate;
        this.roomDispatcherRunning = true;
        this.currentClientsWaiting = new ConcurrentLinkedQueue<>();
        this.connectedClients = new HashSet<>();
        this.rooms = new HashSet<>();
        this.heartbeatHandler = new HeartbeatHandler(5);
    }

    @Override
    public void run() {
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
            client.setRoom(room);
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
            room.stop();
        });

        heartbeatHandler.stopHeartbeatRunning();

        roomDispatcherRunning = false;
    }

    @Override
    public synchronized void hearbeat(HeartbeatMessage heartbeatMessage) {
        System.out.println("New hearbeat: " + heartbeatMessage.getId());
        heartbeatHandler.heartbeat(heartbeatMessage);
    }
}
