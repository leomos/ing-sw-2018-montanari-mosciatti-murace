package it.polimi.se2018.view.gui;

import it.polimi.se2018.model.events.ModelChangedMessageDiceArena;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SwingDiceArena extends JPanel {
    private ArrayList<SwingDie> buttons;

    /**
     * This constructor generates JPanel which contains all the dice in Dice Arena
     * @param message to represent Dice Arena in model
     */
    public SwingDiceArena(ModelChangedMessageDiceArena message) {
        SwingDie d = new SwingDie(0);
        buttons = d.setOfDice(message.getRepresentation());

        setLayout(new FlowLayout());

        int l = buttons.size();
        for (int i=0; i<l; i++) {
            buttons.get(i).setToolTipText("Dice Arena");
            add(buttons.get(i));
        }
    }

    public ArrayList<SwingDie> getButtons() {
        return buttons;
    }
}
