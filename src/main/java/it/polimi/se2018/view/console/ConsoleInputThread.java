package it.polimi.se2018.view.console;

import java.util.Scanner;

public class ConsoleInputThread extends Thread {

    private Scanner scanner;

    private InputParserInterface inputParserInterface;

    private Object mutex;

    private boolean acceptInput = true;

    public ConsoleInputThread() {
        scanner = new Scanner(System.in);
    }

    @Override
    public void run() {
        String input;
        while(scanner.hasNextLine()) {
            input = scanner.nextLine();
            synchronized (this) {
                inputParserInterface.parse(input);
            }
        }
    }

    public synchronized void setInputParserInterface(InputParserInterface inputParserInterface) {
        this.inputParserInterface = inputParserInterface;
    }
}
