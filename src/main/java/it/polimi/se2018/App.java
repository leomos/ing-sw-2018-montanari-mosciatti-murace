package it.polimi.se2018;


import it.polimi.se2018.model.events.MethodCallMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        //System.out.println( "Hello World!" );
        Socket s = null;
        try {
            s = new Socket("localhost", 1111);
            MethodCallMessage mc = new MethodCallMessage("registerNewClient");
            mc.addArgument("name", "gianni");
            (new ObjectOutputStream(s.getOutputStream())).writeObject(mc);
            System.out.println(((MethodCallMessage)(new ObjectInputStream(s.getInputStream())).readObject()).getReturnValue());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                s.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
