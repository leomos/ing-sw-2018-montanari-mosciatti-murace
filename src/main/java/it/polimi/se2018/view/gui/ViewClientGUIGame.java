package it.polimi.se2018.view.gui;

import it.polimi.se2018.model.events.*;
import it.polimi.se2018.network.visitor.MessageVisitorInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Scanner;

public class ViewClientGUIGame extends SwingPhase {
    private JFrame jFrame = new JFrame();

    private String idDieChosen = "";

    private String toolCardChosen = "";

    private int riga;

    private int colonna;

    private int idClient;

    private boolean end = false;

    private boolean isMyTurn;

    private boolean move = false;

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
            if (isMyTurn)
                new TurnFrame();
        }
    }

    public void print(){
        SwingToolCards[] toolCard = new SwingToolCards[3];
        for (int i=0; i<3; i++) {
            int finalI = i;
            toolCard[i] = new SwingToolCards(toolCards.get(i));
            toolCard[i].getCard().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    toolCardChosen = toolCardChosen + toolCards.get(finalI).getIdToolCard();
                }
            });
        }

        SwingPublicObjective[] publicObjective = new SwingPublicObjective[3];
        for (int i=0; i<3; i++) {
            publicObjective[i] = new SwingPublicObjective(publicObjectives.get(i));
        }

        SwingPatternCard myPatternCard;
        SwingDiceOnPatternCard mine = null;
        SwingDiceOnPatternCard[] patternCard = new SwingDiceOnPatternCard[3];
        for (int i=0; i<patternCards.size(); i++) {
            if (patternCards.get(i).getIdPlayer().equals(Integer.toString(idClient))) {
                myPatternCard = new SwingPatternCard(patternCards.get(i), false);

                for (int m=0; m<diceOnPatternCards.size(); m++) {
                    if (diceOnPatternCards.get(m).getIdPlayer().equals(Integer.toString(idClient)))
                        mine = new SwingDiceOnPatternCard(diceOnPatternCards.get(m), patternCards.get(i), myPatternCard.getPatternCard());
                }
                for (int n=0; n<20; n++) {
                    int finalN = n;
                    SwingDie die = mine.getPc().get(n);
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
            else {
                SwingPatternCard card = new SwingPatternCard(patternCards.get(i), true);
                for (int m=0; m<diceOnPatternCards.size(); m++) {
                    if (diceOnPatternCards.get(m).getIdPatternCard().equals(card.getId()))
                        patternCard[m] = new SwingDiceOnPatternCard(diceOnPatternCards.get(m), patternCards.get(i), card.getPatternCard());
                }
            }
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

        SwingPlayer player = new SwingPlayer(mine, new SwingPrivateObjective(privateObjective), tokensLeft.getTokensLeft());

        JButton conferma = new JButton("CONFERMA MOSSA");
        conferma.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isMyTurn) {
                    if (!idDieChosen.equals("")) {
                        ConfirmationFrame f = new ConfirmationFrame(idDieChosen, colonna, riga, toolCardChosen);
                        f.addWindowListener(new WindowListener() {
                            @Override
                            public void windowOpened(WindowEvent e) {
                            }

                            @Override
                            public void windowClosing(WindowEvent e) {
                            }

                            @Override
                            public void windowClosed(WindowEvent e) {
                                if (f.isOk() == 1)
                                    move = true;
                                if (f.isOk() == 2) {
                                    toolCardChosen = "";
                                    idDieChosen = "";
                                }
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
                    }
                }
                else {
                    new NotYourTurnFrame();
                    toolCardChosen = "";
                    idDieChosen = "";
                }
            }
        });

        JButton endTurn = new JButton("END TURN");
        endTurn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                end = true;
            }
        });

        SwingRoundTrack rt = new SwingRoundTrack(roundTrack);

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
        for (int i=0; i<3; i++) {
            t.add(toolCard[i]);
        }
        constraints.insets.bottom = 20;
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.anchor = GridBagConstraints.SOUTHEAST;
        jFrame.add(t, constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.anchor = GridBagConstraints.SOUTH;
        jFrame.add(arena, constraints);

        constraints.insets.right = 30;
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.anchor = GridBagConstraints.EAST;
        jFrame.add(conferma, constraints);

        constraints.insets.top = 70;
        constraints.insets.right = 50;
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.anchor = GridBagConstraints.EAST;
        jFrame.add(endTurn, constraints);

        constraints.fill = GridBagConstraints.NONE;
        constraints.insets.top = 300;
        constraints.insets.left = 10;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.anchor = GridBagConstraints.SOUTHWEST;
        jFrame.add(rt, constraints);

        jFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        jFrame.setVisible(true);
    }

    @Override
    public Integer askForPatternCard() {
        return null;
    }

    public PlayerMessage getMainMove() {
        PlayerMessage message = new PlayerMessage() {
            @Override
            public void accept(MessageVisitorInterface messageVisitorInterface) {

            }
        };

        if (!idDieChosen.equals("") && toolCardChosen.equals("") && move) {
            message = new PlayerMessageDie(idClient, Integer.parseInt(idDieChosen), colonna, riga);
            idDieChosen = "";
            toolCardChosen = "";
            new NotYourTurnFrame();
        }
        if (idDieChosen.equals("") && !toolCardChosen.equals("") && move) {
            message = new PlayerMessageToolCard(idClient, Integer.parseInt(toolCardChosen));
            idDieChosen = "";
            toolCardChosen = "";
        }
        if (end) {
            message = new PlayerMessageEndTurn(idClient);
            end = false;
        }

        return message;
    }

}
