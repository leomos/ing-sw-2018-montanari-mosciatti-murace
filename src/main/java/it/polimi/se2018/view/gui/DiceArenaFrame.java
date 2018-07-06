package it.polimi.se2018.view.gui;

import it.polimi.se2018.model.events.ModelChangedMessageDiceArena;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class DiceArenaFrame extends ToolCardFrame {

    private int id = -1;

    private boolean confirm = false;

    /**
     * This constructor creates a ToolCardFrame when the player uses ToolCard 5, 6, 8, 9, 10 or 11. It shows a JFrame
     * with the representation of Dice Arena. The palyer has to choose one die and confirm his move
     * @param message that represents Dice Arena
     */
    public DiceArenaFrame(ModelChangedMessageDiceArena message) {
        SwingDiceArena diceArena = new SwingDiceArena(message);

        JFrame frame = new JFrame();
        new JDialog(frame, "", true);
        setLayout(new BorderLayout());

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
                if (id!=-1) {
                    confirm = true;
                    dispose();
                }
        });

        add(label, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
        add(button, BorderLayout.SOUTH);

        setSize(500, 300);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setVisible(true);

        Dimension screenSize = Toolkit.getDefaultToolkit ().getScreenSize();
        setLocation ((screenSize.width - 500) / 2, (screenSize.height - 300) / 2);
    }

    @Override
    public ArrayList<Integer> getValues() {
        return null;
    }

    @Override
    public int getValue() {
        while(id==-1 || !confirm) {
            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return id;
    }

    @Override
    public void close() {
        setModal(false);
        dispose();
    }
}
