package it.polimi.se2018.view.gui;

import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel {
    private Image image;

    public Panel(Image image) {
        this.image = image;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }
}
