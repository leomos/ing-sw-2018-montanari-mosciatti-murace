package it.polimi.se2018.view.gui;

import it.polimi.se2018.model.events.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class ViewClientGUIGame extends SwingPhase {
    private JFrame jFrame = new JFrame();

    private String idDieChosen = "";

    private String toolCardChosen = "";

    private int riga = -1;

    private int colonna = -1;

    private int idClient;

    private boolean isMyTurn;

    private ArrayList<String> idPlayers = new ArrayList<>();

    private ArrayList<ModelChangedMessagePatternCard> patternCards = new ArrayList<>();

    private ArrayList<ModelChangedMessageDiceOnPatternCard> diceOnPatternCards = new ArrayList<>();

    private ArrayList<ModelChangedMessagePublicObjective> publicObjectives = new ArrayList<>();

    private ArrayList<ModelChangedMessageToolCard> toolCards = new ArrayList<>();

    private ModelChangedMessageDiceArena diceArena;

    private ModelChangedMessagePrivateObjective privateObjective;

    private ModelChangedMessageTokensLeft tokensLeft;

    private ArrayList<ModelChangedMessageRound> roundTrack = new ArrayList<>();

    public ViewClientGUIGame (int idClient) {
        this.idClient = idClient;
    }

    @Override
    public void update(ModelChangedMessage message) {

        if (message instanceof ModelChangedMessagePatternCard){
            idPlayers.add(Integer.toString(((ModelChangedMessagePatternCard) message).getIdPlayer()));
            patternCards.add((ModelChangedMessagePatternCard) message);
            diceOnPatternCards.add(null);
        }
        else if (message instanceof ModelChangedMessageDiceOnPatternCard){
            int i = idPlayers.indexOf(((ModelChangedMessageDiceOnPatternCard) message).getIdPlayer());
            diceOnPatternCards.remove(i);
            diceOnPatternCards.add(i, (ModelChangedMessageDiceOnPatternCard) message);
        }
        else if(message instanceof ModelChangedMessagePrivateObjective) {
            if (((ModelChangedMessagePrivateObjective) message).getIdPlayer() == idClient)
                privateObjective = ((ModelChangedMessagePrivateObjective) message);
        }
        else if(message instanceof ModelChangedMessagePublicObjective)
            publicObjectives.add((ModelChangedMessagePublicObjective)message);
        else if(message instanceof ModelChangedMessageToolCard) {
            if(toolCards.size() == 3) {
                for(int i = 0; i < 3; i++)
                    if(toolCards.get(i).getIdToolCard() == ((ModelChangedMessageToolCard) message).getIdToolCard()) {
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
            int i = ((ModelChangedMessageRound) message).getIdRound();
            if(i >= roundTrack.size())
                roundTrack.add((ModelChangedMessageRound) message);
            else {
                roundTrack.remove(i);
                roundTrack.add(i, (ModelChangedMessageRound) message);
            }
        }
        else if(message instanceof ModelChangedMessageTokensLeft) {
            if (((ModelChangedMessageTokensLeft) message).getIdPlayer() == idClient)
                tokensLeft = (ModelChangedMessageTokensLeft) message;
        }
        else if(message instanceof ModelChangedMessageRefresh) {
            isMyTurn = ((ModelChangedMessageRefresh) message).getIdPlayerPlaying() == idClient;
        }
    }

    public void print(){
        jFrame.getContentPane().removeAll();
        jFrame.repaint();

        jFrame.setLayout(new GridBagLayout());
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setTitle("TABLE");
        Color c = new Color(34, 139, 34);
        jFrame.getContentPane().setBackground(c);
        jFrame.setResizable(false);

        //TOOLCARD
        SwingToolCards[] toolCard = new SwingToolCards[3];
        for (int i=0; i<3; i++) {
            int finalI = i;
            toolCard[i] = new SwingToolCards(toolCards.get(i));
            toolCard[i].getCard().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    toolCardChosen = "" + toolCards.get(finalI).getIdToolCard();
                }
            });
        }

        //PUBLIC OBJECTIVE
        SwingPublicObjective[] publicObjective = new SwingPublicObjective[3];
        for (int i=0; i<3; i++) {
            publicObjective[i] = new SwingPublicObjective(publicObjectives.get(i));
        }

        //PATTERNCARD
        SwingPatternCard myPatternCard;
        SwingDiceOnPatternCard mine = null;
        SwingDiceOnPatternCard[] patternCard = new SwingDiceOnPatternCard[3];
        for (int i=0; i<patternCards.size(); i++) {
            if (patternCards.get(i).getIdPlayer() == (idClient)) {
                myPatternCard = new SwingPatternCard(patternCards.get(i), false);

                for (int m=0; m<diceOnPatternCards.size(); m++) {
                    if (diceOnPatternCards.get(m).getIdPlayer() == idClient)
                        mine = new SwingDiceOnPatternCard(diceOnPatternCards.get(m), patternCards.get(i), myPatternCard.getPatternCard(), false);
                }
                for (int n=0; n<20; n++) {
                    int finalN = n;
                    SwingDie die = mine.getPc().get(n);
                    die.addActionListener(actionListener -> {
                            if (!idDieChosen.equals("")) {
                                riga = finalN / 5;
                                colonna = finalN % 5;
                            }
                    });
                }
            }
            else {
                SwingPatternCard card = new SwingPatternCard(patternCards.get(i), true);
                for (int m=0; m<diceOnPatternCards.size(); m++) {
                    if (Integer.toString(diceOnPatternCards.get(m).getIdPatternCard()).equals(card.getId()))
                        patternCard[m] = new SwingDiceOnPatternCard(diceOnPatternCards.get(m), patternCards.get(i), card.getPatternCard(), true);
                }
            }
        }

        //DICEARENA
        SwingDiceArena arena = new SwingDiceArena(diceArena);
        for (int i=0; i<arena.getButtons().size(); i++){
            SwingDie b = arena.getButtons().get(i);
            b.addActionListener(actionListener -> {
                    if (idDieChosen.equals(""))
                        idDieChosen = "" + b.getId();
            });
        }

        //PLAYER
        SwingPlayer player = new SwingPlayer(mine, new SwingPrivateObjective(privateObjective), Integer.toString(tokensLeft.getTokensLeft()));

        //BOTTONI
        JButton conferma = new JButton("CONFIRM MOVE");
        conferma.addActionListener(actionListener -> {
                if (isMyTurn) {
                    if (!idDieChosen.equals("") || !toolCardChosen.equals("")) {
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
                                if (f.isOk() == 1) {
                                    try {
                                        serverInterface.notify(getMainMove());
                                    } catch (RemoteException e1) {
                                        e1.printStackTrace();
                                    }
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
                else
                    new NotYourTurnFrame();
        });

        JButton endTurn = new JButton("END TURN");
        endTurn.addActionListener(actionListener -> {
                if (isMyTurn) {
                    try {
                        serverInterface.notify(new PlayerMessageEndTurn(idClient));
                    } catch (RemoteException e1) {
                        e1.printStackTrace();
                    }
                }
                else new NotYourTurnFrame();
        });

        //ROUNDTRANCK
        SwingRoundTrack rt = new SwingRoundTrack(roundTrack);

        GridBagConstraints constraints = new GridBagConstraints();
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
            if (!(patternCards.get(i).getIdPlayer() == idClient))
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

        jFrame.validate();
    }

    @Override
    public Integer askForPatternCard() {
        return null;
    }

    @Override
    public ArrayList<Integer> getPositionInPatternCard() {
        ArrayList<Integer> values = new ArrayList<>();
        int pc = 0, dop = 0;

        for (int i=0; i<patternCards.size(); i++) {
            if (patternCards.get(i).getIdPlayer().equals(Integer.toString(idClient))) {
                pc = i;

                for (int m = 0; m < diceOnPatternCards.size(); m++)
                    if (diceOnPatternCards.get(m).getIdPlayer().equals(Integer.toString(idClient))) {
                        dop = m;
                }
            }
        }

        ConfirmPositionFrame frame = new ConfirmPositionFrame(diceOnPatternCards.get(dop), patternCards.get(pc));
        return frame.getvalues();

                /*.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                values.addAll(frame.getvalues());
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
        while(values.isEmpty()) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return values;
*/
    }

    public PlayerMessage getMainMove() {

        if (!idDieChosen.equals("") && toolCardChosen.length() == 0) {
            int s = Integer.parseInt(idDieChosen);
            idDieChosen = "";
            return  new PlayerMessageDie(idClient, s, colonna, riga);
        }
        if (idDieChosen.equals("") && !toolCardChosen.equals("")) {
            int t = Integer.parseInt(toolCardChosen);
            toolCardChosen = "";
            return new PlayerMessageToolCard(idClient, t);
        }
        return null;

    }
}
