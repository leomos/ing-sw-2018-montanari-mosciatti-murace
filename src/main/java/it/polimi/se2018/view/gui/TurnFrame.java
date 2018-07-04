package it.polimi.se2018.view.gui;

import javax.swing.*;
import java.awt.*;

public class TurnFrame extends Frame {
    public TurnFrame() {
        super();
        dialog.setTitle("TURN");
        dialog.setSize(200, 100);
        label = new JLabel("It's your turn!", SwingConstants.CENTER);
        dialog.add(label, BorderLayout.CENTER);
        dialog.setLocation ((screenSize.width - 200) / 2, (screenSize.height - 100) / 2);
        dialog.setVisible(true);
    }
}
