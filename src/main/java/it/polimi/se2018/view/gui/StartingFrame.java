package it.polimi.se2018.view.gui;

import it.polimi.se2018.view.ViewClient;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class StartingFrame extends ViewClient implements ActionListener {
    private JFrame jFrame = new JFrame();
    private JComboBox network;
    private JTextField name;
    private ArrayList<String> choices;

    public StartingFrame() throws IOException {
        this.jFrame.setTitle("SAGRADA");
        this.jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JLabel welcome = new JLabel("BENVENUTO!! INSERISCI IL TUO NOME DI GIOCO");
        name = new JTextField("",20);
        name.setEditable(true);
        JPanel p1 = new JPanel();
        p1.setLayout(new GridLayout(2, 1));
        p1.add(welcome);
        p1.add(name);

        JLabel label = new JLabel("INSERISCI LE TUE PREFERENZE DI RETE");
        String[] n = {"RMI", "Socket"};
        network = new JComboBox(n);
        JPanel p2 = new JPanel();
        p2.setLayout(new GridLayout(3, 1));
        p2.add(label);
        p2.add(network);

        JButton ok = new JButton("OK");
        ok.addActionListener(this);

        ImageIcon i = new ImageIcon(ImageIO.read(new File("../PROGETTO\\src\\images\\Sagrada.jpg")));
        Image i1 = i.getImage().getScaledInstance(500, 670, Image.SCALE_SMOOTH);
        Panel p = new Panel(i1);
        this.jFrame.setContentPane(p);

        GridBagConstraints constraints = new GridBagConstraints();
        this.jFrame.setLayout(new GridBagLayout());

        constraints.insets = new Insets(40, 20, 10, 5);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.CENTER;
        this.jFrame.add(p1, constraints);

        constraints.insets = new Insets(40, 20, 10, 5);
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.CENTER;
        this.jFrame.add(p2, constraints);

        constraints.insets = new Insets(40, 20, 10, 5);
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        this.jFrame.add(ok, constraints);

        this.jFrame.setSize(500, 700);
        this.jFrame.setResizable(false);
        this.jFrame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String n = this.name.getText();
        String net = (String) this.network.getSelectedItem();
        this.choices.add(n);
        this.choices.add(net);
    }

    public ArrayList<String> getChoices() {
        return this.choices;
    }


    @Override
    public void setIdClient(int idClient) {

    }
}
