package it.polimi.se2018.view.gui;

import it.polimi.se2018.model.events.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class ViewClientGUISetup extends SwingPhase implements ActionListener {

    private JFrame jFrame = new JFrame();

    private int idClient;

    private ArrayList<ModelChangedMessagePatternCard> patternCards = new ArrayList<>();

    private ModelChangedMessagePrivateObjective privateObjective;

    private String idPatternCardChosen = "";

    private ButtonGroup group;

    public ViewClientGUISetup(int idClient) {
        this.idClient = idClient;
    }

    @Override
    public void update(ModelChangedMessagePatternCard message) {
        if (message.getIdPlayer() == idClient)
            patternCards.add(message);
    }

    @Override
    public void update(ModelChangedMessagePrivateObjective message) {
        if (message.getIdPlayer()==idClient)
            privateObjective = message;
    }

    @Override
    public void update(ModelChangedMessageDiceOnPatternCard message) {

    }

    @Override
    public void update(ModelChangedMessagePublicObjective message) {

    }

    @Override
    public void update(ModelChangedMessageDiceArena message) {

    }

    @Override
    public void update(ModelChangedMessageRound message) {

    }

    @Override
    public void update(ModelChangedMessageTokensLeft message) {

    }

    @Override
    public void update(ModelChangedMessageEndGame message) {

    }

    @Override
    public void update(ModelChangedMessageToolCard message) {

    }

    @Override
    public void update(ModelChangedMessageRefresh message) {

    }

    @Override
    public void update(ModelChangedMessageOnlyOnePlayerLeft message) {

    }

    /**
     * Prints the 4 pattern cards given to the player
     */
    public void print() {
        SwingPatternCard p1 = new SwingPatternCard(patternCards.get(0), true);
        SwingPatternCard p2 = new SwingPatternCard(patternCards.get(1), true);
        SwingPatternCard p3 = new SwingPatternCard(patternCards.get(2), true);
        SwingPatternCard p4 = new SwingPatternCard(patternCards.get(3), true);


        jFrame.setResizable(false);
        jFrame.setTitle("PATTERNCARD CHOICE");
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setLayout(new FlowLayout());
        JPanel p = new JPanel();
        Color c = new Color(34, 139, 34);
        p.setBackground(c);
        jFrame.setContentPane(p);

        JButton b = new JButton("CONFERMA");
        b.setEnabled(false);
        b.addActionListener(this);

        JRadioButton rb1 = new JRadioButton("PATTERNCARD 1");
        rb1.setActionCommand(Integer.toString(patternCards.get(0).getIdPatternCard()));
        rb1.addActionListener(actionListener -> b.setEnabled(true));
        JRadioButton rb2 = new JRadioButton("PATTERNCARD 2");
        rb2.setActionCommand(Integer.toString(patternCards.get(1).getIdPatternCard()));
        rb2.addActionListener(actionListener -> b.setEnabled(true));
        JRadioButton rb3 = new JRadioButton("PATTERNCARD 3");
        rb3.setActionCommand(Integer.toString(patternCards.get(2).getIdPatternCard()));
        rb3.addActionListener(actionListener -> b.setEnabled(true));
        JRadioButton rb4 = new JRadioButton("PATTERNCARD 4");
        rb4.setActionCommand(Integer.toString(patternCards.get(3).getIdPatternCard()));
        rb4.addActionListener(actionListener -> b.setEnabled(true));

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

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BorderLayout());

        jPanel.add(panel, BorderLayout.CENTER);
        jPanel.add(b, BorderLayout.SOUTH);

        SwingPrivateObjective pri = new SwingPrivateObjective(privateObjective);

        jFrame.add(jPanel);
        jFrame.add(pri);

        jFrame.pack();
        jFrame.setVisible(true);

        Dimension screenSize = Toolkit.getDefaultToolkit ().getScreenSize();
        Dimension frameSize = jFrame.getSize();
        jFrame.setLocation ((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
    }

    /**
     * Reprint the JFrame when the player push the button b in method print()
     * @param e
     */
    public void actionPerformed(ActionEvent e) {
        idPatternCardChosen = idPatternCardChosen + group.getSelection().getActionCommand();
        try {
            waiting();
            serverInterface.notify(new PlayerMessageSetup(idClient, Integer.parseInt(idPatternCardChosen)));
        } catch (RemoteException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * Method needed for the player to choose one of the 4 available pattern cards
     * @param s string inserted by the player
     * @return pattern card id if the value is accepted, -1 otherwise
     */
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

    @Override
    public Integer getDieFromDiceArena() {
        return 0;
    }

    @Override
    public ArrayList<Integer> getIncrementedValue() {
        return null;
    }

    @Override
    public ArrayList<Integer> getSinglePositionInPatternCard(ArrayList<Integer> listOfAvailablePosition) {
        return null;
    }

    @Override
    public ArrayList<Integer> getDoublePositionInPatternCard() {
        return null;
    }

    @Override
    public ArrayList<Integer> getDieFromRoundTrack() {
        return null;
    }

    @Override
    public Integer getValueForDie(){
        return null;
    }

    /**
     * Method to close the JFrame created
     */
    @Override
    public void close() {
        jFrame.dispose();
    }

    /**
     * Creates a JFrame that contains the PatternCard chosen, the Private Objective and the tokens of the player while
     * he is waiting for other players' choice
     */
    public void waiting() {
        jFrame.getContentPane().removeAll();
        jFrame.repaint();

        String s = "********************************************************************************";
        jFrame.getContentPane().setBackground(new Color(34, 139, 34));
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setResizable(false);
        jFrame.setLayout(new BorderLayout());

        int tokens = 0;
        SwingDiceOnPatternCard diceOnPatternCard = null;
        SwingPatternCard patternCard;
        for (ModelChangedMessagePatternCard patternCard1 : patternCards) {
            if (patternCard1.getIdPatternCard() == Integer.parseInt(idPatternCardChosen)) {
                patternCard = new SwingPatternCard(patternCard1, true);
                ModelChangedMessageDiceOnPatternCard message = new ModelChangedMessageDiceOnPatternCard(idClient, patternCard1.getIdPatternCard(), s);
                diceOnPatternCard = new SwingDiceOnPatternCard(message, patternCard1, patternCard.getPatternCard(), true);
                tokens = patternCard1.getDifficulty();
            }
        }

        SwingPrivateObjective privateObjective1 = new SwingPrivateObjective(this.privateObjective);

        JLabel label = new JLabel("Waiting for other players...", SwingConstants.CENTER);
        label.setFont(new Font("Imprint MT Shadow", Font.PLAIN, 24));

        SwingPlayer player = new SwingPlayer(diceOnPatternCard, privateObjective1, tokens);

        jFrame.add(label, BorderLayout.NORTH);
        jFrame.add(player, BorderLayout.CENTER);

        jFrame.pack();
        jFrame.setVisible(true);

        jFrame.validate();
    }
}
