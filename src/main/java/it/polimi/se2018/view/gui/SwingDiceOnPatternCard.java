package it.polimi.se2018.view.gui;

import it.polimi.se2018.model.events.ModelChangedMessageDiceOnPatternCard;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SwingDiceOnPatternCard extends JPanel {
    public SwingDiceOnPatternCard (ModelChangedMessageDiceOnPatternCard message){
        ArrayList<SwingDie> buttons = new ArrayList<>();
        int cont = 0;
        String id = "";

        setPreferredSize(new Dimension(250, 200));
        setLayout(new GridLayout(4, 5));

        while (cont<80) {
            id = id + message.getRepresentation().charAt(cont) + message.getRepresentation().charAt(cont+1);
            SwingDie button = new SwingDie(0);
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
            buttons.add(button);
            cont = cont + 4;
        }
        for (int i=0; i<20; i++) {
            add(buttons.get(i));
        }
    }
}
