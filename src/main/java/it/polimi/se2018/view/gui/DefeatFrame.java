package it.polimi.se2018.view.gui;

import javax.swing.*;
import java.awt.*;

public class DefeatFrame extends Frame {
    public DefeatFrame() {
        super();
        label = new JLabel("YOU LOSE!!", SwingConstants.CENTER);
        add(label, BorderLayout.CENTER);
    }
}
