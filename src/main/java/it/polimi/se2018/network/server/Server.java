package it.polimi.se2018.network.server;

import it.polimi.se2018.network.ClientGathererInterface;
import it.polimi.se2018.network.RoomDispatcherInterface;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Main process for the server.
 * This class make possibile the interaction between gatherers and dispatcher, which are the respectively
 * responsible for handling the connections and the lifecycle of the rooms.
 * The gatherer is continuosly listening for new connections (be them via socket, RMI or else). When a new connection
 * is established it passes the newly created interface to the dispatcher.
 * The dispatcher now manages the newly arrived interface.
 */
public class Server {

    private ExecutorService executorService;

    /**
     * Parse the options, creates dispatcher and gatherers and start them
     * @param args options passed via command line
     */
    public static void main(String[] args) {
        final OptionParser optionParser = new OptionParser();

        final String[] roomTimerOptions = {
                "r",
                "roomTimer"
        };

        optionParser.acceptsAll(Arrays.asList(roomTimerOptions), "Room starting countdown value.")
                .withRequiredArg()
                .ofType(Integer.class)
                .defaultsTo(10);


        final String[] turnTimerOptions = {
                "t",
                "turnTimer"
        };

        optionParser.acceptsAll(Arrays.asList(turnTimerOptions), "Turn timer countdown value.")
                .withRequiredArg()
                .ofType(Integer.class)
                .defaultsTo(90);


        final String[] serverHostnameOptions = {
                "s",
                "serverHostname"
        };

        optionParser.acceptsAll(Arrays.asList(serverHostnameOptions), "Server hostname.")
                .withRequiredArg()
                .ofType(String.class)
                .defaultsTo("localhost");

        final OptionSet optionSet = optionParser.parse(args);

        System.out.println(optionSet.asMap());

        System.setProperty("java.rmi.server.hostname", (String) optionSet.valueOf("serverHostname"));

        RoomDispatcherInterface roomDispatcher = new SimpleRoomDispatcherImplementation(
                (Integer) optionSet.valueOf("roomTimer"),
                1,
                (Integer) optionSet.valueOf("turnTimer"));

        ClientGathererInterface clientGathererSocket = new ClientGathererImplementationSocket(1111);
        ClientGathererInterface clientGathererRMI = null;
        try {
            clientGathererRMI = new ServerImplementationRMI(8080, "localhost", "sagrada");
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        clientGathererSocket.setRoomDispatcher(roomDispatcher);
        clientGathererRMI.setRoomDispatcher(roomDispatcher);

        Server server = new Server();
        server.startServer(roomDispatcher, clientGathererSocket, clientGathererRMI);
    }


    /**
     * Starts dispatcher and gatherers.
     * @param roomDispatcherInterface Implementation of the RoomDispatcherInterface to use
     * @param clientGathererInterfaces Array of implementations of the ClientGathererInterface to use
     */
    private void startServer(RoomDispatcherInterface roomDispatcherInterface, ClientGathererInterface... clientGathererInterfaces) {
        this.executorService = Executors.newFixedThreadPool(clientGathererInterfaces.length + 1);
        this.executorService.submit(roomDispatcherInterface);
        for (ClientGathererInterface clientGathererInterface : clientGathererInterfaces) {
            executorService.submit(clientGathererInterface);
        }
    }
}
