package it.polimi.se2018.view.gui;

import javax.swing.*;
import java.awt.*;

public class WinFrame extends Frame {
    public WinFrame() {
        super();
        dialog.setTitle("RESULT");
        dialog.setSize(200, 100);
        label = new JLabel("CONGRATS, YOU WIN!!", SwingConstants.CENTER);
        dialog.add(label, BorderLayout.CENTER);
        dialog.setLocation ((screenSize.width-200)/2, (screenSize.height-100)/2);
        dialog.setVisible(true);
    }
}
