package it.polimi.se2018.network.client;

import it.polimi.se2018.model.events.ModelChangedMessage;
import it.polimi.se2018.view.ViewClient;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientImplementationSocket extends Thread implements ClientInterface {
    private ViewClient viewClient;

    private Socket socket;

    public ClientImplementationSocket(ViewClient viewClient, Socket socket) {
        this.viewClient = viewClient;
        this.socket = socket;
    }

    @Override
    public void update(ModelChangedMessage modelChangedMessage) {
        viewClient.update(modelChangedMessage);
    }

    @Override
    public void run(){
        Scanner scanner = new Scanner(System.in);

        boolean loop = true;
        while ( loop ) {

            System.out.println("\nWrite a message: ");
            String text = scanner.nextLine();

            if ( text.equals("stop") )  {

                scanner.close();
                loop = false;

            } else {

                OutputStreamWriter writer;

                try {

                    writer = new OutputStreamWriter(this.socket.getOutputStream());
                    BufferedWriter bw = new BufferedWriter(writer);
                    PrintWriter pw = new PrintWriter(bw, true);

                    pw.println(text);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }
    }

}
