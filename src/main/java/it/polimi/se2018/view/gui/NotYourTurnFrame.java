package it.polimi.se2018.view.gui;

import javax.swing.*;
import java.awt.*;

public class NotYourTurnFrame extends Frame {
    public NotYourTurnFrame() {
        super();
        label = new JLabel("It's not your turn!!", SwingConstants.CENTER);
        add(label, BorderLayout.CENTER);
    }
}
