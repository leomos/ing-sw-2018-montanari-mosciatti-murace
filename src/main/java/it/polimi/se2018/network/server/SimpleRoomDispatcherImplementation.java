package it.polimi.se2018.network.server;

import it.polimi.se2018.network.ClientInterface;
import it.polimi.se2018.network.RoomDispatcherInterface;
import it.polimi.se2018.utils.Triplet;

import java.util.*;
import java.util.concurrent.*;
import java.util.logging.Logger;

public class SimpleRoomDispatcherImplementation implements RoomDispatcherInterface {

    private static final Logger LOGGER = Logger.getLogger(SimpleRoomDispatcherImplementation.class.getName());

    private ConcurrentMap<Room, Set<Triplet>> rooms;

    private Queue<Triplet> currentClientsWaiting;

    private int id = 0;

    private int countdownLength;

    private boolean roomRunning;


    public SimpleRoomDispatcherImplementation(int countdownLength) {
        this.countdownLength = countdownLength;
        this.roomRunning = true;
        this.rooms = new ConcurrentHashMap<Room, Set<Triplet>>();
        this.currentClientsWaiting = new ConcurrentLinkedQueue<>();
    }

    @Override
    public void run() {
        while(roomRunning) {
            LOGGER.info("Waiting for at least 2 clients.");
            while (currentClientsWaiting.size() < 2) {
                try {
                    TimeUnit.SECONDS.sleep(5);
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
            Triplet c;
            Set<Triplet> clients = new HashSet<>();
            int i = 0;
            while ((c = currentClientsWaiting.poll()) != null && i < 4) {
                i++;
                clients.add(c);
            }

            startNewRoom(clients);

            LOGGER.info("Room started with " + clients.size() + " clients.");
        }
    }

    private void startNewRoom(Set<Triplet> clients) {
        Room room = new Room();

        clients.iterator().forEachRemaining( client -> {
            room.addClient((ClientInterface) client.z);
        });

        this.rooms.putIfAbsent(room, clients);

        room.start();
    }

    @Override
    public synchronized Integer handleClient(ClientInterface clientInterface, String name) {
        id++;
        Triplet<Integer, String, ClientInterface> client = new Triplet<>(id, name, clientInterface);
        LOGGER.info("New client to dispatch: " + id + " " + name);
        System.out.println("CLIENTS ACTUALLY WAITING: ");
        currentClientsWaiting.add(client);

        currentClientsWaiting.forEach(c -> {
            System.out.println(c.toString());
        });
        return id;
    }

    public void stopDispatcher() {
        rooms.forEach((room, client) -> {
            room.stop();
        });
    }
}
