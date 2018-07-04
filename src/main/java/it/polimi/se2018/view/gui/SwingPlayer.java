package it.polimi.se2018.view.gui;

import javax.swing.*;
import java.awt.*;

public class SwingPlayer extends JPanel {
    public SwingPlayer(SwingDiceOnPatternCard patternCard, SwingPrivateObjective privateObjective, int tokens) {
        Color c = new Color(34, 139, 34);
        setBackground(c);

        JPanel panel = new JPanel();
        panel.setBackground(c);
        GridBagConstraints constraints = new GridBagConstraints();
        panel.setLayout(new GridBagLayout());
        panel.add(patternCard);
        constraints.insets = new Insets(20, 20, 20, 20);
        constraints.gridx = 1;
        constraints.gridy = 0;
        panel.add(privateObjective, constraints);

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridLayout(1, 2, 50, 0));
        jPanel.setBackground(c);
        JButton button = new JButton("PATTERNCARD");
        SwingPatternCard swingPatternCard = new SwingPatternCard(patternCard.getS(), true);
        button.addActionListener(actionListener -> {
            JFrame f = new JFrame("YOUR PATTERNCARD");
            f.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
            f.add(swingPatternCard);
            f.pack();
            f.setVisible(true);
            Dimension screenSize = Toolkit.getDefaultToolkit ().getScreenSize();
            Dimension frameSize = f.getSize();
            f.setLocation ((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
        });
        Tokens t = new Tokens(tokens);
        t.setToolTipText("Tokens left");
        jPanel.add(button);
        jPanel.add(t);

        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
        add(jPanel, BorderLayout.SOUTH);
    }
}
