package it.polimi.se2018.view.gui;

import javax.swing.*;
import java.awt.*;

public class NotYourTurnFrame extends Frame {
    public NotYourTurnFrame() {
        super();
        dialog.setTitle("TURN");
        label = new JLabel("It's not your turn!!", SwingConstants.CENTER);
        dialog.add(label, BorderLayout.CENTER);
        dialog.setSize(200, 100);
        dialog.setLocation ((screenSize.width-200)/2, (screenSize.height-100)/2);
        dialog.setVisible(true);
    }
}
