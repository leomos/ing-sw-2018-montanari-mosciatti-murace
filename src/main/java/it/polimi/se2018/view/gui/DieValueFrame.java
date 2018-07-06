package it.polimi.se2018.view.gui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class DieValueFrame extends ToolCardFrame {

    private int value = 0;

    private boolean confirm = false;

    public DieValueFrame() {
        JFrame frame = new JFrame();
        new JDialog(frame, "DIE VALUE", true);

        JLabel label = new JLabel("Select a value", SwingConstants.CENTER);

        JButton button = new JButton("OK");
        button.setEnabled(false);
        button.addActionListener(actionListener -> {
            if(value != 0) {
                confirm = true;
                dispose();
            }
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

        setLayout(new BorderLayout());
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setSize(100, 150);
        add(label, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
        add(button, BorderLayout.SOUTH);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation ((screenSize.width - 100)/2, (screenSize.height - 150)/2);

        setVisible(true);
    }

    @Override
    public ArrayList<Integer> getValues() {
        return null;
    }

    @Override
    public int getValue() {
        while (value==0 || !confirm) {
            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return value;
    }

    @Override
    public void close() {
        setModal(false);
        dispose();
    }
}
