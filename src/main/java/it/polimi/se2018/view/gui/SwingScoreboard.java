package it.polimi.se2018.view.gui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SwingScoreboard extends JPanel {

    private ArrayList<ArrayList<Integer>> values1;

    /**
     * This constructor creates a JPanel which contains the final ranking
     * @param scoreboard is a string that represents the ranking of the game
     */
    public SwingScoreboard(String scoreboard) {
        setBackground(new Color(0, 0, 0, 0));

        String id;
        String points;

        String[] s = scoreboard.split("/");
        ArrayList<ArrayList<Integer>> values = new ArrayList<>();

        for (int i=0; i<s.length-1; i++) {
            String[] a = s[i].split(";");
            id = "" + a[0];
            points = "" + a[1];

            ArrayList<Integer> player = new ArrayList<>();
            player.add(Integer.parseInt(id));
            player.add(Integer.parseInt(points));
            values.add(player);
        }

        values1 = new ArrayList<>();

        for (int i=values.size()-1; i>=0; i--) {
            int min = 0;
            int cont = 0;
            for (int n = values.size() - 1; n >=0; n--) {
                if (values.get(n).get(1) > cont) {
                    min = n;
                    cont = values.get(n).get(1);
                }
            }
            values1.add(values.get(min));
            values.remove(min);
        }

        setLayout(new GridLayout(values1.size(), 1, 0, 20));

        for (int m=0; m<values1.size(); m++) {
            JPanel p = new JPanel();
            p.setLayout(new GridLayout(1, 3));
            p.setBackground(new Color(210, 210, 210, 200));

            JLabel l1 = new JLabel(Integer.toString(m+1) + "°", SwingConstants.CENTER);
            l1.setFont(new Font("Lucida Handwriting", Font.PLAIN, 18));
            JLabel l2 = new JLabel("PLAYER " + Integer.toString(values1.get(m).get(0)), SwingConstants.CENTER);
            l2.setFont(new Font("Lucida Handwriting", Font.PLAIN, 18));
            JLabel l3 = new JLabel(Integer.toString(values1.get(m).get(1)), SwingConstants.CENTER);
            l3.setFont(new Font("Lucida Handwriting", Font.PLAIN, 18));

            p.add(l1);
            p.add(l2);
            p.add(l3);
            add(p);
        }
    }

    public Integer getWinner() {
        return values1.get(0).get(0);
    }
}