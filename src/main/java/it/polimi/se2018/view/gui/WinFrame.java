package it.polimi.se2018.view.gui;

import javax.swing.*;
import java.awt.*;

public class WinFrame extends Frame {
    public WinFrame() {
        super();
        label = new JLabel("CONGRATS, YOU WIN!!", SwingConstants.CENTER);
        add(label, BorderLayout.CENTER);
    }
}
