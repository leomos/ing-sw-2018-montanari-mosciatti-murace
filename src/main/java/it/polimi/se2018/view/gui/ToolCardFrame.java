package it.polimi.se2018.view.gui;

import javax.swing.*;
import java.awt.*;

public class ToolCardFrame extends Frame {
    public ToolCardFrame(String s) {
        super();
        dialog.setTitle("TOOLCARD");
        JLabel label = new JLabel(s, SwingConstants.CENTER);
        dialog.setSize(300, 100);
        dialog.setLocation ((screenSize.width-300)/2, (screenSize.height-100)/2);
        dialog.add(label, BorderLayout.CENTER);
    }
}
