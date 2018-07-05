package it.polimi.se2018.view.gui;

import javax.swing.*;
import java.util.ArrayList;

public abstract class ToolCardFrame extends JDialog {

    public abstract ArrayList<Integer> getValues();

    public abstract int getValue();

    public abstract void close();
}
