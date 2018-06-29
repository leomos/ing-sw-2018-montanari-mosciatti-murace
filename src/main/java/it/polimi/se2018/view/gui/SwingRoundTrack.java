package it.polimi.se2018.view.gui;

import it.polimi.se2018.model.events.ModelChangedMessageDiceArena;
import it.polimi.se2018.model.events.ModelChangedMessageRound;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SwingRoundTrack extends it.polimi.se2018.view.gui.Panel {
    private JButton[] buttons = new JButton[10];
    private String[] representations = new String[10];

    public SwingRoundTrack() {
        super(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Alessandro Murace\\Pictures\\RoundTrack.jpg"));
        setLayout(new GridLayout(1, 10, 5, 0));
        setPreferredSize(new Dimension(600, 50));

        for (int i=0; i<10; i++) {
            representations[i] = "";
        }

        representations[0] = representations[0] + "r4y3g6p1b3";


        for (Integer i = 1; i<11; i++) {
            JButton b = new JButton();
            b.setText(i.toString());
            if (representations[i-1].length()==0)
                b.setEnabled(false);
            buttons[i-1] = b;
            b.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFrame jFrame = new JFrame("ROUND " + b.getText());
                    ModelChangedMessageDiceArena messageDiceArena = new ModelChangedMessageDiceArena(representations[Integer.parseInt(b.getText())-1]);
                    SwingDiceArena round = new SwingDiceArena(messageDiceArena);
                    jFrame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
                    jFrame.add(round);
                    jFrame.pack();
                    jFrame.setVisible(true);
                }
            });
            add(b);
        }
    }

    public void setRoundtrack(ModelChangedMessageRound messageRound) {
        int i = Integer.parseInt(messageRound.getIdRound());
        this.representations[i] = messageRound.getRepresentation();
    }

    public static void main(String args[]) {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        ModelChangedMessageRound message = new ModelChangedMessageRound("1", "r4y3g6p1b3");
        SwingRoundTrack r = new SwingRoundTrack();
        //r.setRoundtrack(message);
        f.add(r);
        f.setVisible(true);
        f.pack();
    }
}
