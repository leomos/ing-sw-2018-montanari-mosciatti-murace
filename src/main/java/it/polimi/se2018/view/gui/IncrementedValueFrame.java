package it.polimi.se2018.view.gui;

import it.polimi.se2018.model.events.ModelChangedMessageDiceArena;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class IncrementedValueFrame extends ToolCardFrame {

    private ArrayList<Integer> values = new ArrayList<>();

    public IncrementedValueFrame(ModelChangedMessageDiceArena message) {
        SwingDiceArena diceArena = new SwingDiceArena(message);

        JFrame frame = new JFrame();
        new JDialog(frame, "", true);
        setLayout(new BorderLayout());

        JLabel label = new JLabel("Choose one die from DiceArena", SwingConstants.CENTER);

        String s = "Die chosen: ";
        JLabel die = new JLabel(s, SwingConstants.CENTER);

        JPanel p = new JPanel();
        p.setLayout(new FlowLayout());
        for (int i=0; i<diceArena.getButtons().size(); i++) {
            int finalI = i;
            diceArena.getButtons().get(i).addActionListener(actionListener -> {
                    values.add(0, finalI);
                    die.setText(s + values.get(0));
            });
            p.add(diceArena.getButtons().get(i));
        }

        JRadioButton incrementButton = new JRadioButton();
        JRadioButton decrementButton = new JRadioButton();
        ButtonGroup bg = new ButtonGroup();
        bg.add(incrementButton);
        bg.add(decrementButton);
        incrementButton.setText("INCREASE");
        decrementButton.setText("DECREASE");

        incrementButton.setSelected(true);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(incrementButton);
        buttonPanel.add(decrementButton);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(buttonPanel, BorderLayout.NORTH);
        panel.add(p, BorderLayout.CENTER);
        panel.add(die, BorderLayout.SOUTH);

        JButton button = new JButton("CONTINUE");
        button.addActionListener(actionListener -> {
                if(!values.isEmpty()) {
                    if (incrementButton.isSelected())
                        values.add(1, 1);
                    if (decrementButton.isSelected())
                        values.add(1, 0);
                    dispose();
                }
        });

        add(label, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
        add(button, BorderLayout.SOUTH);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation ((screenSize.width - 500)/2, (screenSize.height - 200)/2);

        setSize(500, 200);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setVisible(true);

    }

    @Override
    public ArrayList<Integer> getValues(){
        while(values.size() != 2);
        return values;
    }

    @Override
    public int getValue() {
        return 0;
    }

    @Override
    public void close() {
        setModal(false);
        dispose();
    }
}
