package it.polimi.se2018.view.gui;

import javax.swing.*;
import java.awt.*;

public class SuspendFrame extends JDialog {
    private JFrame frame;

    public SuspendFrame() {
        frame = new JFrame();
        new JDialog(frame, "SUSPEND", true);

        JLabel label = new JLabel("You are now suspended. Click OK to return to play", SwingConstants.CENTER);
        JButton button = new JButton("OK");
        button.addActionListener(actionListener -> {
            dispose();
        });

        setLayout(new FlowLayout());
        add(label);
        add(button);

        setSize(350, 100);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation ((screenSize.width-400)/2, (screenSize.height-100)/2);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setVisible(true);
    }
}
