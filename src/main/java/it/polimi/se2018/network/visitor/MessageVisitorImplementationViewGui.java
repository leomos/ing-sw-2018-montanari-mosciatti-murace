package it.polimi.se2018.network.visitor;

import it.polimi.se2018.model.events.*;
import it.polimi.se2018.view.gui.ViewClientGUI;

public class MessageVisitorImplementationViewGui implements MessageVisitorInterface {

    private ViewClientGUI viewClientGUI;

    public MessageVisitorImplementationViewGui(ViewClientGUI viewClientGUI){ this.viewClientGUI = viewClientGUI;}

    @Override
    public void visitMethodCallMessage(MethodCallMessage methodCallMessage) {

    }

    @Override
    public void visitModelChangedMessageConnected(ModelChangedMessageConnected modelChangedMessageConnected) {
        //todo
    }

    @Override
    public void visitModelChangedMessageDiceArena(ModelChangedMessageDiceArena modelChangedMessageDiceArena) {
        viewClientGUI.getSwingPhase().update(modelChangedMessageDiceArena);
    }

    @Override
    public void visitModelChangedMessageDiceOnPatternCard(ModelChangedMessageDiceOnPatternCard modelChangedMessageDiceOnPatternCard) {
        viewClientGUI.getSwingPhase().update(modelChangedMessageDiceOnPatternCard);
    }

    @Override
    public void visitModelChangedMessageEndGame(ModelChangedMessageEndGame modelChangedMessageEndGame) {
        viewClientGUI.getSwingPhase().update(modelChangedMessageEndGame);
    }

    @Override
    public void visitModelChangedMessageMoveFailed(ModelChangedMessageMoveFailed modelChangedMessageMoveFailed) {
        viewClientGUI.update(modelChangedMessageMoveFailed);
    }

    @Override
    public void visitModelChangedMessagePatternCard(ModelChangedMessagePatternCard modelChangedMessagePatternCard) {
        viewClientGUI.getSwingPhase().update(modelChangedMessagePatternCard);
    }

    @Override
    public void visitModelChangedMessagePrivateObjective(ModelChangedMessagePrivateObjective modelChangedMessagePrivateObjective) {
        viewClientGUI.getSwingPhase().update(modelChangedMessagePrivateObjective);
    }

    @Override
    public void visitModelChangedMessagePublicObjective(ModelChangedMessagePublicObjective modelChangedMessagePublicObjective) {
        viewClientGUI.getSwingPhase().update(modelChangedMessagePublicObjective);
    }

    @Override
    public void visitModelChangedMessageChangeGamePhase(ModelChangedMessageChangeGamePhase modelChangedMessageChangeGamePhase) {
        viewClientGUI.update(modelChangedMessageChangeGamePhase);
    }

    @Override
    public void visitModelChangedMessageRefresh(ModelChangedMessageRefresh modelChangedMessageRefresh) {
        viewClientGUI.update(modelChangedMessageRefresh);
    }

    @Override
    public void visitModelChangedMessageRound(ModelChangedMessageRound modelChangedMessageRound) {
        viewClientGUI.getSwingPhase().update(modelChangedMessageRound);
    }

    @Override
    public void visitModelChangedMessageNewEvent(ModelChangedMessageNewEvent modelChangedMessageNewEvent) {
        viewClientGUI.update(modelChangedMessageNewEvent);
    }

    @Override
    public void visitModelChangedMessageToolCard(ModelChangedMessageToolCard modelChangedMessageToolCard) {
        viewClientGUI.getSwingPhase().update(modelChangedMessageToolCard);
    }

    @Override
    public void visitModelChangedMessageTokensLeft(ModelChangedMessageTokensLeft modelChangedMessageTokensLeft) {
        viewClientGUI.getSwingPhase().update(modelChangedMessageTokensLeft);
    }

    @Override
    public void visitModelChangedMessagePlayerAFK(ModelChangedMessagePlayerAFK modelChangedMessagePlayerAFK) {
        viewClientGUI.update(modelChangedMessagePlayerAFK);
    }

    @Override
    public void visitModelChangedMessageOnlyOnePlayerLeft(ModelChangedMessageOnlyOnePlayerLeft modelChangedMessageOnlyOnePlayerLeft) {
        viewClientGUI.getSwingPhase().update(modelChangedMessageOnlyOnePlayerLeft);
    }

    @Override
    public void visitPlayerMessageDie(PlayerMessageDie playerMessageDie) {

    }

    @Override
    public void visitPlayerMessageEndTurn(PlayerMessageEndTurn playerMessageEndTurn) {

    }

    @Override
    public void visitPlayerMessageSetup(PlayerMessageSetup playerMessageSetup) {

    }

    @Override
    public void visitPlayerMessageToolCard(PlayerMessageToolCard playerMessageToolCard) {

    }

    @Override
    public void visitPlayerMessageNotAFK(PlayerMessageNotAFK playerMessageNotAFK) {

    }

    @Override
    public void visitHeartbeatMessage(HeartbeatMessage heartbeatMessage) {

    }
}
