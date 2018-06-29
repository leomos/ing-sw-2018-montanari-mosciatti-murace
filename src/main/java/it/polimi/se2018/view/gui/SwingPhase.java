package it.polimi.se2018.view.gui;

import it.polimi.se2018.model.events.ModelChangedMessage;

public abstract class SwingPhase {

    public abstract void update(ModelChangedMessage modelChangedMessage);

    public abstract void print();

    public abstract Integer askForPatternCard();
}
