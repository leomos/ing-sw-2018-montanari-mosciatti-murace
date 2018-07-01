package it.polimi.se2018.view.gui;

import it.polimi.se2018.model.events.PlayerMessage;
import it.polimi.se2018.model.events.PlayerMessageDie;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConfirmationFrame extends JFrame {
    public ConfirmationFrame(int idClient, String idDie, int x, int y, String toolCard) {
        setLayout(new BorderLayout());
        setSize(300, 100);
        setVisible(true);

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
        choices.setLayout(new FlowLayout());
        choices.add(mossa);
        choices.add(toolcard);

        JPanel pannelloPulsanti = new JPanel();
        pannelloPulsanti.setLayout(new FlowLayout());

        JButton esci = new JButton("Sì");
        esci.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PlayerMessage message = new PlayerMessageDie(idClient, Integer.parseInt(idDie), x, y);
                setVisible(false);
            }
        });
        pannelloPulsanti.add(esci);

        JButton annulla = new JButton("No");
        annulla.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        pannelloPulsanti.add(annulla);

        add(label, BorderLayout.NORTH);
        add(choices, BorderLayout.CENTER);
        add(pannelloPulsanti, BorderLayout.SOUTH);
    }
}
