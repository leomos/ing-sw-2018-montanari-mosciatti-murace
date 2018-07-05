package it.polimi.se2018.view.gui;

import it.polimi.se2018.model.events.ModelChangedMessageDiceArena;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class IncrementedValueFrame extends ToolCardFrame {

    private JDialog dialog;

    private ArrayList<Integer> values = new ArrayList<>();

    public IncrementedValueFrame(ModelChangedMessageDiceArena message) {
        SwingDiceArena diceArena = new SwingDiceArena(message);

        JFrame frame = new JFrame();
        dialog = new JDialog(frame, "", true);
        dialog.setLayout(new BorderLayout());

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
                    dialog.dispose();
                }
        });

        dialog.add(label, BorderLayout.NORTH);
        dialog.add(panel, BorderLayout.CENTER);
        dialog.add(button, BorderLayout.SOUTH);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        dialog.setLocation ((screenSize.width - 500)/2, (screenSize.height - 200)/2);

        dialog.setSize(500, 200);
        dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        dialog.setVisible(true);

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
}
