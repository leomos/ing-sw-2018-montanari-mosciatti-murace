package it.polimi.se2018.view.gui;

import javax.swing.*;
import java.awt.*;

public class DefeatFrame extends Frame {
    public DefeatFrame() {
        super();
        dialog.setTitle("RESULT");
        label = new JLabel("YOU LOSE!!", SwingConstants.CENTER);
        dialog.add(label, BorderLayout.CENTER);
        dialog.setSize(200, 100);
        dialog.setLocation ((screenSize.width-200)/2, (screenSize.height-100)/2);
        dialog.setVisible(true);
    }
}
