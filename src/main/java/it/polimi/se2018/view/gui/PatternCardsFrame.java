package it.polimi.se2018.view.gui;

import it.polimi.se2018.model.events.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class PatternCardsFrame extends SwingPhase implements ActionListener {

    private JFrame jFrame = new JFrame();

    private int idClient;

    private ArrayList<ModelChangedMessagePatternCard> patternCards = new ArrayList<>();

    private ModelChangedMessagePrivateObjective privateObjective;

    private String idPatternCardChosen = "";

    private ButtonGroup group;

    public PatternCardsFrame(int idClient) {
        this.idClient = idClient;
    }

    public void update(ModelChangedMessage message) {
        if(message instanceof ModelChangedMessagePatternCard) {
            if (((ModelChangedMessagePatternCard) message).getIdPlayer() == idClient)
                patternCards.add((ModelChangedMessagePatternCard) message);
        }
        else if(message instanceof ModelChangedMessagePrivateObjective)
            if (((ModelChangedMessagePrivateObjective) message).getIdPlayer() == idClient)
                privateObjective = ((ModelChangedMessagePrivateObjective) message);
    }

    public void print() {
        SwingPatternCard p1 = new SwingPatternCard(patternCards.get(0), true);
        SwingPatternCard p2 = new SwingPatternCard(patternCards.get(1), true);
        SwingPatternCard p3 = new SwingPatternCard(patternCards.get(2), true);
        SwingPatternCard p4 = new SwingPatternCard(patternCards.get(3), true);
        JButton po = new JButton("PRIVATE OBJECTIVE");
        po.addActionListener(actionListener -> {
                JFrame f = new JFrame();
                f.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
                SwingPrivateObjective p = new SwingPrivateObjective(privateObjective);
                f.getContentPane().add(p);
                f.setVisible(true);
                f.pack();
                f.setResizable(false);
        });

        jFrame.setResizable(false);
        jFrame.setTitle("PATTERNCARD CHOICE");
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setSize(550, 700);
        jFrame.setLayout(new BorderLayout());
        JPanel p = new JPanel();
        Color c = new Color(34, 139, 34);
        p.setBackground(c);

        jFrame.setContentPane(p);

        JButton b = new JButton("CONFERMA");
        b.setEnabled(false);
        b.addActionListener(this);

        JRadioButton rb1 = new JRadioButton("PATTERNCARD 1");
        rb1.setActionCommand(Integer.toString(patternCards.get(0).getIdPatternCard()));
        rb1.addActionListener(actionListener -> {
                b.setEnabled(true);
        });
        JRadioButton rb2 = new JRadioButton("PATTERNCARD 2");
        rb2.setActionCommand(Integer.toString(patternCards.get(1).getIdPatternCard()));
        rb2.addActionListener(actionListener -> {
                b.setEnabled(true);
        });
        JRadioButton rb3 = new JRadioButton("PATTERNCARD 3");
        rb3.setActionCommand(Integer.toString(patternCards.get(2).getIdPatternCard()));
        rb3.addActionListener(actionListener -> {
                b.setEnabled(true);
        });
        JRadioButton rb4 = new JRadioButton("PATTERNCARD 4");
        rb4.setActionCommand(Integer.toString(patternCards.get(3).getIdPatternCard()));
        rb4.addActionListener(actionListener -> {
                b.setEnabled(true);
        });

        group = new ButtonGroup();
        group.add(rb1);
        group.add(rb2);
        group.add(rb3);
        group.add(rb4);

        JPanel panel = new JPanel();
        GridBagConstraints constraints = new GridBagConstraints();
        panel.setLayout(new GridBagLayout());

        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        JPanel panel4 = new JPanel();
        panel1.setLayout(new BorderLayout());
        panel2.setLayout(new BorderLayout());
        panel3.setLayout(new BorderLayout());
        panel4.setLayout(new BorderLayout());

        panel1.add(p1, BorderLayout.CENTER);
        panel1.add(rb1, BorderLayout.SOUTH);

        panel2.add(p2, BorderLayout.CENTER);
        panel2.add(rb2, BorderLayout.SOUTH);

        panel3.add(p3, BorderLayout.CENTER);
        panel3.add(rb3, BorderLayout.SOUTH);

        panel4.add(p4, BorderLayout.CENTER);
        panel4.add(rb4, BorderLayout.SOUTH);

        constraints.insets = new Insets(5, 10, 5, 10);
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(panel1, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(panel2, constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        panel.add(panel3, constraints);

        constraints.gridx = 1;
        constraints.gridy = 1;
        panel.add(panel4, constraints);

        jFrame.add(po, BorderLayout.NORTH);
        jFrame.add(panel, BorderLayout.CENTER);
        jFrame.add(b, BorderLayout.SOUTH);

        jFrame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        idPatternCardChosen = idPatternCardChosen + group.getSelection().getActionCommand();
        try {
            serverInterface.notify(new PlayerMessageSetup(idClient, Integer.parseInt(idPatternCardChosen)));
        } catch (RemoteException e1) {
            e1.printStackTrace();
        }
        jFrame.dispose();
    }

    public Integer askForPatternCard() {
        do {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (idPatternCardChosen.length()==0);
        return Integer.parseInt(idPatternCardChosen);
    }

    @Override
    public PlayerMessage getMainMove() {
        return null;
    }

    @Override
    public ArrayList<Integer> getPositionInPatternCard() {
        return null;
    }
}
