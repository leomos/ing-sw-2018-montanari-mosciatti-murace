package it.polimi.se2018.view.gui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DieValueFrame extends ToolCardFrame {
    private JDialog dialog;

    private int value = 0;

    public DieValueFrame() {
        JFrame frame = new JFrame();
        dialog = new JDialog(frame, "DIE VALUE", true);

        JLabel label = new JLabel("Select a value", SwingConstants.CENTER);

        JButton button = new JButton("OK");
        button.setEnabled(false);
        button.addActionListener(actionListener -> {
                dialog.dispose();
        });

        ButtonGroup group = new ButtonGroup();
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.setPreferredSize(new Dimension(20, 100));
        for (int i=0; i<6; i++) {
            JRadioButton rb = new JRadioButton(Integer.toString(i+1));
            group.add(rb);
            panel.add(rb);
            int finalI = i;
            rb.addActionListener(actionListener -> {
                    button.setEnabled(true);
                    value = finalI + 1;
            });
        }

        dialog.setLayout(new BorderLayout());
        dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        dialog.setSize(100, 150);
        dialog.add(label, BorderLayout.NORTH);
        dialog.add(panel, BorderLayout.CENTER);
        dialog.add(button, BorderLayout.SOUTH);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        dialog.setLocation ((screenSize.width - 100)/2, (screenSize.height - 150)/2);

        dialog.setVisible(true);
    }

    @Override
    public ArrayList<Integer> getValues() {
        return null;
    }

    @Override
    public int getValue() {
        while (value==0);
        return value;
    }
}
