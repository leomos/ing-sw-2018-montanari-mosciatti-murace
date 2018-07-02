package it.polimi.se2018.view.gui;

import javax.swing.*;
import java.awt.*;

public class ConfirmationFrame extends JFrame {
    private int isok;

    public ConfirmationFrame(String idDie, int x, int y, String toolCard) {
        isok = 0;
        setLayout(new BorderLayout());
        setResizable(false);

        JLabel label = new JLabel("Do you confirn your move?", SwingConstants.CENTER);
        String move = "MOVE: ";
        String t = "TOOLCARD: ";
        if (!idDie.equals(""))
            move = move + "DIE " + idDie + " IN DICEARENA:" + " ROW " + y + "; COLUMN " + x;
        if (!toolCard.equals(""))
            t = t + toolCard;

        JLabel mossa = new JLabel(move, SwingConstants.CENTER);
        JLabel toolcard = new JLabel(t, SwingConstants.CENTER);

        JPanel choices = new JPanel();
        choices.setLayout(new GridLayout(2, 1));
        choices.add(mossa);
        choices.add(toolcard);

        JPanel pannelloPulsanti = new JPanel();
        pannelloPulsanti.setLayout(new FlowLayout());

        JButton esci = new JButton("Sì");
        esci.addActionListener(actionListener -> {
                isok = 1;
                dispose();
        });
        pannelloPulsanti.add(esci);

        JButton annulla = new JButton("No");
        annulla.addActionListener(actionListener -> {
                isok = 2;
                dispose();

        });
        pannelloPulsanti.add(annulla);

        add(label, BorderLayout.NORTH);
        add(choices, BorderLayout.CENTER);
        add(pannelloPulsanti, BorderLayout.SOUTH);

        pack();
        setVisible(true);
    }

    public int isOk() {
        return isok;
    }
}
