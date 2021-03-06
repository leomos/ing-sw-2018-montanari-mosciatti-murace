package it.polimi.se2018.view.gui;

import javax.swing.*;
import java.awt.*;

public class NewEventFrame {

    private JDialog dialog;

    /**
     * This constructor creates a JDialog that shows the message of String s
     * @param s is the message to show
     */
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

        Dimension screenSize = Toolkit.getDefaultToolkit ().getScreenSize();
        dialog.setLocation ((screenSize.width - 200) / 2, (screenSize.height - 100) / 2);

        dialog.setVisible(true);
    }
}
