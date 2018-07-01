package it.polimi.se2018.view.gui;

import javax.swing.*;
import java.awt.*;

public class MoveFailedFrame extends Frame {
    public MoveFailedFrame() {
        super();
        label = new JLabel("Move failed... Try again", SwingConstants.CENTER);
        add(label, BorderLayout.CENTER);
    }
}
