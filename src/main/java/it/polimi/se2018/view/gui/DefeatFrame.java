package it.polimi.se2018.view.gui;

import javax.swing.*;
import java.awt.*;

public class DefeatFrame extends Frame {
    public DefeatFrame() {
        super();
        label = new JLabel("YOU LOSE!!", SwingConstants.CENTER);
        add(label, BorderLayout.CENTER);
        setSize(200, 100);
        Dimension frameSize = getSize ();
        setLocation ((screenSize.width-frameSize.width)/2, (screenSize.height-frameSize.height)/2);
    }
}
