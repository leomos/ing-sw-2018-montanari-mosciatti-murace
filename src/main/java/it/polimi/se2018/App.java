package it.polimi.se2018;


import it.polimi.se2018.model.events.MethodCallMessage;
import it.polimi.se2018.view.console.ComplexInputParserImplementation;
import it.polimi.se2018.view.console.ConsoleInputThread;
import it.polimi.se2018.view.console.InputParserInterface;
import it.polimi.se2018.view.console.SimpleInputParserImplementation;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        ConsoleInputThread consoleInputThread = new ConsoleInputThread();
        InputParserInterface simple = new SimpleInputParserImplementation();
        InputParserInterface complex = new ComplexInputParserImplementation();

        consoleInputThread.setInputParserInterface(complex);

        consoleInputThread.start();

        for (int i = 0; i < 10; i++) {
            System.out.println(i);
            if(i%2 == 0)
                consoleInputThread.setInputParserInterface(simple);
            else
                consoleInputThread.setInputParserInterface(complex);
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
