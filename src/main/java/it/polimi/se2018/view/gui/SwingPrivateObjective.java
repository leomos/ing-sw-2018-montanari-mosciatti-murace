package it.polimi.se2018.view.gui;

import it.polimi.se2018.model.events.ModelChangedMessagePrivateObjective;
import it.polimi.se2018.utils.ImageLoader;

import javax.swing.*;
import java.awt.*;

public class SwingPrivateObjective extends it.polimi.se2018.view.gui.Panel{

    /**
     * This constructor creates a Panel that contains the Private Objective in the message
     * @param message that represents a Private Objective
     */
    public SwingPrivateObjective (ModelChangedMessagePrivateObjective message) {
        super((new ImageLoader()).getPrivateObjective(message.getIdPrivateObjective()));
        setPreferredSize(new Dimension(185, 255));
        setToolTipText("Private Objectives");
    }
}
