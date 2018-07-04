package it.polimi.se2018.view.gui;

import javax.swing.*;
import java.awt.*;

public class WinFrame extends Frame {
    public WinFrame() {
        super();
        setTitle("RESULT");
        setSize(200, 100);
        label = new JLabel("CONGRATS, YOU WIN!!", SwingConstants.CENTER);
        add(label, BorderLayout.CENTER);
        Dimension frameSize = getSize ();
        setLocation ((screenSize.width-frameSize.width)/2, (screenSize.height-frameSize.height)/2);
    }
}
