package it.polimi.se2018.view.gui;

import it.polimi.se2018.model.events.ModelChangedMessage;
import it.polimi.se2018.model.events.PlayerMessage;

import javax.swing.*;
import java.awt.*;

public class EndGameFrame extends SwingPhase {
    private JFrame jFrame = new JFrame();

    public EndGameFrame() {
        jFrame.setTitle("SAGRADA");
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Image image = java.awt.Toolkit.getDefaultToolkit().getImage("../ing-sw-2018-montanari-mosciatti-murace\\src\\images\\Sagrada.jpg");
        image = image.getScaledInstance(500, 670, Image.SCALE_SMOOTH);
        it.polimi.se2018.view.gui.Panel p = new it.polimi.se2018.view.gui.Panel(image);
        jFrame.setContentPane(p);
        jFrame.setLayout(new GridBagLayout());

        JPanel title = new JPanel();
        title.add(new JLabel("CLASSIFICA FINALE", SwingConstants.CENTER));

        JPanel classifica = new JPanel();
        classifica.setLayout(new GridLayout(4, 3));
        classifica.setPreferredSize(new Dimension(200, 200));

        for (int i=0; i<4; i++) {
            JLabel label = new JLabel(Integer.toString(i), SwingConstants.CENTER);
            JLabel player = new JLabel("PLAYER: " + Integer.toString(i));

            classifica.add(label);
            classifica.add(player);
        }

        JPanel thanks = new JPanel();
        thanks.add(new JLabel("Thanks for playing!!", SwingConstants.CENTER));

        GridBagConstraints constraints = new GridBagConstraints();

        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets.top = 50;
        constraints.anchor = GridBagConstraints.NORTH;
        jFrame.add(title, constraints);

        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets.top = 200;
        constraints.anchor = GridBagConstraints.CENTER;
        jFrame.add(classifica, constraints);

        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.insets.bottom = 100;
        constraints.anchor = GridBagConstraints.CENTER;
        jFrame.add(thanks, constraints);

        jFrame.setSize(500, 700);
        jFrame.setResizable(false);
        jFrame.setVisible(true);
    }

    public static void main(String args[]) {
        new EndGameFrame();
    }

    @Override
    public void update(ModelChangedMessage modelChangedMessage) {

    }

    @Override
    public void print() {

    }

    @Override
    public Integer askForPatternCard() {
        return null;
    }

    @Override
    public PlayerMessage getMainMove() {
        return null;
    }
}
