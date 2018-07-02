package it.polimi.se2018.view.gui;

import javax.swing.*;
import java.awt.*;

public class MoveFailedFrame extends Frame {
    public MoveFailedFrame(String s) {
        super();
        setTitle("MOVE FAILED");
        label = new JLabel(s, SwingConstants.CENTER);
        add(label, BorderLayout.CENTER);
    }
}
