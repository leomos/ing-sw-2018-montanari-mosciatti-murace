package it.polimi.se2018.view.console;

public class SimpleInputParserImplementation implements  InputParserInterface {
    @Override
    public void parse(String command) {
        System.out.println("Comando ricevuto: " + command);
    }
}
