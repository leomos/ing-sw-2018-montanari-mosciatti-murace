package it.polimi.se2018.view.gui;

import it.polimi.se2018.model.events.ModelChangedMessage;
import it.polimi.se2018.model.events.ModelChangedMessageEndGame;
import it.polimi.se2018.model.events.PlayerMessage;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class EndGameFrame extends SwingPhase {
    private JFrame jFrame = new JFrame();

    private int idClient;

    private ModelChangedMessageEndGame messageEndGame;

    public EndGameFrame(int idClient) {
        this.idClient = idClient;
    }

    public void print() {
        jFrame.setTitle("SAGRADA");
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Image image = java.awt.Toolkit.getDefaultToolkit().getImage("../ing-sw-2018-montanari-mosciatti-murace\\src\\images\\Sagrada.jpg");
        image = image.getScaledInstance(500, 670, Image.SCALE_SMOOTH);
        it.polimi.se2018.view.gui.Panel p = new it.polimi.se2018.view.gui.Panel(image);
        jFrame.setContentPane(p);
        jFrame.setLayout(new GridBagLayout());

        JPanel title = new JPanel();
        title.setBackground(new Color(210, 210, 210, 200));
        JLabel label = new JLabel("FINAL RANKING", SwingConstants.CENTER);
        label.setFont(new Font("Ravie", Font.PLAIN, 26));
        label.setForeground(new Color(253, 233, 16));
        title.add(label);

        SwingScoreboard classifica = new SwingScoreboard(messageEndGame.getScoreboard());

        JPanel thanks = new JPanel();
        JLabel l = new JLabel("Thanks for playing!!", SwingConstants.CENTER);
        thanks.add(l);
        thanks.setBackground(new Color(210, 210, 210, 200));
        l.setFont(new Font("Ink Free", Font.BOLD, 20));
        GridBagConstraints constraints = new GridBagConstraints();

        JButton esci = new JButton("EXIT");
        esci.addActionListener(actionListener -> {
                jFrame.dispose();
        });

        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets.top = 150;
        constraints.insets.bottom = 0;
        constraints.anchor = GridBagConstraints.NORTH;
        jFrame.add(title, constraints);

        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.insets.bottom = 150;
        constraints.insets.top = 0;
        constraints.anchor = GridBagConstraints.NORTH;
        jFrame.add(classifica, constraints);

        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.insets.top = 100;
        constraints.insets.bottom = 0;
        constraints.anchor = GridBagConstraints.CENTER;
        jFrame.add(thanks, constraints);

        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.insets.bottom = 0;
        constraints.insets.bottom = 70;
        constraints.anchor = GridBagConstraints.SOUTH;
        jFrame.add(esci, constraints);

        jFrame.setSize(500, 700);
        jFrame.setResizable(false);
        jFrame.setVisible(true);

        if (idClient==classifica.getWinner())
            new WinFrame();
        else
            new DefeatFrame();

        Dimension screenSize = Toolkit.getDefaultToolkit ().getScreenSize();
        Dimension frameSize = jFrame.getSize();
        jFrame.setLocation ((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
    }

    public void set() {
        this.messageEndGame = new ModelChangedMessageEndGame("1231246231124050");
    }

    @Override
    public void update(ModelChangedMessage modelChangedMessage) {

    }

    @Override
    public Integer askForPatternCard() {
        return null;
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

}
