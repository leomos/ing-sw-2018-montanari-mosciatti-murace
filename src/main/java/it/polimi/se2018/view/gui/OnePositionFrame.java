package it.polimi.se2018.view.gui;

import it.polimi.se2018.model.events.ModelChangedMessageDiceOnPatternCard;
import it.polimi.se2018.model.events.ModelChangedMessagePatternCard;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class OnePositionFrame {
    private JDialog dialog;

    private int row = -1;

    private int col = -1;

    public OnePositionFrame(ModelChangedMessageDiceOnPatternCard messageDiceOnPatternCard, ModelChangedMessagePatternCard messagePatternCard, ArrayList<Integer> listOfAvailablePositions) {
        SwingPatternCard patternCard = new SwingPatternCard(messagePatternCard, false);
        SwingDiceOnPatternCard diceOnPatternCard = new SwingDiceOnPatternCard(messageDiceOnPatternCard, messagePatternCard, patternCard.getPatternCard(), false);

        JFrame frame = new JFrame();
        dialog = new JDialog(frame, "", true);

        JLabel label = new JLabel("Select position from your patternCard", SwingConstants.CENTER);

        String s = "(COLUMN; ROW) -> ";
        JLabel label1 = new JLabel(s, SwingConstants.CENTER);

        for (int i=0; i<20; i++) {
            int finalN = i;
            diceOnPatternCard.getPc().get(i).addActionListener(actionListener -> {
                    row = finalN % 5;
                    col = finalN / 5;
                    label1.setText(s + col + " " + row);
            });
        }

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(label1, BorderLayout.NORTH);
        panel.add(diceOnPatternCard, BorderLayout.CENTER);

        JButton button = new JButton("CONFIRM");
        button.addActionListener(actionListener -> {
                if (row!=-1 && col!=-1 && checkPositionsAreInArrayList(col, row, listOfAvailablePositions))
                    dialog.dispose();
                else
                    ;  //todo: generates message error bloccante
        });

        dialog.setLayout(new BorderLayout());
        dialog.setSize(new Dimension(270, 350));

        dialog.add(label, BorderLayout.NORTH);
        dialog.add(panel, BorderLayout.CENTER);
        dialog.add(button, BorderLayout.SOUTH);
        dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        dialog.setVisible(true);
    }

    private boolean checkPositionsAreInArrayList(int x, int y, ArrayList<Integer> list){
        if(!list.isEmpty()) {
            for (int i = 0; i < list.size(); i += 2)
                if (x == list.get(i) && y == list.get(i + 1))
                    return true;
        }
        else
            return true;
        return false;
    }

    public ArrayList<Integer> getValues() {
        ArrayList<Integer> v = new ArrayList<>();
        v.add(row);
        v.add(col);
        return v;
    }
}
