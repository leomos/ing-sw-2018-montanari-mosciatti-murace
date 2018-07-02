package it.polimi.se2018.view;

import it.polimi.se2018.model.events.HeartbeatMessage;
import it.polimi.se2018.model.events.ModelChangedMessage;
import it.polimi.se2018.network.ClientInterface;
import it.polimi.se2018.network.ServerInterface;
import it.polimi.se2018.network.client.ClientImplementationRMI;
import it.polimi.se2018.network.client.ServerImplementationSocket;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.Instant;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public abstract class ViewClient {

    protected ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    private Runnable task;

    protected ServerInterface serverInterface;

    public synchronized void update(ModelChangedMessage modelChangedMessage) {
    }

    public void setServerInterface(ServerInterface serverInterface) {
        this.serverInterface = serverInterface;
    }

    public Integer getDieFromPatternCard() {
        return null;
    }

    public ArrayList<Integer> getDieFromRoundTrack() {
        return null;
    }

    public Integer getDieFromDiceArena() { return null;}

    public ArrayList<Integer> getIncrementedValue() {
        return null;
    }

    public ArrayList<Integer> getPositionInPatternCard(){
        return null;
    }

    public ArrayList<Integer> getSinglePositionInPatternCard(ArrayList<Integer> listOfAvailablePositions){
        return null;
    }

    public ArrayList<Integer> getDoublePositionInPatternCard(){ return null;}

    public Integer getValueForDie(){
        return 0;
    }

    public Integer askForPatternCard(){
        return null;
    }

    public Boolean block() {return true;}

    public Boolean free() {return true;}

    public abstract void setIdClient(int idClient);

    public void startHeartbeating(int id) {
        task = () -> {
            try {
                HeartbeatMessage heartbeatMessage = new HeartbeatMessage(id, Instant.now());
                serverInterface.sendHeartbeat(heartbeatMessage);
            } catch (IOException e) {
                handleDisconnection();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        };

        int initialDelay = 0;
        int period = 1;
        executor.scheduleAtFixedRate(task, initialDelay, period, TimeUnit.SECONDS);
        System.out.println("Heartbeat started for client " + id);
    }

    public void handleDisconnection() {
        System.out.println("Problema di connessione!");
        // QUESTE DUE OPERAZIONI VANNO FATTE ASSOLUTAMENTE NELLE IMPLEMENTAZIONI DI QUESTO METODO!!!
        this.serverInterface = null;
        this.executor.shutdown();
    }

    protected Boolean reconnect(int id, int connectionType) {
        switch (connectionType) {
            case 0:
                serverInterface = new ServerImplementationSocket(this);
                Socket socket = null;
                try {
                    socket = new Socket("localhost", 1111);
                    System.out.println("socket connesso");
                    return serverInterface.reconnect(socket, id);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 1:
                try {
                    serverInterface = (ServerInterface) Naming.lookup("//localhost/sagrada");
                    ClientImplementationRMI clientImplementationRMI = new ClientImplementationRMI(this);
                    ClientInterface remoteRef = (ClientInterface) UnicastRemoteObject.exportObject(clientImplementationRMI, 0);
                    return serverInterface.reconnect(remoteRef, id);
                } catch (NotBoundException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
        return false;
    }

    protected void initNewExecutor() {
        this.executor = Executors.newScheduledThreadPool(1);
    }

    public abstract void run();

}
