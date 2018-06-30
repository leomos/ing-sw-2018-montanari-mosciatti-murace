package it.polimi.se2018.view.gui;

import it.polimi.se2018.model.events.ModelChangedMessageDiceArena;
import it.polimi.se2018.model.events.ModelChangedMessageRound;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SwingRoundTrack extends JPanel {
    public SwingRoundTrack(ArrayList<ModelChangedMessageRound> roundtrack) {
        setLayout(new GridLayout(1, 10, 5, 0));
        setPreferredSize(new Dimension(600, 60));
        setBackground(Color.ORANGE);

        Integer m = roundtrack.size();

        for (Integer i = 1; i < 11; i++) {
            JButton b = new JButton();
            b.setText(i.toString());
            b.setToolTipText("RoundTrack");
            if (i - 1 < m) {
                b.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JFrame jFrame = new JFrame("ROUND " + b.getText());
                        ModelChangedMessageDiceArena messageDiceArena = new ModelChangedMessageDiceArena(roundtrack.get(Integer.parseInt(b.getText())-1).getRepresentation());
                        SwingDiceArena round = new SwingDiceArena(messageDiceArena);
                        jFrame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
                        jFrame.add(round);
                        jFrame.pack();
                        jFrame.setVisible(true);
                    }
                });
            } else
                b.setEnabled(false);
            add(b);
        }
    }
}
