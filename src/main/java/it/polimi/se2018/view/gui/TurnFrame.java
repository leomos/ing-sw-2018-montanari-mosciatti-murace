package it.polimi.se2018.view.gui;

import javax.swing.*;
import java.awt.*;

public class TurnFrame extends Frame {
    public TurnFrame() {
        super();
        setTitle("TURN");
        setSize(200, 100);
        label = new JLabel("It's your turn!", SwingConstants.CENTER);
        add(label, BorderLayout.CENTER);
        Dimension frameSize = getSize ();
        setLocation ((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
    }
}
