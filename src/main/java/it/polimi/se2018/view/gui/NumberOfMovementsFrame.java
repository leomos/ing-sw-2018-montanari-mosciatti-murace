package it.polimi.se2018.view.gui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class NumberOfMovementsFrame extends ToolCardFrame {

    private int i = -1;

    public NumberOfMovementsFrame(){

        JFrame frame = new JFrame();
        new JDialog(frame, "", true);
        setLayout(new BorderLayout());

        JLabel label = new JLabel("How many movements do you want to perform?", SwingConstants.CENTER);

        JRadioButton oneMovementButton = new JRadioButton();
        JRadioButton twoMovementsButton = new JRadioButton();
        ButtonGroup bg = new ButtonGroup();
        bg.add(oneMovementButton);
        bg.add(twoMovementsButton);

        JPanel buttonPanel = new JPanel();

        oneMovementButton.setText("ONE MOVEMENT");
        twoMovementsButton.setText("TWO MOVEMENTS");

        buttonPanel.add(oneMovementButton);
        buttonPanel.add(twoMovementsButton);
        oneMovementButton.setSelected(true);

        JButton button = new JButton("CONTINUE");
        button.addActionListener(actionListener -> {
                if (oneMovementButton.isSelected())
                    i = 1;
                if (twoMovementsButton.isSelected())
                    i = 2;
                dispose();
        });

        add(label, BorderLayout.NORTH);
        add(buttonPanel,BorderLayout.CENTER);
        add(button, BorderLayout.SOUTH);

        setSize(300, 200);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation ((screenSize.width - 300)/2, (screenSize.height - 200)/2);

        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public ArrayList<Integer> getValues() {
        return null;
    }

    @Override
    public int getValue() {
        while(i == -1) {
            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return i;
    }

    @Override
    public void close() {
        setModal(false);
        dispose();
    }
}
