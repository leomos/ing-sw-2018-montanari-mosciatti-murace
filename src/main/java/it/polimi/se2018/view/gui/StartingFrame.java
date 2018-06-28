package it.polimi.se2018.view.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class StartingFrame extends JFrame {
    public StartingFrame() throws IOException {
        setTitle("SAGRADA");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JLabel welcome = new JLabel("BENVENUTO!! INSERISCI IL TUO NOME DI GIOCO");
        JTextField name = new JTextField("",20);
        name.setEditable(true);
        JPanel p1 = new JPanel();
        p1.setLayout(new GridLayout(2, 1));
        p1.add(welcome);
        p1.add(name);

        JLabel label = new JLabel("INSERISCI LE TUE PREFERENZE DI RETE E DI MODALITA' DI GIOCO");
        String[] n = {"RMI", "Socket"};
        String[] m = {"Console", "GUI"};
        JComboBox network = new JComboBox(n);
        JComboBox mode = new JComboBox(m);
        JPanel p2 = new JPanel();
        p2.setLayout(new GridLayout(3, 1));
        p2.add(label);
        p2.add(network);
        p2.add(mode);

        JButton ok = new JButton("OK");

        ImageIcon i = new ImageIcon(ImageIO.read(new File("../PROGETTO\\src\\images\\Sagrada.jpg")));
        Image i1 = i.getImage().getScaledInstance(500, 670, Image.SCALE_SMOOTH);
        Panel p = new Panel(i1);
        setContentPane(p);

        GridBagConstraints constraints = new GridBagConstraints();
        setLayout(new GridBagLayout());

        constraints.insets = new Insets(40, 20, 10, 5);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.CENTER;
        add(p1, constraints);

        constraints.insets = new Insets(40, 20, 10, 5);
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.CENTER;
        add(p2, constraints);

        constraints.insets = new Insets(40, 20, 10, 5);
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        add(ok, constraints);

        setSize(500, 700);
        setResizable(false);
        setVisible(true);
    }

    public static void main(String args[]) throws IOException {
        new StartingFrame();
    }

}
