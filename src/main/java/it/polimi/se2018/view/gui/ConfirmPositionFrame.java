package it.polimi.se2018.view.gui;

import it.polimi.se2018.model.events.ModelChangedMessageDiceOnPatternCard;
import it.polimi.se2018.model.events.ModelChangedMessagePatternCard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class ConfirmPositionFrame extends ToolCardFrame {

    private int row1 = -1;

    private int col1 = -1;

    private int row2 = -1;

    private int col2 = -1;

    private ArrayList<Integer> v = new ArrayList<>();

    /**
     * This constructor creates a ToolCardFrame when the player uses ToolCard 2 or 3. It shows a JFrame with the
     * representation of PatternCard with the dice. The palyer has to choose 2 positions and confirm his move
     * @param messageDiceOnPatternCard contains the representation of PatternCard during the game
     * @param messagePatternCard contains the representation of PatternCard without dice
     */
    public ConfirmPositionFrame(ModelChangedMessageDiceOnPatternCard messageDiceOnPatternCard, ModelChangedMessagePatternCard messagePatternCard) {
        SwingPatternCard patternCard = new SwingPatternCard(messagePatternCard, false);
        SwingDiceOnPatternCard diceOnPatternCard = new SwingDiceOnPatternCard(messageDiceOnPatternCard, messagePatternCard, patternCard.getPatternCard(), false);

        JLabel label = new JLabel("Select two positions");

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        String s = "INITIAL POSITION (COLUMN - ROW): ";
        String t = "FINAL POSITION (COLUMN - ROW): ";
        JLabel initialPosition = new JLabel(s, SwingConstants.CENTER);
        JLabel finalPosition = new JLabel(t, SwingConstants.CENTER);

        panel.setPreferredSize(new Dimension(300, 250));
        panel.add(diceOnPatternCard);
        panel.add(initialPosition);
        panel.add(finalPosition);

        for (int i=0; i<20; i++) {
            int finalN = i;
            diceOnPatternCard.getPc().get(i).addActionListener(actionListener -> {
                if (row1==-1 && col1==-1) {
                    row1 = finalN % 5;
                    col1 = finalN / 5;
                    initialPosition.setText(s + col1 + " - " + row1);
                }
                else if (row2==-1 && col2==-1) {
                    row2 = finalN % 5;
                    col2 = finalN / 5;
                    finalPosition.setText(t + col2 + " - " + row2);
                }
            });
        }

        JButton b = new JButton("RESET CHOICES");
        b.addActionListener(actionListener -> {
            initialPosition.setText(s);
            finalPosition.setText(t);
            col1 = -1;
            row1 = -1;
            col2 = -1;
            row2 = -1;
        });

        JFrame frame = new JFrame();
        new JDialog(frame, "", true);
        setLayout(new BorderLayout());

        JButton button = new JButton("CONTINUE");
        button.addActionListener(actionListener -> {
                if (row1 != -1 && col1 != -1 && row2 != -1 && col2 != -1) {
                    v.add(row1);
                    v.add(col1);
                    v.add(row2);
                    v.add(col2);
                    dispose();
                } else {
                    new MoveFailedFrame("Select two dice");
                }
        });

        JPanel buttons = new JPanel();
        buttons.setLayout(new FlowLayout());
        buttons.add(b);
        buttons.add(button);

        add(label, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
        setSize(new Dimension(300, 400));

        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setVisible(true);

        Dimension screenSize = Toolkit.getDefaultToolkit ().getScreenSize();
        setLocation ((screenSize.width - 250) / 2, (screenSize.height - 350) / 2);
    }

    @Override
    public ArrayList<Integer> getValues() {
        while(v.isEmpty()) {
            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
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
