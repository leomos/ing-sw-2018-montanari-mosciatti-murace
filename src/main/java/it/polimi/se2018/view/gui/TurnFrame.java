package it.polimi.se2018.view.gui;

import javax.swing.*;
import java.awt.*;

public class TurnFrame extends Frame {
    public TurnFrame() {
        super();
        label = new JLabel("It's your turn!", SwingConstants.CENTER);
        add(label, BorderLayout.CENTER);
    }
}
