package it.polimi.se2018.view.gui;

import it.polimi.se2018.view.ViewClient;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class EndGameFrame extends ViewClient {
    private JFrame jFrame = new JFrame();

    public EndGameFrame() {
        jFrame.setTitle("SAGRADA");
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.getContentPane().setLayout(new BorderLayout());


        Image image = java.awt.Toolkit.getDefaultToolkit().getImage("../ing-sw-2018-montanari-mosciatti-murace\\src\\images\\Sagrada.jpg");
        image = image.getScaledInstance(500, 670, Image.SCALE_SMOOTH);
        it.polimi.se2018.view.gui.Panel p = new it.polimi.se2018.view.gui.Panel(image);
        jFrame.setContentPane(p);

        JLabel title = new JLabel("CLASSIFICA FINALE", SwingConstants.CENTER);
        title.setBackground(Color.WHITE);

        JPanel classifica = new JPanel();
        classifica.setLayout(new GridLayout(4, 3));

        for (int i=0; i<4; i++) {
            JLabel label = new JLabel(Integer.toString(i), SwingConstants.CENTER);
            JLabel player = new JLabel("PLAYER: " + Integer.toString(i));

            classifica.add(label);
            classifica.add(player);
        }

        JLabel thanks = new JLabel("Thanks for playing!!", SwingConstants.CENTER);
        thanks.setBackground(Color.WHITE);

        jFrame.getContentPane().add(title, BorderLayout.NORTH);
        jFrame.getContentPane().add(classifica, BorderLayout.CENTER);
        jFrame.getContentPane().add(thanks, BorderLayout.SOUTH);

        jFrame.setSize(500, 700);
        jFrame.setResizable(false);
        jFrame.setVisible(true);
    }

    @Override
    public void setIdClient(int idClient) {
    }

    public static void main(String args[]) {
        new EndGameFrame();
    }
}
