package it.polimi.se2018.network.server;

import it.polimi.se2018.network.ClientGathererInterface;
import it.polimi.se2018.network.RoomDispatcherInterface;

import java.rmi.RemoteException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private ExecutorService executorService;

    public static void main(String[] args) {
        RoomDispatcherInterface roomDispatcher = new SimpleRoomDispatcherImplementation(10, 1);

        ClientGathererInterface clientGathererSocket = new ClientGathererImplementationSocket(1111);
        ClientGathererInterface clientGathererRMI = null;
        try {
            clientGathererRMI = new ServerImplementationRMI(1099, "localhost", "sagrada");
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        clientGathererSocket.setRoomDispatcher(roomDispatcher);
        clientGathererRMI.setRoomDispatcher(roomDispatcher);

        Server server = new Server();
        server.startServer(roomDispatcher, clientGathererSocket, clientGathererRMI);
    }

    private void startServer(RoomDispatcherInterface roomDispatcherInterface, ClientGathererInterface... clientGathererInterfaces) {
        this.executorService = Executors.newFixedThreadPool(clientGathererInterfaces.length + 1);
        this.executorService.submit(roomDispatcherInterface);
        for (ClientGathererInterface clientGathererInterface : clientGathererInterfaces) {
            executorService.submit(clientGathererInterface);
        }
    }
}
