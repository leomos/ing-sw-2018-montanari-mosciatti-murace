package it.polimi.se2018.view.gui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SwingDie extends JButton {
    private int val;

    public SwingDie(int val) {
        this.val = val;
        setPreferredSize(new Dimension(50, 50));
    }

    public void setVal(int val) {
        this.val = val;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Color c = new Color(200,0,0,5);
        g.setColor(c);
        g.drawRect(0, 0, 50, 50);
        g.setColor(Color.black);

        if (val > 1) {
            // punto in alto a sinistra
            g.fillOval(10, 10, 9, 9);
            // punto in basso a destra
            g.fillOval(30, 30, 9, 9);
        }
        if (val > 3) {
            // punto in alto a destra
            g.fillOval(30, 10, 9, 9);
            // punto in basso a sinistra
            g.fillOval(10, 30, 9,9);
        }
        if (val == 6) {
            // punto nel mezzo a sinistra
            g.fillOval(10, 20, 9, 9);
            // punto nel mezzo a destra
            g.fillOval(30, 20, 9, 9);
        }
        if (val % 2 == 1)
            // punto al centro
            g.fillOval(20, 20, 9, 9);
    }

    public ArrayList<SwingDie> setOfDice(String representation) {
        ArrayList<SwingDie> dice = new ArrayList<>();
        char c;
        char n;
        int l = representation.length();

        for (int i=0; i<l;) {
            c = representation.charAt(i);
            n = representation.charAt(i+1);
            SwingDie d = new SwingDie(0);
            d.setToolTipText("Dice Arena");

            switch (n) {
                case '1':
                    d.setVal(1);
                    break;
                case '2':
                    d.setVal(2);
                    break;
                case '3':
                    d.setVal(3);
                    break;
                case '4':
                    d.setVal(4);
                    break;
                case '5':
                    d.setVal(5);
                    break;
                case '6':
                    d.setVal(6);
                    break;
                default:
                    break;
            }

            switch (c) {
                case 'b':
                    d.setBackground(Color.BLUE);
                    break;
                case 'y':
                    d.setBackground(Color.YELLOW);
                    break;
                case 'g':
                    d.setBackground(Color.GREEN);
                    break;
                case 'r':
                    d.setBackground(Color.RED);
                    break;
                case 'p':
                    d.setBackground(new Color(143, 0, 255));
                    break;
                default:
                    break;
            }
            dice.add(d);
            i = i + 2;
        }
        return dice;
    }
}