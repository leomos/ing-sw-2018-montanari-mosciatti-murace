package it.polimi.se2018.view.gui;

import javax.swing.*;
import java.awt.*;

public class MoveFailedFrame extends Frame {
    public MoveFailedFrame(String s) {
        super();
        dialog.setTitle("MOVE FAILED");
        label = new JLabel(s, SwingConstants.CENTER);
        dialog.add(label, BorderLayout.CENTER);
        dialog.setSize(500, 100);
        dialog.setLocation ((screenSize.width-500)/2, (screenSize.height-100)/2);
        dialog.setVisible(true);
    }
}
