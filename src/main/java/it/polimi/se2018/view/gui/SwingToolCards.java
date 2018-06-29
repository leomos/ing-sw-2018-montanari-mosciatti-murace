package it.polimi.se2018.view.gui;

import it.polimi.se2018.model.events.ModelChangedMessageToolCard;

import javax.swing.*;
import java.awt.*;

public class SwingToolCards extends JPanel {
    public SwingToolCards(ModelChangedMessageToolCard message) {
        String path = "C:\\Users\\Alessandro Murace\\Pictures\\TOOLCARDS\\Toolcard";
        path = path + message.getIdToolCard() + ".jpg";

        JButton b = new JButton();
        b.setIcon(new ImageIcon(path));
        b.setPreferredSize(new Dimension(185, 255));
        b.setToolTipText("Toolcards");

        JLabel l = new JLabel("COSTO: " + message.getCost(), SwingConstants.CENTER);

        setLayout(new BorderLayout());
        add(b, BorderLayout.CENTER);
        add(l, BorderLayout.SOUTH);
    }
}
