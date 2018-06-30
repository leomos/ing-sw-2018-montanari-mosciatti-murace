package it.polimi.se2018.view.gui;

import it.polimi.se2018.model.events.ModelChangedMessagePublicObjective;
import java.awt.*;

public class SwingPublicObjective extends it.polimi.se2018.view.gui.Panel {
    public SwingPublicObjective(ModelChangedMessagePublicObjective message) {
        super(Toolkit.getDefaultToolkit().getImage("../ing-sw-2018-montanari-mosciatti-murace\\src\\images\\publicobjectives\\PublicObjective" + message.getIdPublicObjective() + ".jpg"));
        setPreferredSize(new Dimension(185, 255));
        setToolTipText("Public Objectives");
    }
}
