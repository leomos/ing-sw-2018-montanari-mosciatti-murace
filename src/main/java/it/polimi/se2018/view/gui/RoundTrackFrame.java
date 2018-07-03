package it.polimi.se2018.view.gui;

import it.polimi.se2018.model.events.ModelChangedMessageDiceArena;
import it.polimi.se2018.model.events.ModelChangedMessageRound;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class RoundTrackFrame {
    private JDialog dialog;

    private int round = -1;

    private int die = -1;

    public RoundTrackFrame(ArrayList<ModelChangedMessageRound> roundTrack) {
        ArrayList<SwingDiceArena> arena = new ArrayList<>();

        for (int i=0; i<roundTrack.size(); i++) {
            ModelChangedMessageDiceArena messageDiceArena = new ModelChangedMessageDiceArena(roundTrack.get(i).getRepresentation());
            SwingDiceArena round1 = new SwingDiceArena(messageDiceArena);
            arena.add(round1);
        }

        JFrame frame = new JFrame();
        dialog = new JDialog(frame, "", true);

        JLabel label = new JLabel("Select a die from RoundTrack", SwingConstants.CENTER);

        String s = "ROUND - DIE ";
        JLabel label1 = new JLabel(s, SwingConstants.CENTER);

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new FlowLayout());

        for (int i=0; i<arena.size(); i++) {
            jPanel.add(arena.get(i));
            for (int n=0; n<arena.get(i).getButtons().size(); n++) {
                int finalI = i;
                int finalN = n;
                arena.get(i).getButtons().get(n).addActionListener(actionListener -> {
                    round = finalI;
                    die = finalN;
                    label1.setText(s + round + "-" + die);
                });
            }
        }

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(label1, BorderLayout.NORTH);
        panel.add(jPanel, BorderLayout.CENTER);

        JButton button = new JButton("CONFIRM");
        button.addActionListener(actionListener -> {
            if (round!=-1 && die!=-1)
            dialog.dispose();
        });

        dialog.setLayout(new BorderLayout());
        dialog.setSize(new Dimension(350, 350));
        dialog.add(label, BorderLayout.NORTH);
        dialog.add(panel, BorderLayout.CENTER);
        dialog.add(button, BorderLayout.SOUTH);
        dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        dialog.setVisible(true);
    }

    public ArrayList<Integer> getValues() {
        ArrayList<Integer> v = new ArrayList<>();
        v.add(round);
        v.add(die);
        return v;
    }
}
