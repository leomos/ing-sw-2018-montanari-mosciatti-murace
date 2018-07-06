package it.polimi.se2018.view.gui;

import it.polimi.se2018.model.events.ModelChangedMessageToolCard;
import it.polimi.se2018.utils.ImageLoader;

import javax.swing.*;
import java.awt.*;

public class SwingToolCards extends JPanel {

    private JButton b;

    /**
     * This constructor creates a button which contains the ToolCard sent by message and its cost
     * @param message represents one ToolCard
     */
    public SwingToolCards(ModelChangedMessageToolCard message) {
        ImageLoader imageLoader = new ImageLoader();
        ImageIcon icon = new ImageIcon(imageLoader.getToolCard(message.getIdToolCard()));

        b = new JButton();
        b.setIcon(icon);
        b.setPreferredSize(new Dimension(185, 255));
        b.setToolTipText("Toolcards");

        JLabel l = new JLabel("COST: " + message.getCost(), SwingConstants.CENTER);

        setLayout(new BorderLayout());
        add(b, BorderLayout.CENTER);
        add(l, BorderLayout.SOUTH);
    }

    public JButton getCard() {
        return b;
    }
}
