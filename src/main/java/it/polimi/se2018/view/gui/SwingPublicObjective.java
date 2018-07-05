package it.polimi.se2018.view.gui;

import it.polimi.se2018.model.events.ModelChangedMessagePublicObjective;
import it.polimi.se2018.utils.ImageLoader;

import java.awt.*;

public class SwingPublicObjective extends it.polimi.se2018.view.gui.Panel {
    public SwingPublicObjective(ModelChangedMessagePublicObjective message) {
        super((new ImageLoader()).getPublicObjective(message.getIdPublicObjective()));
        setPreferredSize(new Dimension(185, 255));
        setToolTipText("Public Objectives");
    }
}
