package it.polimi.se2018.view.gui;

import javax.swing.*;
import java.awt.*;

public class PlayerReconnectedFrame extends Frame {

    /**
     * This constructor creates a Frame shown when a player reconnect to the game
     * @param clientId is the id of client reconnected
     */
    public PlayerReconnectedFrame(int clientId){super();
        dialog.setTitle("PLAYER RECONNECTED");
        label = new JLabel("Player " + clientId + " is back in the game!", SwingConstants.CENTER);
        dialog.add(label, BorderLayout.CENTER);
        dialog.setSize(500, 100);
        dialog.setLocation ((screenSize.width-500)/2, (screenSize.height-100)/2);
        dialog.setVisible(true);
    }
}
