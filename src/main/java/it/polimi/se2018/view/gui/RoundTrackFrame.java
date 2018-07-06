package it.polimi.se2018.view.gui;

import it.polimi.se2018.model.events.ModelChangedMessageDiceArena;
import it.polimi.se2018.model.events.ModelChangedMessageRound;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class RoundTrackFrame extends ToolCardFrame {

    private int round = -1;

    private int die = -1;

    private boolean confirm = false;

    public RoundTrackFrame(ArrayList<ModelChangedMessageRound> roundTrack) {
        ArrayList<SwingDiceArena> arena = new ArrayList<>();

        for (int i=0; i<roundTrack.size(); i++) {
            ModelChangedMessageDiceArena messageDiceArena = new ModelChangedMessageDiceArena(roundTrack.get(i).getRepresentation());
            SwingDiceArena round1 = new SwingDiceArena(messageDiceArena);
            arena.add(round1);
        }

        JFrame frame = new JFrame();
        new JDialog(frame, "", true);

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
            if (round!=-1 && die!=-1) {
                confirm = true;
                dispose();
            }
        });

        setLayout(new BorderLayout());
        setSize(new Dimension(350, 350));

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation ((screenSize.width-350)/2, (screenSize.height-350)/2);

        add(label, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
        add(button, BorderLayout.SOUTH);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public ArrayList<Integer> getValues() {
        ArrayList<Integer> v = new ArrayList<>();
        while (!confirm) {
            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        v.add(round);
        v.add(die);
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
