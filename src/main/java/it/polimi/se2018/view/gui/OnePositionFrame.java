package it.polimi.se2018.view.gui;

import it.polimi.se2018.model.events.ModelChangedMessageDiceOnPatternCard;
import it.polimi.se2018.model.events.ModelChangedMessagePatternCard;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class OnePositionFrame extends ToolCardFrame {

    private int row = -1;

    private int col = -1;

    private boolean confirm = false;

    public OnePositionFrame(ModelChangedMessageDiceOnPatternCard messageDiceOnPatternCard, ModelChangedMessagePatternCard messagePatternCard, ArrayList<Integer> listOfAvailablePositions) {
        SwingPatternCard patternCard = new SwingPatternCard(messagePatternCard, false);
        SwingDiceOnPatternCard diceOnPatternCard = new SwingDiceOnPatternCard(messageDiceOnPatternCard, messagePatternCard, patternCard.getPatternCard(), false);

        JFrame frame = new JFrame();
        new JDialog(frame, "", true);

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
                if (row!=-1 && col!=-1 && checkPositionsAreInArrayList(col, row, listOfAvailablePositions)) {
                    confirm = true;
                    dispose();
                }
                else
                    new MoveFailedFrame("Select an available position!");
        });

        setLayout(new BorderLayout());
        setSize(new Dimension(270, 350));

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation ((screenSize.width-270)/2, (screenSize.height-350)/2);

        add(label, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
        add(button, BorderLayout.SOUTH);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setVisible(true);
    }

    private boolean checkPositionsAreInArrayList(int x, int y, ArrayList<Integer> list){
        if(!list.isEmpty()) {
            for (int i = 0; i < list.size(); i += 2)
                if (y == list.get(i) && x == list.get(i + 1))
                    return true;
        }
        else
            return true;
        return false;
    }

    //@Override
    public ArrayList<Integer> getValues() {
        ArrayList<Integer> v = new ArrayList<>();
        while(!confirm) {
            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        v.add(row);
        v.add(col);
        return v;
    }

    @Override
    public int getValue() {
        return 0;
    }

    @Override
    public void close() {
        setModal(false);
        dispose();
    }
}
