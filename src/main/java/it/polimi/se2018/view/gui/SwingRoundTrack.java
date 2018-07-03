package it.polimi.se2018.view.gui;

import it.polimi.se2018.model.events.ModelChangedMessageDiceArena;
import it.polimi.se2018.model.events.ModelChangedMessageRound;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SwingRoundTrack extends JPanel {
    private ArrayList<SwingDiceArena> arena = new ArrayList<>();

    public SwingRoundTrack(ArrayList<ModelChangedMessageRound> roundtrack) {
        setLayout(new FlowLayout());
        setPreferredSize(new Dimension(500, 60));
        setBackground(new Color(210, 210, 210, 200));

        Integer m = roundtrack.size();

        for (Integer i = 1; i < 11; i++) {
            JButton b = new JButton();
            b.setPreferredSize(new Dimension(50, 50));
            b.setText(i.toString());
            b.setToolTipText("RoundTrack");
            if (i - 1 < m) {
                b.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JFrame jFrame = new JFrame("ROUND " + b.getText());
                        ModelChangedMessageDiceArena messageDiceArena = new ModelChangedMessageDiceArena(roundtrack.get(Integer.parseInt(b.getText())-1).getRepresentation());
                        SwingDiceArena round = new SwingDiceArena(messageDiceArena);
                        arena.add(round);
                        for (int i=0; i<round.getButtons().size(); i++)
                            round.getButtons().get(i).setToolTipText("ROUND " + b.getText());
                        jFrame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
                        jFrame.add(round);
                        jFrame.setResizable(false);
                        jFrame.pack();
                        jFrame.setVisible(true);
                    }
                });
            } else
                b.setEnabled(false);
            add(b);
        }
    }

    public ArrayList<SwingDiceArena> getArena() {
        return arena;
    }
}
