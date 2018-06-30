package it.polimi.se2018.view.gui;

import it.polimi.se2018.model.events.ModelChangedMessagePrivateObjective;
import java.awt.*;

public class SwingPrivateObjective extends it.polimi.se2018.view.gui.Panel{
    public SwingPrivateObjective (ModelChangedMessagePrivateObjective message) {
        super(Toolkit.getDefaultToolkit().getImage("../ing-sw-2018-montanari-mosciatti-murace\\src\\images\\privateobjectives\\PrivateObjectives" + message.getIdPublicObjective() + ".png"));
        setPreferredSize(new Dimension(185, 255));
        setToolTipText("Private Objectives");
    }
}
