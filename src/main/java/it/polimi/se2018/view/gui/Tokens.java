package it.polimi.se2018.view.gui;

import javax.swing.*;
import java.awt.*;

public class Tokens extends JLabel {
    private String num;

    public Tokens(String n) {
        setPreferredSize(new Dimension(100, 30));
        this.num = n;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Color c = new Color(240, 248, 255);
        g.setColor(c);

        for (int i = 0; i< Integer.parseInt(num); i++) {
            g.fillOval(200+(i*25), 0, 15, 15);
        }
    }
}
