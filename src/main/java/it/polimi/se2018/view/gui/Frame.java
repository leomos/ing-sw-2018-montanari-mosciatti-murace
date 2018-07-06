package it.polimi.se2018.view.gui;

import javax.swing.*;
import java.awt.*;

public class Frame {
    protected JLabel label;

    protected JButton ok;

    protected Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    JFrame dialog = new JFrame();

    /**
     * This constructor creates a JFrame to show a message to the player. It has a button to close it
     */
    public Frame() {
        JButton ok = new JButton("OK");
        ok.addActionListener(actionListener -> {
                dialog.dispose();
        });

        dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        dialog.setResizable(false);
        dialog.setLayout(new BorderLayout());
        dialog.add(ok, BorderLayout.SOUTH);
    }
}
