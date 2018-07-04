package it.polimi.se2018.view.gui;

import javax.swing.*;
import java.awt.*;

public class ToolCardFrame extends Frame {
    public ToolCardFrame(String s) {
        super();
        dialog.setTitle("TOOLCARD");
        JLabel label = new JLabel(s, SwingConstants.CENTER);
        dialog.setSize(300, 100);
        Dimension frameSize = dialog.getSize ();
        dialog.setLocation ((screenSize.width-frameSize.width)/2, (screenSize.height-frameSize.height)/2);
        dialog.add(label, BorderLayout.CENTER);
    }
}
