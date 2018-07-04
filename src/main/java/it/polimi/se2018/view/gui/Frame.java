package it.polimi.se2018.view.gui;

import javax.swing.*;
import java.awt.*;

public class Frame extends JDialog {
    protected JLabel label;

    protected JButton ok;

    protected  Dimension screenSize = Toolkit.getDefaultToolkit ().getScreenSize ();

    JFrame frame = new JFrame();

    public Frame() {
        new JDialog(frame, "", true);
        JButton ok = new JButton("OK");
        ok.addActionListener(actionListener -> {
                dispose();
        });
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());
        add(ok, BorderLayout.SOUTH);
        setVisible(true);
    }
}
