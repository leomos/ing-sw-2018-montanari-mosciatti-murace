package it.polimi.se2018.view.gui;

import it.polimi.se2018.model.events.ModelChangedMessagePatternCard;
import it.polimi.se2018.model.patternCard.PatternCard;

import javax.swing.*;
import java.awt.*;

public class PatternCardsFrame extends JFrame {
    public PatternCardsFrame(SwingPatternCard p1, SwingPatternCard p2, SwingPatternCard p3, SwingPatternCard p4) {
        setTitle("SCELTA PATTERNCARD");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(550, 700);
        setLayout(new BorderLayout());
        JPanel p = new JPanel();
        Color c = new Color(34, 139, 34);
        p.setBackground(c);

        setContentPane(p);

        JLabel l = new JLabel("SCEGLI LA PATTERNCARD CON CUI GIOCARE", SwingConstants.CENTER);
        l.setForeground(Color.WHITE);

        JButton b = new JButton("CONFERMA");

        JRadioButton rb1 = new JRadioButton("PATTERNCARD 1");
        JRadioButton rb2 = new JRadioButton("PATTERNCARD 2");
        JRadioButton rb3 = new JRadioButton("PATTERNCARD 3");
        JRadioButton rb4 = new JRadioButton("PATTERNCARD 4");
        ButtonGroup group = new ButtonGroup();
        group.add(rb1);
        group.add(rb2);
        group.add(rb3);
        group.add(rb4);

        JPanel panel = new JPanel();
        GridBagConstraints constraints = new GridBagConstraints();
        panel.setLayout(new GridBagLayout());

        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        JPanel panel4 = new JPanel();
        panel1.setLayout(new BorderLayout());
        panel2.setLayout(new BorderLayout());
        panel3.setLayout(new BorderLayout());
        panel4.setLayout(new BorderLayout());

        panel1.add(p1, BorderLayout.CENTER);
        panel1.add(rb1, BorderLayout.SOUTH);

        panel2.add(p2, BorderLayout.CENTER);
        panel2.add(rb2, BorderLayout.SOUTH);

        panel3.add(p3, BorderLayout.CENTER);
        panel3.add(rb3, BorderLayout.SOUTH);

        panel4.add(p4, BorderLayout.CENTER);
        panel4.add(rb4, BorderLayout.SOUTH);

        constraints.insets = new Insets(5, 10, 5, 10);
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(panel1, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(panel2, constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        panel.add(panel3, constraints);

        constraints.gridx = 1;
        constraints.gridy = 1;
        panel.add(panel4, constraints);

        add(l, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
        add(b, BorderLayout.SOUTH);

        setVisible(true);
    }

    public static void main(String args[]) {
        ModelChangedMessagePatternCard message1 = new ModelChangedMessagePatternCard("1", "2", "Bellesguard", "3", "b600y03b00056200401g");
        ModelChangedMessagePatternCard message2 = new ModelChangedMessagePatternCard("1", "7", "Symphony of Light", "6", "20501y6p2r0b4g003050");
        ModelChangedMessagePatternCard message3 = new ModelChangedMessagePatternCard("1", "11", "Batllo", "5", "0060005b403gyp214r53");
        ModelChangedMessagePatternCard message4 = new ModelChangedMessagePatternCard("1", "21", "Comitas", "5", "y02060405y000y512y30");

        SwingPatternCard p1 = new SwingPatternCard(message1, true);
        SwingPatternCard p2 = new SwingPatternCard(message2, true);
        SwingPatternCard p3 = new SwingPatternCard(message3, true);
        SwingPatternCard p4 = new SwingPatternCard(message4, true);

        new PatternCardsFrame(p1, p2, p3, p4);
    }
}
