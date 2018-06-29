package it.polimi.se2018.view.gui;

import it.polimi.se2018.model.events.ModelChangedMessagePrivateObjective;

import java.awt.*;
import java.awt.Panel;

public class SwingPrivateObjective extends it.polimi.se2018.view.gui.Panel{
    public SwingPrivateObjective (ModelChangedMessagePrivateObjective message) {
        super(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Alessandro Murace\\Pictures\\PRIVATEOBJECTIVES\\PrivateObjectives" + message.getIdPublicObjective() + ".png"));
        setPreferredSize(new Dimension(185, 255));
        setToolTipText("Private Objectives");
    }
}
