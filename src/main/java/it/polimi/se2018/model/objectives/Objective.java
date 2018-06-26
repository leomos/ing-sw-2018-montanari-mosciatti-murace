package it.polimi.se2018.model.objectives;

import it.polimi.se2018.model.patternCard.PatternCard;
import it.polimi.se2018.model.container.DiceContainerUnsupportedIdException;

public interface Objective {

    int calculateScore (PatternCard patterncard) throws DiceContainerUnsupportedIdException;
}
