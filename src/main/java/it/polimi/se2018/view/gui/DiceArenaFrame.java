package it.polimi.se2018.view.gui;

import it.polimi.se2018.model.events.ModelChangedMessageDiceArena;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DiceArenaFrame extends ToolCardFrame {
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
            diceArena.getButtons().get(i).addActionListener(actionListener -> {
                    id = finalI;
                    die.setText(s + id);
            });
            p.add(diceArena.getButtons().get(i));
        }

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(p, BorderLayout.CENTER);
        panel.add(die, BorderLayout.SOUTH);

        JButton button = new JButton("CONTINUE");
        button.addActionListener(actionListener -> {
                if (id!=-1)
                    dialog.dispose();
        });

        dialog.add(label, BorderLayout.NORTH);
        dialog.add(panel, BorderLayout.CENTER);
        dialog.add(button, BorderLayout.SOUTH);

        dialog.setSize(500, 300);
        dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        dialog.setVisible(true);

        Dimension screenSize = Toolkit.getDefaultToolkit ().getScreenSize();
        dialog.setLocation ((screenSize.width - 500) / 2, (screenSize.height - 300) / 2);
    }

    @Override
    public ArrayList<Integer> getValues() {
        return null;
    }

    @Override
    public int getValue() {
        while(id==-1);
        return id;
    }
}
