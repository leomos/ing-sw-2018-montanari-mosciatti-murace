package it.polimi.se2018.view.gui;

import javax.swing.*;
import java.awt.*;

public class OnePlayerLeftFrame {
    private JFrame frame = new JFrame();

    public OnePlayerLeftFrame(int player, int i) {
        Image image = java.awt.Toolkit.getDefaultToolkit().getImage("../ing-sw-2018-montanari-mosciatti-murace\\src\\images\\Sagrada.jpg");
        image = image.getScaledInstance(500, 670, Image.SCALE_SMOOTH);
        it.polimi.se2018.view.gui.Panel p = new it.polimi.se2018.view.gui.Panel(image);

        JLabel testo = new JLabel("Only player " + player + " is left in game!!", SwingConstants.CENTER);
        testo.setFont(new Font("Eras Bold ITC", Font.PLAIN, 20));

        JLabel label = new JLabel("", SwingConstants.CENTER);
        if (player==i)
            label.setText("CONGRATS, YOU WIN!!");
        else
            label.setText("YOU LOSE!");
        label.setFont(new Font("Ravie", Font.PLAIN, 24));
        label.setForeground(new Color(253, 233, 16));

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.add(testo, BorderLayout.NORTH);
        panel.add(label, BorderLayout.SOUTH);

        JButton button = new JButton("EXIT");
        button.addActionListener(actionListener -> {
            System.exit(0);
        });

        frame.setLayout(new BorderLayout());
        //GridBagConstraints constraints = new GridBagConstraints();

        frame.setSize(new Dimension(500, 700));
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.setContentPane(p);

        //constraints.gridx = 0;
        //constraints.gridy = 0;
        //constraints.gridwidth = 2;
        //constraints.gridheight = 1;
        //constraints.weightx = 1;
        //constraints.weighty = 1;
        //constraints.insets.top = 300;
        //constraints.anchor = GridBagConstraints.SOUTH;
        frame.add(panel, BorderLayout.CENTER);

        //constraints.gridx = 0;
        //constraints.gridy = 1;
        //constraints.weightx = 1;
        //constraints.weighty = 1;
        //constraints.anchor = GridBagConstraints.SOUTH;
        frame.add(button, BorderLayout.SOUTH);
        frame.setResizable(false);

        frame.setVisible(true);
        Dimension screenSize = Toolkit.getDefaultToolkit ().getScreenSize();
        frame.setLocation ((screenSize.width - 300) / 2, (screenSize.height - 200) / 2);
    }
}
