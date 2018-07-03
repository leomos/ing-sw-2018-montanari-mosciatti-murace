package it.polimi.se2018.view.gui;

import it.polimi.se2018.model.events.ModelChangedMessageDiceOnPatternCard;
import it.polimi.se2018.model.events.ModelChangedMessagePatternCard;
import it.polimi.se2018.model.events.ModelChangedMessagePrivateObjective;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SwingDiceOnPatternCard extends JPanel {
    private ArrayList<SwingDie> pc = new ArrayList<>();

    public SwingDiceOnPatternCard (ModelChangedMessageDiceOnPatternCard message, ModelChangedMessagePatternCard modelChangedMessagePatternCard, ArrayList<SwingDie> patterCard, boolean enable) {
        int cont = 0;
        String id = "";

        JPanel p = new JPanel();
        p.setLayout(new GridLayout(4, 5));
        p.setPreferredSize(new Dimension(250, 200));

        JPanel p1 = new JPanel();
        p1.setLayout(new GridLayout(3, 1));
        JLabel l1 = new JLabel("PLAYER: " + modelChangedMessagePatternCard.getIdPlayer(), SwingConstants.CENTER);
        JLabel l2 = new JLabel("PATTERNCARD: " + modelChangedMessagePatternCard.getName(), SwingConstants.CENTER);
        JLabel l3 = new JLabel("DIFFICULTY: " + modelChangedMessagePatternCard.getDifficulty(), SwingConstants.CENTER);
        p1.add(l1);
        p1.add(l2);
        p1.add(l3);

        ArrayList<SwingDie> dice = new ArrayList<>();
        while (cont<80) {
            id = id + message.getRepresentation().charAt(cont) + message.getRepresentation().charAt(cont+1);
            SwingDie button = new SwingDie(0);
            if (enable)
                button.setEnabled(false);

            if (!id.equals("**")) {
                button.setId(id);
                switch (message.getRepresentation().charAt(cont+2)) {
                    case 'r':
                        button.setBackground(Color.RED);
                        break;
                    case 'g':
                        button.setBackground(Color.GREEN);
                        break;
                    case 'y':
                        button.setBackground(Color.YELLOW);
                        break;
                    case 'b':
                        button.setBackground(Color.BLUE);
                        break;
                    case 'p':
                        button.setBackground(new Color(143, 0, 255));
                        break;
                    default:
                        break;
                }
                switch (message.getRepresentation().charAt(cont+3)) {
                    case '1':
                        button.setVal(1);
                        break;
                    case '2':
                        button.setVal(2);
                        break;
                    case '3':
                        button.setVal(3);
                        break;
                    case '4':
                        button.setVal(4);
                        break;
                    case '5':
                        button.setVal(5);
                        break;
                    case '6':
                        button.setVal(6);
                        break;
                    default:
                        break;
                }
            }
            dice.add(button);
            cont = cont + 4;
        }

        for (int i=0; i<20; i++) {
            if (dice.get(i).getVal()==0) {
                p.add(patterCard.get(i));
                pc.add(patterCard.get(i));
            }
            else {
                p.add(dice.get(i));
                pc.add(dice.get(i));
            }
        }

        setLayout(new BorderLayout());
        add(p, BorderLayout.CENTER);
        add(p1, BorderLayout.SOUTH);
    }

    public ArrayList<SwingDie> getPc() {
        return pc;
    }
}
