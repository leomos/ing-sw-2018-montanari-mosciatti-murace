package it.polimi.se2018.view.gui;

import it.polimi.se2018.model.events.ModelChangedMessagePatternCard;

import javax.swing.*;
import java.awt.*;

public class SwingPatternCard extends JPanel {

    public SwingPatternCard(ModelChangedMessagePatternCard modelChangedMessagePatternCard, boolean enable) {
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(4, 5));
        p.setPreferredSize(new Dimension(250, 200));

        JPanel p1 = new JPanel();
        p1.setLayout(new GridLayout(3, 1));
        JLabel l1 = new JLabel("PLAYER: " + modelChangedMessagePatternCard.getIdPlayer(), SwingConstants.CENTER);
        JLabel l2 = new JLabel("PATTERNCARD: " + modelChangedMessagePatternCard.getName(), SwingConstants.CENTER);
        JLabel l3 = new JLabel("DIFFICULTY: " + modelChangedMessagePatternCard.getDifficulty(), SwingConstants.CENTER);
        p1.add(l1);
        p1.add(l2);
        p1.add(l3);

        String rep = modelChangedMessagePatternCard.getRepresentation();
        for (int i=0; i<20; i++) {
            char c = rep.charAt(i);
            SwingDie d = new SwingDie(0);
            if (enable)
                d.setEnabled(false);

            switch (c) {
                case '1':
                    d.setVal(1);
                    d.setBackground(Color.gray);
                    p.add(d);
                    break;
                case '2':
                    d.setVal(2);
                    d.setBackground(Color.gray);
                    p.add(d);
                    break;
                case '3':
                    d.setVal(3);
                    d.setBackground(Color.gray);
                    p.add(d);
                    break;
                case '4':
                    d.setVal(4);
                    d.setBackground(Color.gray);
                    p.add(d);
                    break;
                case '5':
                    d.setVal(5);
                    d.setBackground(Color.gray);
                    p.add(d);
                    break;
                case '6':
                    d.setVal(6);
                    d.setBackground(Color.gray);
                    p.add(d);
                    break;

                case 'r':
                    d.setBackground(Color.RED);
                    p.add(d);
                    break;
                case 'b':
                    d.setBackground(Color.BLUE);
                    p.add(d);
                    break;
                case 'y':
                    d.setBackground(Color.YELLOW);
                    p.add(d);
                    break;
                case 'p':
                    d.setBackground(new Color(143, 0, 255));
                    p.add(d);
                    break;
                case 'g':
                    d.setBackground(Color.GREEN);
                    p.add(d);
                    break;

                case '0':
                    d.setBackground(Color.WHITE);
                    p.add(d);
                    break;
            }
        }
        setLayout(new BorderLayout());
        add(p, BorderLayout.CENTER);
        add(p1, BorderLayout.SOUTH);
    }

    public static void main(String args[]) {
        ModelChangedMessagePatternCard message = new ModelChangedMessagePatternCard("1", "2", "Aurorae Magnificus", "5", "b600y03b0p056200401g");

        JFrame f = new JFrame();
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.add(new SwingPatternCard(message, false));
        f.setVisible(true);
        f.setSize(300,300);
    }
}
