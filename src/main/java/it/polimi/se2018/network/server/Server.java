package it.polimi.se2018.network.server;

import it.polimi.se2018.network.ClientGathererInterface;
import it.polimi.se2018.network.RoomDispatcherInterface;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private ExecutorService executorService;

    public static void main(String[] args) {
        RoomDispatcherInterface roomDispatcher = new SimpleRoomDispatcherImplementation(3);

        ClientGathererInterface clientGathererSocket = new ClientGathererImplementationSocket(1111);
        clientGathererSocket.setRoomDispatcher(roomDispatcher);

        Server server = new Server();
        server.startServer(roomDispatcher, clientGathererSocket);
    }

    private void startServer(RoomDispatcherInterface roomDispatcherInterface, ClientGathererInterface... clientGathererInterfaces) {
        this.executorService = Executors.newFixedThreadPool(clientGathererInterfaces.length + 1);
        this.executorService.submit(roomDispatcherInterface);
        for (ClientGathererInterface clientGathererInterface : clientGathererInterfaces) {
            executorService.submit(clientGathererInterface);
        }
    }
}
