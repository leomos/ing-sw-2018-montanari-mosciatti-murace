package it.polimi.se2018.view.gui;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Abstract class that contains methods used by the classes that are used when the player use a ToolCard
 */
public abstract class ToolCardFrame extends JDialog {

    public abstract ArrayList<Integer> getValues();

    public abstract int getValue();

    /**
     * Method to close the JDialog
     */
    public abstract void close();
}
