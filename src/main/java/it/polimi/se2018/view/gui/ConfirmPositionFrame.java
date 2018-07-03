package it.polimi.se2018.view.gui;

import it.polimi.se2018.model.events.ModelChangedMessageDiceOnPatternCard;
import it.polimi.se2018.model.events.ModelChangedMessagePatternCard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

public class ConfirmPositionFrame {

    private int row1 = -1;

    private int col1 = -1;

    private int row2 = -1;

    private int col2 = -1;

    private JDialog jDialog;

    private JDialog d;

    private ArrayList<Integer> v = new ArrayList<>();

    public ConfirmPositionFrame(ModelChangedMessageDiceOnPatternCard messageDiceOnPatternCard, ModelChangedMessagePatternCard messagePatternCard) {
        SwingPatternCard patternCard = new SwingPatternCard(messagePatternCard, false);
        SwingDiceOnPatternCard diceOnPatternCard = new SwingDiceOnPatternCard(messageDiceOnPatternCard, messagePatternCard, patternCard.getPatternCard(), false);

        JLabel label = new JLabel("Select a couple of dice from your patternCard");

        for (int i=0; i<20; i++) {
            int finalN = i;
            diceOnPatternCard.getPc().get(i).addActionListener(actionListener -> {
                    if (row1==-1 && col1==-1) {
                        row1 = finalN % 5;
                        col1 = finalN / 5;
                    }
                    else if (row2==-1 && col2==-1) {
                        row2 = finalN % 5;
                        col2 = finalN / 5;
                    }
            });
        }

        JFrame frame = new JFrame();
        jDialog = new JDialog(frame, "", true);
        jDialog.setLayout(new BorderLayout());

        JButton button = new JButton("CONTINUE");
        button.addActionListener(actionListener -> {
                if (row1 != -1 && col1 != -1 && row2 != -1 && col2 != -1) {
                    JFrame f = new JFrame();
                    d = new JDialog(f, "", Dialog.ModalityType.APPLICATION_MODAL);
                    d.addWindowListener(new WindowListener() {
                        @Override
                        public void windowOpened(WindowEvent e) {

                        }

                        @Override
                        public void windowClosing(WindowEvent e) {

                        }

                        @Override
                        public void windowClosed(WindowEvent e) {
                            jDialog.dispose();
                        }

                        @Override
                        public void windowIconified(WindowEvent e) {

                        }

                        @Override
                        public void windowDeiconified(WindowEvent e) {

                        }

                        @Override
                        public void windowActivated(WindowEvent e) {

                        }

                        @Override
                        public void windowDeactivated(WindowEvent e) {

                        }
                    });

                    d.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                    d.setSize(200, 100);

                    JLabel l = new JLabel("Do you confirm?");
                    JLabel label1 = new JLabel("Old position: (" + Integer.toString(row1) + "," + Integer.toString(col1) + ")", SwingConstants.CENTER);
                    JLabel label2 = new JLabel("New position: (" + Integer.toString(row2) + "," + Integer.toString(col2) + ")", SwingConstants.CENTER);
                    JPanel panel = new JPanel();
                    panel.setLayout(new GridLayout(2,1));
                    panel.add(label1);
                    panel.add(label2);
                    JButton b = new JButton("CONFIRM");
                    b.addActionListener(actionlistener -> {
                            v.add(row1);
                            v.add(col1);
                            v.add(row2);
                            v.add(col2);
                            d.dispose();
                    });
                    d.setLayout(new BorderLayout());
                    d.add(l, BorderLayout.NORTH);
                    d.add(panel, BorderLayout.CENTER);
                    d.add(b, BorderLayout.SOUTH);
                    d.pack();
                    d.setVisible(true);
                } else {
                    new MoveFailedFrame("Select two dice");
                }
        });

        jDialog.add(label, BorderLayout.NORTH);
        jDialog.add(diceOnPatternCard, BorderLayout.CENTER);
        jDialog.add(button, BorderLayout.SOUTH);
        jDialog.setSize(new Dimension(250, 350));

        jDialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        jDialog.setResizable(false);
        jDialog.setVisible(true);

    }

    public ArrayList<Integer> getvalues() {
        while(v.isEmpty()) {}
        return v;
    }
}
