package it.polimi.se2018.view.gui;

import it.polimi.se2018.model.events.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class ViewClientGUIGame extends SwingPhase {

    private JFrame jFrame = new JFrame();

    private ToolCardFrame toolCardFrame;

    private boolean newturn;

    private String idDieChosen = "";

    private String toolCardChosen = "";

    private int row = -1;

    private int column = -1;

    private int idClient;

    private boolean isMyTurn;

    private int idPlayerPlaying;

    private boolean canIPlay = true;

    private ArrayList<Integer> idPlayers = new ArrayList<>();

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
        newturn = true;
    }



    @Override
    public void update(ModelChangedMessagePatternCard message) {
        idPlayers.add(message.getIdPlayer());
        patternCards.add(message);
        diceOnPatternCards.add(null);
    }

    @Override
    public void update(ModelChangedMessagePrivateObjective message) {
        if ((message).getIdPlayer() == idClient)
            privateObjective = message;
    }

    @Override
    public void update(ModelChangedMessageDiceOnPatternCard message) {
        int i = idPlayers.indexOf(message.getIdPlayer());
        diceOnPatternCards.remove(i);
        diceOnPatternCards.add(i, message);
    }

    @Override
    public void update(ModelChangedMessagePublicObjective message) {
        publicObjectives.add(message);
    }

    @Override
    public void update(ModelChangedMessageDiceArena message) {
        diceArena = message;
    }

    @Override
    public void update(ModelChangedMessageRound message) {
        int i = (message).getIdRound();
        if(i >= roundTrack.size())
            roundTrack.add(message);
        else {
            roundTrack.remove(i);
            roundTrack.add(i, message);
        }

    }

    @Override
    public void update(ModelChangedMessageTokensLeft message) {
        if ((message).getIdPlayer() == idClient)
            tokensLeft = message;
    }

    @Override
    public void update(ModelChangedMessageEndGame message) {

    }

    @Override
    public void update(ModelChangedMessageRefresh message) {
        isMyTurn = message.getIdPlayerPlaying() == idClient;
        idPlayerPlaying = message.getIdPlayerPlaying();

    }

    @Override
    public void update(ModelChangedMessageOnlyOnePlayerLeft message) {

    }

    @Override
    public void update(ModelChangedMessageToolCard message) {
        if(toolCards.size() == 3) {
            for(int i = 0; i < 3; i++)
                if(toolCards.get(i).getIdToolCard() == (message).getIdToolCard()) {
                    toolCards.remove(i);
                    toolCards.add(i, message);
                }
        } else {
            toolCards.add(message);
        }

    }


    /**
     * Prints the table:
     * it prints the other player's pattern card, the rounds, the tool cards, the public objectives,
     * the player's information (pattern card, private objective and tokens left) + the draft pool
     */
    public void print(){
        jFrame.getContentPane().removeAll();
        jFrame.repaint();

        jFrame.setLayout(new GridBagLayout());
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setTitle("TABLE");
        Color c = new Color(34, 139, 34);
        jFrame.getContentPane().setBackground(c);
        jFrame.setResizable(false);

        String s1 = "DRAFT POOL: die ";
        String s2 = "TOOLCARD: ";
        String s3 = "POSITION: (COLUMN, ROW): ";
        JLabel l1 = new JLabel(s1, SwingConstants.CENTER);
        JLabel l2 = new JLabel(s2, SwingConstants.CENTER);
        JLabel l3 = new JLabel(s3, SwingConstants.CENTER);
        l1.setFont(new Font("ERAS DEMI ITC", Font.PLAIN, 12));
        l2.setFont(new Font("ERAS DEMI ITC", Font.PLAIN, 12));
        l3.setFont(new Font("ERAS DEMI ITC", Font.PLAIN, 12));

        //TOOLCARD
        SwingToolCards[] toolCard = new SwingToolCards[3];
        for (int i=0; i<3; i++) {
            int finalI = i;
            toolCard[i] = new SwingToolCards(toolCards.get(i));
            toolCard[i].getCard().addActionListener(actionListener -> {
                    toolCardChosen = "" + toolCards.get(finalI).getIdToolCard();
                    idDieChosen = "";
                    row = -1;
                    column = -1;
                    l1.setText(s1 + idDieChosen);
                    l2.setText(s2 + toolCardChosen);
                    if (column!=-1 && row!=-1)
                        l3.setText(s3 + column + " - " + row);
                    else
                        l3.setText(s3);
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
                                row = finalN / 5;
                                column = finalN % 5;
                                toolCardChosen = "";
                                l1.setText(s1 + idDieChosen);
                                l2.setText(s2 + toolCardChosen);
                                l3.setText(s3 + column + " - " + row);

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
                idDieChosen = "" + b.getId();
                toolCardChosen = "";
                l1.setText(s1 + idDieChosen);
                l2.setText(s2 + toolCardChosen);
                if (column!=-1 && row!=-1)
                    l3.setText(s3 + column + " - " + row);
                else
                    l3.setText(s3);
            });
        }

        //PLAYER
        SwingPlayer player = new SwingPlayer(mine, new SwingPrivateObjective(privateObjective), tokensLeft.getTokensLeft());

        //BUTTONS
        JPanel p = new JPanel();
        p.setPreferredSize(new Dimension(200, 150));
        p.setLayout(new GridLayout(5, 1, 0, 5));
        p.setBackground(c);
        JButton conferma = new JButton("CONFIRM MOVE");
        conferma.addActionListener(actionListener -> {
            if (isMyTurn) {
                if (canIPlay) {
                    if (!idDieChosen.equals("") || !toolCardChosen.equals("")) {
                        ConfirmationFrame f = new ConfirmationFrame(idDieChosen, column, row, toolCardChosen);
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
                                        viewClientGUI.handleDisconnection();
                                    }
                                } else {
                                    idDieChosen = "";
                                    toolCardChosen = "";
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
                    new MoveFailedFrame("Finish to use ToolCard first");
            }
            else
                new NotYourTurnFrame();
        });

        JButton endTurn = new JButton("END TURN");
        endTurn.addActionListener(actionListener -> {
            if (isMyTurn) {
                if (canIPlay) {
                    try {
                        serverInterface.notify(new PlayerMessageEndTurn(idClient));
                        newturn = true;
                    } catch (RemoteException e1) {
                        viewClientGUI.handleDisconnection();
                    }
                }
                else
                    new MoveFailedFrame("Finish to use ToolCard first");
            }
            else new NotYourTurnFrame();
        });
        p.add(conferma);
        p.add(endTurn);
        p.add(l1);
        p.add(l2);
        p.add(l3);

        //ROUNDTRANCK
        SwingRoundTrack rt = new SwingRoundTrack(roundTrack);

        JPanel turn = new JPanel();
        turn.setBackground(new Color(244, 241, 255));
        turn.setPreferredSize(new Dimension(200, 50));
        turn.setLayout(new GridLayout(2, 1));
        int m = roundTrack.size() + 1;
        JLabel cr = new JLabel("CURRENT ROUND: " + m, SwingConstants.CENTER);
        JLabel pp;
        if (idPlayerPlaying==idClient)
            pp = new JLabel("PLAYER PLAYING: " + idPlayerPlaying + ", YOU!!", SwingConstants.CENTER);
        else
            pp = new JLabel("PLAYER PLAYING: " + idPlayerPlaying, SwingConstants.CENTER);
        cr.setFont(new Font("ERAS DEMI ITC", Font.PLAIN, 12));
        pp.setFont(new Font("ERAS DEMI ITC", Font.PLAIN, 12));
        turn.add(cr);
        turn.add(pp);

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
            if (patternCards.get(i).getIdPlayer() != idClient)
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
        jFrame.add(p, constraints);

        constraints.fill = GridBagConstraints.NONE;
        constraints.insets.top = 300;
        constraints.insets.left = 10;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.anchor = GridBagConstraints.SOUTHWEST;
        jFrame.add(rt, constraints);

        constraints.insets.top = 300;
        constraints.insets.right = 500;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.gridwidth = 2;
        constraints.gridheight = 1;
        constraints.anchor = GridBagConstraints.SOUTHEAST;
        jFrame.add(turn, constraints);

        jFrame.pack();
        jFrame.setVisible(true);

        if (isMyTurn && newturn) {
            new TurnFrame();
            newturn = false;
        }

        Dimension screenSize = Toolkit.getDefaultToolkit ().getScreenSize();
        Dimension frameSize = jFrame.getSize();
        jFrame.setLocation ((screenSize.width - frameSize.width) / 2, 0);

        jFrame.validate();
    }

    @Override
    public Integer askForPatternCard() {
        return null;
    }

    /**
     * Method needed for pattern card n.2, n.3 and n.4 to get a starting and a final position to move a die
     * @return array list containing the starting and the final positions
     */
    @Override
    public ArrayList<Integer> getPositionInPatternCard() {
        int myId = -1;

        block();
        for (int i=0; i<patternCards.size(); i++)
            if (patternCards.get(i).getIdPlayer() == idClient)
                myId = i;


        toolCardFrame = new ConfirmPositionFrame(diceOnPatternCards.get(myId), patternCards.get(myId));
        ArrayList<Integer> returnValues = toolCardFrame.getValues();
        free();
        return returnValues;
    }

    /**
     * Method needed for pattern card so that the player can select one of the dice from the dice arena
     * @return die chosen
     */
    @Override
    public Integer getDieFromDiceArena() {
        block();
        toolCardFrame = new DiceArenaFrame(diceArena);
        free();
        return toolCardFrame.getValue();
    }

    /**
     * Method needed for tool card n.1
     * @return array list containing the die chosen and a value to see if he wants to increase or decrease its value
     */
    @Override
    public ArrayList<Integer> getIncrementedValue() {
        block();
        toolCardFrame = new IncrementedValueFrame(diceArena);
        ArrayList<Integer> returnValues = toolCardFrame.getValues();
        free();
        return returnValues;
    }

    /**
     * Get a single position (x and y) that needs to be contained in listOfAvailablePositions.
     * If list of available positions is empty, the player can insert any positions
     * @param listOfAvailablePosition positions available
     * @return array list containing the position chosen by the player
     */
    @Override
    public ArrayList<Integer> getSinglePositionInPatternCard(ArrayList<Integer> listOfAvailablePosition) {
        int myId = -1;

        block();
        for (int i=0; i<patternCards.size(); i++)
            if (patternCards.get(i).getIdPlayer() == idClient)
                myId = i;

        toolCardFrame = new OnePositionFrame(diceOnPatternCards.get(myId), patternCards.get(myId), listOfAvailablePosition);
        ArrayList<Integer> returnValues = toolCardFrame.getValues();
        free();
        return returnValues;
    }

    /**
     * Method needed for tool card n.12 to move either one or two dice
     * @return array list containing the start and the final positions of the movement(s)
     */
    @Override
    public ArrayList<Integer> getDoublePositionInPatternCard() {
        int myId = -1;

        block();
        for (int i=0; i<patternCards.size(); i++)
            if (patternCards.get(i).getIdPlayer() == idClient)
                myId = i;

        toolCardFrame = new NumberOfMovementsFrame();
        int momevemnts = toolCardFrame.getValue();

        toolCardFrame = new ConfirmPositionFrame(diceOnPatternCards.get(myId), patternCards.get(myId));
        ArrayList<Integer> returnValues = toolCardFrame.getValues();

        if(momevemnts == 2) {
            toolCardFrame = new ConfirmPositionFrame(diceOnPatternCards.get(myId), patternCards.get(myId));
            returnValues.addAll(toolCardFrame.getValues());
        }
        free();
        return returnValues;
    }

    /**
     * Method needed for tool cards
     * @return array list containing the die chosen by the player and in which round it appears
     */
    @Override
    public ArrayList<Integer> getDieFromRoundTrack() {
        block();
        toolCardFrame = new RoundTrackFrame(roundTrack);
        ArrayList<Integer> returnValues = toolCardFrame.getValues();
        free();
        return returnValues;
    }

    /**
     * Method needed for pattern card so that the player can choose which value to give to the die
     * @return value chosen by the player
     */
    @Override
    public Integer getValueForDie() {
        Integer value;
        block();
        toolCardFrame = new DieValueFrame();

        value = toolCardFrame.getValue();
        free();
        return value;
    }

    /**
     * Method to close the JFrame created and the ToolCardFrame if it is already open
     */
    @Override
    public void close() {
        if(toolCardFrame != null) toolCardFrame.close();
        jFrame.dispose();
    }

    /**
     * checks if the move chose by the player is acceptable and generates either a playerMessageDie,
     * a playerMessageToolCard, player playerMessageEndTurn. If the move is not correct, it generates a
     * playerMessage with clientId as -1
     * @return playerMessage if the move was correct otherwise a playerMessage with clientId as -1
     */
    @Override
    public PlayerMessage getMainMove() {

        if (!idDieChosen.equals("") && toolCardChosen.length() == 0) {
            int s = Integer.parseInt(idDieChosen);
            idDieChosen = "";
            return  new PlayerMessageDie(idClient, s, column, row);
        }
        if (idDieChosen.equals("") && !toolCardChosen.equals("")) {
            int t = Integer.parseInt(toolCardChosen);
            toolCardChosen = "";
            return new PlayerMessageToolCard(idClient, t);
        }
        return null;

    }

    /**
     * After the player chooses to use a tool card, the main input is disabled through this method
     * @return boolean
     */
    public void block(){
        canIPlay = false;
    }

    /**
     * After the player finishes to use a tool card, the main input is enabled again through this method
     * @return boolean
     */
    public void free() {
        canIPlay = true;
    }



}
