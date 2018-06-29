package it.polimi.se2018.view.gui;

import it.polimi.se2018.model.events.ModelChangedMessageDiceArena;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SwingDiceArena extends JPanel {
    public SwingDiceArena(ModelChangedMessageDiceArena message) {
        SwingDie d = new SwingDie(0);
        ArrayList<SwingDie> dice = d.setOfDice(message.getRepresentation());
        setLayout(new GridLayout(1, 9));

        int l = dice.size();
        for (int i=0; i<l; i++) {
            dice.get(i).setToolTipText("Dice Arena");
            add(dice.get(i));
        }
    }
}
