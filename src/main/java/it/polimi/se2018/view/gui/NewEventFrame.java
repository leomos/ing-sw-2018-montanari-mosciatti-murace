package it.polimi.se2018.view.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewEventFrame {

    private JDialog dialog;

    public NewEventFrame(String s) {
        JFrame frame = new JFrame();
        dialog = new JDialog(frame, "NEW EVENT", true);

        JLabel label = new JLabel(s);

        JButton button = new JButton("OK");
        button.addActionListener(actionListener -> {
                dialog.dispose();
        });

        dialog.setLayout(new BorderLayout());
        dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        dialog.setSize(200, 100);
        dialog.add(label, BorderLayout.CENTER);
        dialog.add(button, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }
}
