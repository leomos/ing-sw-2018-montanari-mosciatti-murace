package it.polimi.se2018.view.console;

public class ComplexInputParserImplementation implements InputParserInterface {
    @Override
    public void parse(String command) {
        System.out.println("Complesso: " + command);
    }
}
