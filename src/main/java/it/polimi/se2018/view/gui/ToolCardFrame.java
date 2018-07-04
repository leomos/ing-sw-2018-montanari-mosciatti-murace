package it.polimi.se2018.view.gui;

import javax.swing.*;
import java.awt.*;

public class ToolCardFrame extends Frame {
    public ToolCardFrame(String s) {
        super();
        setTitle("TOOLCARD");
        JLabel label = new JLabel(s, SwingConstants.CENTER);
        setSize(300, 100);
        Dimension frameSize = getSize ();
        setLocation ((screenSize.width-frameSize.width)/2, (screenSize.height-frameSize.height)/2);
        add(label, BorderLayout.CENTER);
    }
}
