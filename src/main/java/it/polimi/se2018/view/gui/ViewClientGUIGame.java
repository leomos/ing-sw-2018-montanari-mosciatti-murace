package it.polimi.se2018.view.gui;

import it.polimi.se2018.model.events.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Scanner;

public class ViewClientGUIGame extends SwingPhase {

    private JFrame jFrame = new JFrame();

    private String idDieChosen = "";

    private String toolCardChosen = "";

    private int riga;

    private int colonna;

    private int idClient;

    private boolean isMyTurn;

    private Scanner input;

    private ArrayList<String> idPlayers = new ArrayList<String>();

    private ArrayList<ModelChangedMessagePatternCard> patternCards = new ArrayList<ModelChangedMessagePatternCard>();

    private ArrayList<ModelChangedMessageDiceOnPatternCard> diceOnPatternCards = new ArrayList<ModelChangedMessageDiceOnPatternCard>();

    private ArrayList<ModelChangedMessagePublicObjective> publicObjectives = new ArrayList<ModelChangedMessagePublicObjective>();

    private ArrayList<ModelChangedMessageToolCard> toolCards = new ArrayList<ModelChangedMessageToolCard>();

    private ModelChangedMessageDiceArena diceArena;

    private ModelChangedMessagePrivateObjective privateObjective;

    private ModelChangedMessageTokensLeft tokensLeft;

    private ArrayList<ModelChangedMessageRound> roundTrack = new ArrayList<ModelChangedMessageRound>();

    public ViewClientGUIGame (int idClient) {
        this.idClient = idClient;
    }

    @Override
    public void update(ModelChangedMessage message) {

        if (message instanceof ModelChangedMessagePatternCard){
            idPlayers.add(((ModelChangedMessagePatternCard) message).getIdPlayer());
            patternCards.add((ModelChangedMessagePatternCard) message);
            diceOnPatternCards.add(null);
        }
        else if (message instanceof ModelChangedMessageDiceOnPatternCard){
            int i = idPlayers.indexOf(((ModelChangedMessageDiceOnPatternCard) message).getIdPlayer());
            diceOnPatternCards.remove(i);
            diceOnPatternCards.add(i, (ModelChangedMessageDiceOnPatternCard) message);
        }
        else if(message instanceof ModelChangedMessagePrivateObjective) {
            if (((ModelChangedMessagePrivateObjective) message).getIdPlayer().equals(Integer.toString(idClient)))
                privateObjective = ((ModelChangedMessagePrivateObjective) message);
        }
        else if(message instanceof ModelChangedMessagePublicObjective)
            publicObjectives.add((ModelChangedMessagePublicObjective)message);
        else if(message instanceof ModelChangedMessageToolCard) {
            if(toolCards.size() == 3) {
                for(int i = 0; i < 3; i++)
                    if(toolCards.get(i).getIdToolCard().equals(((ModelChangedMessageToolCard) message).getIdToolCard())) {
                        toolCards.remove(i);
                        toolCards.add(i, (ModelChangedMessageToolCard) message);
                    }
            } else {
                toolCards.add((ModelChangedMessageToolCard) message);
            }
        }
        else if(message instanceof ModelChangedMessageDiceArena)
            diceArena = ((ModelChangedMessageDiceArena)message);
        else if(message instanceof ModelChangedMessageRound) {
            int i = Integer.parseInt(((ModelChangedMessageRound) message).getIdRound());
            if(i >= roundTrack.size())
                roundTrack.add((ModelChangedMessageRound) message);
            else {
                roundTrack.remove(i);
                roundTrack.add(i, (ModelChangedMessageRound) message);
            }
        }
        else if(message instanceof ModelChangedMessageTokensLeft) {
            if (((ModelChangedMessageTokensLeft) message).getIdPlayer().equals(Integer.toString(idClient)))
                tokensLeft = (ModelChangedMessageTokensLeft) message;
        }
        else if(message instanceof ModelChangedMessageRefresh) {
            isMyTurn = Integer.parseInt(((ModelChangedMessageRefresh) message).getIdPlayerPlaying()) == idClient;
        }
    }

