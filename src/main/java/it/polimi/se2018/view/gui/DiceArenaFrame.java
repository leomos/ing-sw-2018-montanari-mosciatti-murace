package it.polimi.se2018.view.gui;

import it.polimi.se2018.model.events.ModelChangedMessageDiceArena;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DiceArenaFrame {
    private JDialog dialog;

    private int id = -1;

    public DiceArenaFrame(ModelChangedMessageDiceArena message) {
        SwingDiceArena diceArena = new SwingDiceArena(message);

        JFrame frame = new JFrame();
        dialog = new JDialog(frame, "", true);
        dialog.setLayout(new BorderLayout());

        JLabel label = new JLabel("Choose one die from DiceArena", SwingConstants.CENTER);

        String s = "Die chosen: ";
        JLabel die = new JLabel(s, SwingConstants.CENTER);

        JPanel p = new JPanel();
        p.setLayout(new FlowLayout());
        for (int i=0; i<diceArena.getButtons().size(); i++) {
            int finalI = i;
            diceArena.getButtons().get(i).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    id = finalI;
                    die.setText(s + id);
                }
            });
            p.add(diceArena.getButtons().get(i));
        }

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(p, BorderLayout.CENTER);
        panel.add(die, BorderLayout.SOUTH);

        JButton button = new JButton("CONTINUE");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (id!=-1)
                    dialog.dispose();
            }
        });

        dialog.add(label, BorderLayout.NORTH);
        dialog.add(panel, BorderLayout.CENTER);
        dialog.add(button, BorderLayout.SOUTH);

        dialog.setSize(500, 300);
        dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        dialog.setVisible(true);
    }

    public Integer getid() {
        while(id==-1);
        return id;
    }
}
