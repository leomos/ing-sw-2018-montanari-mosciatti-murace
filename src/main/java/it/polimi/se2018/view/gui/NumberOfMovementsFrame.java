package it.polimi.se2018.view.gui;

import javax.swing.*;
import java.awt.*;

public class NumberOfMovementsFrame {

    private int i = -1;

    public NumberOfMovementsFrame(){

        JFrame frame = new JFrame();
        JDialog dialog = new JDialog(frame, "", true);
        dialog.setLayout(new BorderLayout());

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
                dialog.dispose();
        });

        dialog.add(label, BorderLayout.NORTH);
        dialog.add(buttonPanel,BorderLayout.CENTER);
        dialog.add(button, BorderLayout.SOUTH);

        dialog.setSize(300, 200);
        dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        dialog.setVisible(true);
    }

    public int getNumber(){
        while(i == -1) {}
        return i;
    }
}
