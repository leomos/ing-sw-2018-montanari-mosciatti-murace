package it.polimi.se2018.view.gui;

import it.polimi.se2018.model.events.ModelChangedMessagePublicObjective;
import it.polimi.se2018.utils.ImageLoader;

import java.awt.*;

public class SwingPublicObjective extends it.polimi.se2018.view.gui.Panel {

    /**
     * This constructor creates a Panel that contains the Public Objective sent by message
     * @param message that represents a Public Objective
     */
    public SwingPublicObjective(ModelChangedMessagePublicObjective message) {
        super((new ImageLoader()).getPublicObjective(message.getIdPublicObjective()));
        setPreferredSize(new Dimension(185, 255));
        setToolTipText("Public Objectives");
    }
}
