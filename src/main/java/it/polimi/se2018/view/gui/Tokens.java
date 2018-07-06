package it.polimi.se2018.view.gui;

import javax.swing.*;
import java.awt.*;

public class Tokens extends JLabel {
    private int num;

    public Tokens(int n) {
        setPreferredSize(new Dimension(100, 30));
        this.num = n;
    }

    /**
     * This method creates a set of white ovals which represent the tokens
     * @param g
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Color c = new Color(240, 248, 255);
        g.setColor(c);

        for (int i = 0; i< num; i++) {
            g.fillOval(50+(i*20), 0, 15, 15);
        }
    }
}
