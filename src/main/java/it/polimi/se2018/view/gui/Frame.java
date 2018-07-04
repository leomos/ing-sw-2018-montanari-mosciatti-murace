package it.polimi.se2018.view.gui;

import javax.swing.*;
import java.awt.*;

public class Frame {
    //protected JDialog dialog;

    protected JLabel label;

    protected JButton ok;

    protected  Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    JFrame dialog = new JFrame();

    public Frame() {
        //dialog = new JDialog(frame, "", true);
        JButton ok = new JButton("OK");
        ok.addActionListener(actionListener -> {
                dialog.dispose();
        });

        dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        dialog.setResizable(false);
        dialog.setLayout(new BorderLayout());
        dialog.add(ok, BorderLayout.SOUTH);
        //dialog.setVisible(true);
    }
}
