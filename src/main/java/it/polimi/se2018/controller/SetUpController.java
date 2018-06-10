package it.polimi.se2018.controller;

import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.events.PlayerMessageSetup;

public class SetUpController {

    private Model model;

    public SetUpController(Model model){
        this.model = model;
    }

    public void execute(PlayerMessageSetup playerMessageSetup){

        int idPatternCard = playerMessageSetup.getPatternCardId();
        int idPlayer = playerMessageSetup.getPlayerId();

        model.setChosenPatternCard(idPatternCard, idPlayer);

    }
}
