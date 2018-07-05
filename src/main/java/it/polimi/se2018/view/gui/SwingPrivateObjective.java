package it.polimi.se2018.view.gui;

import it.polimi.se2018.model.events.ModelChangedMessagePrivateObjective;
import it.polimi.se2018.utils.ImageLoader;

import javax.swing.*;
import java.awt.*;

public class SwingPrivateObjective extends it.polimi.se2018.view.gui.Panel{
    public SwingPrivateObjective (ModelChangedMessagePrivateObjective message) {
        super((new ImageLoader()).getPrivateObjective(message.getIdPrivateObjective()));
        setPreferredSize(new Dimension(185, 255));
        setToolTipText("Private Objectives");
    }
}
