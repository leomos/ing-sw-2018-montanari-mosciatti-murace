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

        Tokens t = new Tokens(tokens);
        t.setToolTipText("Tokens left");

        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
        add(t, BorderLayout.SOUTH);
    }
}
