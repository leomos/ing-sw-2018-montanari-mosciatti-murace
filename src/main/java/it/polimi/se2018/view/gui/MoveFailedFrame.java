package it.polimi.se2018.view.gui;

import javax.swing.*;
import java.awt.*;

public class MoveFailedFrame extends Frame {
    public MoveFailedFrame(String s) {
        super();
        setTitle("MOVE FAILED");
        label = new JLabel(s, SwingConstants.CENTER);
        add(label, BorderLayout.CENTER);
        setSize(500, 100);
        Dimension frameSize = getSize ();
        setLocation ((screenSize.width-frameSize.width)/2, (screenSize.height-frameSize.height)/2);
    }
}