    public void print(){
        SwingToolCards[] toolCard = new SwingToolCards[3];
        for (int i=0; i<3; i++) {
            toolCard[i] = new SwingToolCards(toolCards.get(i));
        }

        SwingPublicObjective[] publicObjective = new SwingPublicObjective[3];
        for (int i=0; i<3; i++) {
            publicObjective[i] = new SwingPublicObjective(publicObjectives.get(i));
        }

        SwingPatternCard myPatternCard = null;
        SwingPatternCard[] patternCard = new SwingPatternCard[3];
        for (int i=0; i<patternCards.size(); i++) {
            if (patternCards.get(i).getIdPlayer().equals(Integer.toString(idClient))) {
                myPatternCard = new SwingPatternCard(patternCards.get(i), false);
                for (int n=0; n<20; n++) {
                    int finalN = n;
                    SwingDie die = myPatternCard.getPatternCard().get(n);
                    die.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (!idDieChosen.equals("")) {
                                riga = finalN / 5;
                                colonna = finalN % 5;
                            }
                        }
                    });
                }
            }
            else
                patternCard[i] = new SwingPatternCard(patternCards.get(i), true);
        }

        SwingDiceArena arena = new SwingDiceArena(diceArena);
        for (int i=0; i<arena.getButtons().size(); i++){
            SwingDie b = arena.getButtons().get(i);
            b.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (idDieChosen.equals("")) {
                        idDieChosen = idDieChosen + b.getId();
                    }
                }
            });
        }

        SwingPlayer player = new SwingPlayer(myPatternCard, new SwingPrivateObjective(privateObjective), tokensLeft.getTokensLeft());

        JButton conferma = new JButton("CONFERMA MOSSA");
        conferma.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!idDieChosen.equals(""))
                    new ConfirmationFrame(idClient, idDieChosen, colonna, riga, toolCardChosen);
                idDieChosen = "";
                toolCardChosen = "";
            }
        });

        JButton endTurn = new JButton("END TURN");

        GridBagConstraints constraints = new GridBagConstraints();
        jFrame.setLayout(new GridBagLayout());
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setTitle("TABLE");
        Color c = new Color(34, 139, 34);
        jFrame.getContentPane().setBackground(c);

        constraints.insets = new Insets(0, 20, 0, 0);
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.anchor = GridBagConstraints.SOUTHWEST;
        jFrame.add(player, constraints);

        JPanel pc = new JPanel();
        pc.setBackground(c);
        pc.setPreferredSize(new Dimension(760, 250));
        int n = patternCards.size();
        pc.setLayout(new FlowLayout());
        for (int i=0; i<n; i++) {
            if (!patternCards.get(i).getIdPlayer().equals(Integer.toString(idClient)))
                pc.add(patternCard[i]);
        }

        constraints.insets.left = 0;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.anchor = GridBagConstraints.NORTHWEST;
        jFrame.add(pc, constraints);

        JPanel po = new JPanel();
        po.setPreferredSize(new Dimension(555, 255));
        po.setLayout(new GridLayout(1, 3, 5, 0));
        for (int i=0; i<3; i++)
            po.add(publicObjective[i]);
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.anchor = GridBagConstraints.NORTHEAST;
        jFrame.add(po, constraints);

        JPanel t = new JPanel();
        t.setPreferredSize(new Dimension(555, 270));
        t.setLayout(new GridLayout(1, 3, 5, 0));
        for (int i=0; i<3; i++)
            t.add(toolCard[i]);
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.anchor = GridBagConstraints.NORTHEAST;
        jFrame.add(t, constraints);

        constraints.insets.bottom = 20;
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.anchor = GridBagConstraints.SOUTH;
        jFrame.add(arena, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.anchor = GridBagConstraints.EAST;
        jFrame.add(conferma, constraints);

        constraints.insets.top = 70;
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.anchor = GridBagConstraints.EAST;
        jFrame.add(endTurn, constraints);

        jFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        jFrame.setVisible(true);
    }

    @Override
    public Integer askForPatternCard() {
        return null;
    }

    public PlayerMessage getMainMove() {
        do {
            //Se sono entrambi premuti ritorna PlayerMessageDie
        } while (true);
    }

}
