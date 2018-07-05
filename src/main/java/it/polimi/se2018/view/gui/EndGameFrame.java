package it.polimi.se2018.view.gui;

import it.polimi.se2018.model.events.*;
import it.polimi.se2018.utils.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class EndGameFrame extends SwingPhase {
    private JFrame jFrame = new JFrame();

    private int idClient;

    private ModelChangedMessageEndGame messageEndGame;

    private ModelChangedMessageOnlyOnePlayerLeft messageOnlyOnePlayerLeft;

    private int onePlayer = 0;

    public EndGameFrame(int idClient) {
        this.idClient = idClient;
    }

    public void print() {
        jFrame.setTitle("SAGRADA");
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        ImageLoader imageLoader = new ImageLoader();
        Image image = imageLoader.getSagradaCover();
        image = image.getScaledInstance(500, 670, Image.SCALE_SMOOTH);
        it.polimi.se2018.view.gui.Panel p = new it.polimi.se2018.view.gui.Panel(image);
        jFrame.setContentPane(p);
        jFrame.setLayout(new GridBagLayout());

        JPanel title = new JPanel();
        title.setBackground(new Color(210, 210, 210, 200));
        JLabel label;
        if (onePlayer!=0) {
            label = new JLabel("Only player " + onePlayer + " - " + messageOnlyOnePlayerLeft.getPlayers().get(onePlayer) + " is left in game!!", SwingConstants.CENTER);
            label.setFont(new Font("Ravie", Font.PLAIN, 20));
        }
        else {
            label = new JLabel("FINAL RANKING", SwingConstants.CENTER);
            label.setFont(new Font("Ravie", Font.PLAIN, 26));
        }
        label.setForeground(new Color(253, 233, 16));
        title.add(label);

        JPanel thanks = new JPanel();
        JLabel l = new JLabel("Thanks for playing!!", SwingConstants.CENTER);
        thanks.add(l);
        thanks.setBackground(new Color(210, 210, 210, 200));
        l.setFont(new Font("Ink Free", Font.BOLD, 20));
        GridBagConstraints constraints = new GridBagConstraints();

        JButton esci = new JButton("EXIT");
        esci.addActionListener(actionListener -> {
                System.exit(0);
                //jFrame.dispose();
        });

        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets.top = 150;
        constraints.insets.bottom = 0;
        constraints.anchor = GridBagConstraints.NORTH;
        jFrame.add(title, constraints);

        if (onePlayer==0) {
            SwingScoreboard classifica = new SwingScoreboard(messageEndGame.getScoreboard());

            constraints.weightx = 1;
            constraints.weighty = 1;
            constraints.gridx = 0;
            constraints.gridy = 1;
            constraints.insets.bottom = 150;
            constraints.insets.top = 0;
            constraints.anchor = GridBagConstraints.NORTH;
            jFrame.add(classifica, constraints);

            if (idClient==classifica.getWinner())
                new WinFrame();
            else
                new DefeatFrame();
        }
        else {
            JPanel result = new JPanel();
            result.setBackground(new Color(210, 210, 210, 200));
            JLabel jLabel = new JLabel("", SwingConstants.CENTER);
            jLabel.setFont(new Font("Ravie", Font.PLAIN, 24));
            if (onePlayer==idClient) {
                jLabel.setText("CONGRATS, YOU WIN!!");
                jLabel.setForeground(Color.GREEN);
            }
            if (onePlayer!=idClient) {
                jLabel.setText("YOU LOSE!");
                jLabel.setForeground(Color.RED);
            }
            result.add(jLabel);
            constraints.weightx = 1;
            constraints.weighty = 1;
            constraints.gridx = 0;
            constraints.gridy = 1;
            constraints.insets.bottom = 150;
            constraints.insets.top = 0;
            constraints.anchor = GridBagConstraints.NORTH;
            jFrame.add(result, constraints);
        }

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

        Dimension screenSize = Toolkit.getDefaultToolkit ().getScreenSize();
        Dimension frameSize = jFrame.getSize();
        jFrame.setLocation ((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
    }

    @Override
    public void update(ModelChangedMessagePatternCard message) {

    }

    @Override
    public void update(ModelChangedMessagePrivateObjective message) {

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

    public void update(ModelChangedMessageEndGame message) {
        messageEndGame = message;
        this.print();
    }

    @Override
    public void update(ModelChangedMessageToolCard message) {

    }

    @Override
    public void update(ModelChangedMessageRefresh message) {

    }

    @Override
    public void update(ModelChangedMessageOnlyOnePlayerLeft message) {
        messageOnlyOnePlayerLeft = message;
        onePlayer = message.getPlayerIdLeft();
        this.print();
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

    @Override
    public void close() {
        jFrame.dispose();
    }
}
