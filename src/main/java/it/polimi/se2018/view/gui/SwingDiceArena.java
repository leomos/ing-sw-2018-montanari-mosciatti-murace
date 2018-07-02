package it.polimi.se2018.view.gui;

import it.polimi.se2018.model.events.ModelChangedMessageDiceArena;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SwingDiceArena extends JPanel {
    private ArrayList<SwingDie> buttons;

    public SwingDiceArena(ModelChangedMessageDiceArena message) {
        SwingDie d = new SwingDie(0);
        buttons = d.setOfDice(message.getRepresentation());

        setLayout(new GridLayout(1, buttons.size()));

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
